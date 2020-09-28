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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.mastercard.developer.interceptors.OkHttpOAuth1Interceptor;
import com.mastercard.placesreferenceapplication.exception.PlacesServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

//These Imports are generated by an openAPI's generator on build.
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.MerchantCategoryCodesApi;
import org.openapitools.client.api.MerchantIndustryCodesApi;
import org.openapitools.client.api.PlacesApi;

import org.openapitools.client.model.PageResponse;
import org.openapitools.client.model.MerchantCategoryCode;
import org.openapitools.client.model.MerchantIndustryCode;
import org.openapitools.client.model.PlaceInfo;
import org.openapitools.client.model.PlaceSearchRequest;

@Service
public class DefaultPlaceService implements PlaceService {
    @Value("${mastercard.p12.path}")
    private String p12Path;

    @Value("${mastercard.keystore.alias}")
    private String keyAlias;

    @Value("${mastercard.keystore.pass}")
    private String keyPass;

    @Value("${mastercard.consumer.key}")
    private String consumerKey;

    @Value("${mastercard.basePath}")
    private String basePath;

    public ApiClient signRequest() {
        ApiClient client = new ApiClient();
        client.setBasePath(basePath);
            client.setHttpClient(
                    client.getHttpClient()
                            .newBuilder()
                            .addInterceptor(new OkHttpOAuth1Interceptor(consumerKey, getSigningKey()))
                            .build()
            );

        return client;
    }

    public PrivateKey getSigningKey() {
        try {
            KeyStore pkcs12KeyStore = KeyStore.getInstance("PKCS12", "SunJSSE");
            try (InputStream inputStream = DefaultPlaceService.class.getClassLoader().getResourceAsStream(p12Path)) {
                pkcs12KeyStore.load(inputStream, keyPass.toCharArray());
                return (PrivateKey) pkcs12KeyStore.getKey(keyAlias, keyPass.toCharArray());
            }
        } catch (CertificateException | UnrecoverableKeyException | NoSuchAlgorithmException | IOException | KeyStoreException | NoSuchProviderException exception){
            throw new PlacesServiceException(exception);
        }
    }

    public PageResponse getMccCodes(int limit, int offset, String sort) throws ApiException {
        MerchantCategoryCodesApi api = new MerchantCategoryCodesApi(signRequest());
        return api.getAllMerchantCategoryCodesUsingGet(limit, offset, sort);
    }

    public MerchantCategoryCode getMccByCode(String mccCode) throws ApiException {
        MerchantCategoryCodesApi api = new MerchantCategoryCodesApi(signRequest());

        return api.getByMccCodeUsingGET(mccCode);
    }

    public PageResponse getIndustryCodes(int limit, int offset, String sort) throws ApiException {
        MerchantIndustryCodesApi api = new MerchantIndustryCodesApi(signRequest());

        return api.getAllIndustryCodesUsingGET(limit, offset, sort);
    }

    public MerchantIndustryCode getIndustryByCode(String industryCode) throws ApiException {
        MerchantIndustryCodesApi api = new MerchantIndustryCodesApi(signRequest());

        return api.getByIndustryUsingGET(industryCode);
    }

    public PlaceInfo getLocationDetails(long locationId) throws ApiException {
        PlacesApi api = new PlacesApi(signRequest());

        return api.getPlaceByLocationIdUsingGET(locationId);
    }

    public PageResponse postPlacesSearch(PlaceSearchRequest placeSearchRequestInfo, int limit, int offset) throws ApiException {
        PlacesApi api = new PlacesApi(signRequest());

        return api.searchPlacesUsingPOST(placeSearchRequestInfo, limit, offset);
    }

    public String getErrorAttributes(Exception e) {
        String errorDetails;
        if(e instanceof ApiException) {
            ApiException apiException = ((ApiException) e);

            //Attempt to parse as JSON
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                errorDetails = gson.toJson(JsonParser.parseString(apiException.getResponseBody()));

            } catch (Exception ignoredException) {

                //Print full string in case of not JSON
                errorDetails = apiException.getResponseBody();
            }

        } else {
            Logger logger = LoggerFactory.getLogger(DefaultPlaceService.class);
            logger.error("Error occurred!",e);
            errorDetails = e.toString();

        }

        return errorDetails;
    }
}
