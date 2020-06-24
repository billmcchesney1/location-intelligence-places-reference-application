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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mastercard.placesreferenceapplication.constants.PlacesConstants;
import com.mastercard.placesreferenceapplication.models.RedirectionModel;
import com.mastercard.placesreferenceapplication.models.ApiModel;
import com.mastercard.placesreferenceapplication.services.PlaceService;
import org.openapitools.client.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PlacesController {

    @Value("${mastercard.basePath}")
    private String basePath;

    private final PlaceService service;
    @Autowired
    public PlacesController(PlaceService placeService) {
        this.service = placeService;
    }



    @GetMapping("/")
    public String index(Model model, @ModelAttribute("redirection") RedirectionModel redir, RedirectAttributes redirectAttrs) {
        //Sets landing page and adds the apiModel
        model.addAttribute("requestType", (!redir.getForm().isEmpty() ? redir.getForm() : "home"));
        model.addAttribute("apiModel", new ApiModel());
        model.addAttribute("basePath", basePath);

        return "index.html";
    }

    @GetMapping("/getMccCodes")
    public String getMccCodes(@ModelAttribute("apiModel") ApiModel apiModel, RedirectAttributes redirectAttrs) {
        try {
            PageResponse codes = service.getMccCodes(apiModel.getLimit(), apiModel.getOffset(), apiModel.getSort());

            redirectAttrs.addFlashAttribute(PlacesConstants.RESPONSE, new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(codes));
            redirectAttrs.addFlashAttribute(PlacesConstants.SUCCESS, "Merchant Category Codes retrieved successfully!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute(PlacesConstants.ERROR, e.getMessage());
            redirectAttrs.addFlashAttribute(PlacesConstants.RESPONSE, service.getErrorAttributes(e));
        }
        return "redirect:/?form=getMccCodes";
    }

    @GetMapping("/getMccByCode")
    public String getMccByCode(@ModelAttribute("apiModel") ApiModel apiModel, RedirectAttributes redirectAttrs) {
        try {
            MerchantCategoryCode categoryName = service.getMccByCode(apiModel.getMccCode());

            redirectAttrs.addFlashAttribute(PlacesConstants.RESPONSE, new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(categoryName));
            redirectAttrs.addFlashAttribute(PlacesConstants.SUCCESS, "Category Name retrieved successfully!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute(PlacesConstants.ERROR, e.getMessage());
            redirectAttrs.addFlashAttribute(PlacesConstants.RESPONSE, service.getErrorAttributes(e));
        }
        return "redirect:/?form=getMccByCode";
    }

    @GetMapping("/getIndustryCodes")
    public String getIndustryCodes(@ModelAttribute("apiModel") ApiModel apiModel, RedirectAttributes redirectAttrs) {
        try {
            PageResponse codes = service.getIndustryCodes(apiModel.getLimit(), apiModel.getOffset(), apiModel.getSort());

            redirectAttrs.addFlashAttribute(PlacesConstants.RESPONSE, new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(codes));
            redirectAttrs.addFlashAttribute(PlacesConstants.SUCCESS, "Industry Codes retrieved successfully!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute(PlacesConstants.ERROR, e.getMessage());
            redirectAttrs.addFlashAttribute(PlacesConstants.RESPONSE, service.getErrorAttributes(e));
        }
        return "redirect:/?form=getIndustryCodes";
    }

    @GetMapping("/getIndustryByCode")
    public String getIndustryByCode(@ModelAttribute("apiModel") ApiModel apiModel, RedirectAttributes redirectAttrs) {
        try {
            MerchantIndustryCode industryName = service.getIndustryByCode(apiModel.getIndustryCode());

            redirectAttrs.addFlashAttribute(PlacesConstants.RESPONSE, new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(industryName));
            redirectAttrs.addFlashAttribute(PlacesConstants.SUCCESS, "Industry Name retrieved successfully!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute(PlacesConstants.ERROR, e.getMessage());
            redirectAttrs.addFlashAttribute(PlacesConstants.RESPONSE, service.getErrorAttributes(e));
        }
        return "redirect:/?form=getIndustryByCode";
    }

    @GetMapping("/getLocationDetails")
    public String getLocationDetails(@ModelAttribute("apiModel") ApiModel apiModel, RedirectAttributes redirectAttrs) {
        try {
            PlaceInfo placeDetails = service.getLocationDetails(apiModel.getLocationId());

            redirectAttrs.addFlashAttribute(PlacesConstants.RESPONSE, new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(placeDetails));
            redirectAttrs.addFlashAttribute(PlacesConstants.SUCCESS, "Place retrieved successfully!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute(PlacesConstants.ERROR, e.getMessage());
            redirectAttrs.addFlashAttribute(PlacesConstants.RESPONSE, service.getErrorAttributes(e));
        }
        return "redirect:/?form=getLocationDetails";
    }

    @GetMapping("/postPlacesSearch")
    public String postPlacesSearch(@ModelAttribute("apiModel") ApiModel apiModel, RedirectAttributes redirectAttrs) {
        try {
            Gson gson = new Gson();
            PlaceSearchRequest placeSearchRequest = gson.fromJson(apiModel.getSearchInput(), PlaceSearchRequest.class);

            PageResponse placeDetails = service.postPlacesSearch(placeSearchRequest,apiModel.getLimit(),apiModel.getOffset());

            redirectAttrs.addFlashAttribute(PlacesConstants.RESPONSE, new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(placeDetails));
            redirectAttrs.addFlashAttribute(PlacesConstants.SUCCESS, "Places Search retrieved successfully!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute(PlacesConstants.ERROR, e.getMessage());
            redirectAttrs.addFlashAttribute(PlacesConstants.RESPONSE, service.getErrorAttributes(e));
        }
        return "redirect:/?form=postPlacesSearch";
    }
}
