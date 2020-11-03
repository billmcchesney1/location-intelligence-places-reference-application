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


package com.mastercard.placesreferenceapplication.services;

import org.openapitools.client.ApiException;
import org.openapitools.client.model.MerchantCategoryCode;
import org.openapitools.client.model.MerchantIndustryCode;
import org.openapitools.client.model.PagedMerchantCategoryCode;
import org.openapitools.client.model.PagedMerchantIndustryCode;
import org.openapitools.client.model.PagedPlaceInfo;
import org.openapitools.client.model.PlaceInfo;
import org.openapitools.client.model.PlaceSearchRequest;

public interface PlaceService {

    PagedMerchantCategoryCode getMerchantCategoryCodes(int limit, int offset) throws ApiException;

    MerchantCategoryCode getMerchantCategoryByCode(String mccCode) throws ApiException;

    PagedMerchantIndustryCode getMerchantIndustryCodes(int limit, int offset) throws ApiException;

    MerchantIndustryCode getMerchantIndustryByCode(String industryCode) throws ApiException;

    PlaceInfo getPlaceDetailsByLocationId(long locationId) throws ApiException;

    PagedPlaceInfo getPlacesSearch(PlaceSearchRequest placeSearchRequestInfo, int limit, int offset) throws ApiException;

    String getErrorAttributes(Exception e);
}
