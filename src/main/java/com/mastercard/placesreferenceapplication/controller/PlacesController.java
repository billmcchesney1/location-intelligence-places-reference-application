/*
 *  Copyright (c) 2020 Mastercard
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mastercard.placesreferenceapplication.controller;

import com.mastercard.placesreferenceapplication.services.PlaceService;

import org.openapitools.client.ApiException;
import org.openapitools.client.model.MerchantCategoryCode;
import org.openapitools.client.model.PagedMerchantCategoryCode;
import org.openapitools.client.model.MerchantIndustryCode;
import org.openapitools.client.model.PagedMerchantIndustryCode;
import org.openapitools.client.model.PlaceInfo;
import org.openapitools.client.model.PlaceFilter;
import org.openapitools.client.model.PagedPlaceInfo;
import org.openapitools.client.model.PlaceSearchRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value = "/places")
public class PlacesController {

    private final PlaceService service;
    @Autowired
    public PlacesController(PlaceService placeService) {
        this.service = placeService;
    }

//    Calls for the places on the map

    @GetMapping("/search")
    @ResponseBody
    @Cacheable("places-cache")
    public ResponseEntity<PagedPlaceInfo> getPlacesSearch(@RequestParam(value = "pageOffset", defaultValue = "0", required = false) int pageOffset,
                                                          @RequestParam(value = "pageLength", defaultValue = "20", required = false) int pageLength,
                                                          @RequestParam("latitude") double latitude,
                                                          @RequestParam("longitude") double longitude,
                                                          @RequestParam("distanceUnit") String distanceUnit,
                                                          @RequestParam("country") String country) {
        PlaceFilter placeFilter = new PlaceFilter();
        placeFilter.setCountryCode(country);
        placeFilter.setLatitude(String.valueOf(latitude));
        placeFilter.setLongitude(String.valueOf(longitude));

        PlaceSearchRequest placeSearchRequest = new PlaceSearchRequest();
        placeSearchRequest.setPlace(placeFilter);
        placeSearchRequest.setUnit(PlaceSearchRequest.UnitEnum.fromValue(distanceUnit));
        placeSearchRequest.setDistance(5L);
        placeSearchRequest.setRadiusSearch(true);

        try {
            return ResponseEntity.ok(service.getPlacesSearch(placeSearchRequest, pageLength, pageOffset));
        } catch(ApiException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.valueOf(exception.getCode()));
        }
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<PlaceInfo> getPlaceDetailsByLocationId(@PathVariable("locationId") long locationId) {
        try {
            return ResponseEntity.ok(service.getPlaceDetailsByLocationId(locationId));
        } catch(ApiException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.valueOf(exception.getCode()));
        }
    }

    @GetMapping("/merchantCategoryCodes")
    public ResponseEntity<PagedMerchantCategoryCode> getMerchantCategoryCodes() {
        try {
            return ResponseEntity.ok(service.getMerchantCategoryCodes(100,0));
        } catch(ApiException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.valueOf(exception.getCode()));
        }
    }

    @GetMapping("/merchantCategoryCodes/{mccCode}")
    public ResponseEntity<MerchantCategoryCode> getMerchantCategoryByCode(@PathVariable("mccCode") String mccCode) {
        try {
            return ResponseEntity.ok(service.getMerchantCategoryByCode(mccCode));
        } catch(ApiException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.valueOf(exception.getCode()));
        }
    }

    @GetMapping("/merchantIndustryCodes")
    public ResponseEntity<PagedMerchantIndustryCode> getMerchantIndustryCodes() {
        try {
            return ResponseEntity.ok(service.getMerchantIndustryCodes(101, 0));
        } catch(ApiException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.valueOf(exception.getCode()));
        }
    }

    @GetMapping("/merchantIndustryCodes/{industryCode}")
    public ResponseEntity<MerchantIndustryCode> getMerchantIndustryByCode(@PathVariable("industryCode") String industryCode) {
        try {
            return ResponseEntity.ok(service.getMerchantIndustryByCode(industryCode));
        } catch(ApiException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.valueOf(exception.getCode()));
        }
    }


}
