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
