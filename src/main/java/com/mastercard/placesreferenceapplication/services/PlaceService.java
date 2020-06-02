//        Copyright (c) 2020 Mastercard
//
//        Licensed under the Apache License, Version 2.0 (the "License");
//        you may not use this file except in compliance with the License.
//        You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//        Unless required by applicable law or agreed to in writing, software
//        distributed under the License is distributed on an "AS IS" BASIS,
//        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//        See the License for the specific language governing permissions and
//        limitations under the License.

package com.mastercard.placesreferenceapplication.services;

import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.model.*;

import java.security.*;

public interface PlaceService {
    ApiClient signRequest();

    PrivateKey getSigningKey();

    PageResponse getMccCodes(int limit, int offset, String sort) throws ApiException;

    MerchantCategoryCode getMccByCode(String mccCode) throws ApiException;

    PageResponse getIndustryCodes(int limit, int offset, String sort) throws ApiException;

    MerchantIndustryCode getIndustryByCode(String industryCode) throws ApiException;

    PlaceInfo getLocationDetails(long locationId) throws ApiException;

    PageResponse postPlacesSearch(PlaceSearchRequest placeSearchRequestInfo, int limit, int offset) throws ApiException;

    String getErrorAttributes(Exception e);
}
