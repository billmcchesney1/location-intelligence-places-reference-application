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

package com.mastercard.placesreferenceapplication.config;

import com.mastercard.developer.interceptors.OkHttpOAuth1Interceptor;
import com.mastercard.placesreferenceapplication.exception.PlacesServiceException;
import com.mastercard.placesreferenceapplication.services.DefaultPlaceService;
import okhttp3.logging.HttpLoggingInterceptor;
import org.openapitools.client.ApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.KeyStoreException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

@Configuration
public class ApiClientConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ApiClientConfiguration.class);

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

    @Bean
    public ApiClient apiClient() {
        ApiClient client = new ApiClient();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger::info);
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BASIC);
        try {
            client.setBasePath(basePath);
            client.setHttpClient(
                    client.getHttpClient()
                            .newBuilder()
                            .addInterceptor(new OkHttpOAuth1Interceptor(consumerKey, getSigningKey()))
                            .addInterceptor(loggingInterceptor)
                            .build()
            );

            return client;
        } catch (Exception e) {
            logger.error("Error occurred while configuring ApiClient", e);
        }
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
}
