# Places API Reference Application

[![](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/Mastercard/loyalty-user-management-reference/blob/master/LICENSE)

## Table of Contents

- [Overview](#overview)
    * [Compatibility](#compatibility)
    * [References](#references)
    * [Tutorial](#tutorial)
    * [Frameworks](#frameworks)
- [Setup](#setup)
    * [Prerequisites](#prerequisites)
    * [Application Configuration](#configuration)
    * [Build and Execute](#build-and-execute)
    * [Reference Application Usage](#reference-application-usage)
    * [Integrating with OpenAPI Generator](#integrating-with-openapi-generator)
- [Use Cases](#use-cases)
    * [Parameters](#parameters)
    * [MCC Codes](#mcc-codes)
    * [Industry Codes](#industry-codes)
    * [Places](#places)
- [API Reference](#api-reference)
    - [Reference App API Reference](#ref-app-api-reference)
    - [Places API Reference](#places-api-reference)
- [Authentication](#authentication)
    * [Mastercard's OAuth Library](#oauth-library)
- [Glossary](#glossary)
- [Support](#support)
- [License](#license)
    

## Overview <a name="overview"></a>
This project showcases retrieving places, merchant category codes, and merchant industry codes using the [Places API](https://developer.mastercard.com/places-v2/documentation/).
This application illustrates connecting to the Places API using Mastercard's OAuth library, and an OpenAPI generated client.

An optional dependency is a [Google Maps Key](https://developers.google.com/maps/gmp-get-started). 
If you don't add a Google API key, you'll see an alert when the page loads and "For Development" over the map. This is normal and allows you to see the program without a key in development mode.

When you get to the part about adding APIs, you will need to add the following APIs:

- Maps JavaScript API

See pricing [here](https://developers.google.com/maps/documentation/javascript/usage-and-billing). At the time of writing users are offered a $200 credit each month and the API costs $7 / 1000 Requests.

### Compatibility <a name="compatibility"></a>
 * [Java 8](https://www.oracle.com/java/technologies/javase-jdk15-downloads.html) or later
 
### References <a name="references"></a>
* [Mastercard’s OAuth Signer library](https://github.com/Mastercard/oauth1-signer-java)
* [Using OAuth 1.0a to Access Mastercard APIs](https://developer.mastercard.com/platform/documentation/using-oauth-1a-to-access-mastercard-apis/)

### Tutorial <a name="tutorial"></a>
A tutorial can be found [here](https://developer.mastercard.com/places-v2/tutorial/places-reference-app-tutorial) for setting up and using this service.

## Frameworks <a name="frameworks"></a>
- Spring
- Thymeleaf
- OpenAPI Generator
- [Java 8+](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)

## Setup <a name="frameworks-and-requirements"></a>

### Prerequisites <a name="prerequisites"></a>

* [Mastercard Developers Account](https://developer.mastercard.com/dashboard)
* A text editor or IDE
* [Java 8+](https://www.oracle.com/java/technologies/javase-jdk15-downloads.html)
* [Spring Boot 2.2+](https://spring.io/projects/spring-boot)
* [Apache Maven 3.3+](https://maven.apache.org/download.cgi)
* Set up the `JAVA_HOME` environment variable to match the location of your Java installation.

### Application Configuration <a name="configuration"> </a>
1. Follow this [credentials quick guide](https://developer.mastercard.com/platform/documentation/getting-started-with-mastercard-apis/quick-start-guide/) to get the credentials needed to run this application
    - Be sure to add `Places` to your project.
    - A zip file will be downloaded automatically with your key.
2. Take note of the given **consumer key**, **keyalias**, and **keystore password** given upon the project creation.
3. Extract zip and place the `.p12` file in `/src/main/resources` of the project.
4. Update the properties found in `/src/main/resources/application.properties`.

#### application.properties

`mastercard.api.base-path=https://sandbox.api.mastercard.com/location-intelligence/places-locator`, This is the URL that the application will use to connect to Mastercard. For production usage, just remove `sandbox.`.
    
**Below properties will be required for authentication of API calls.**
    
`mastercard.p12.path=`, this refers to .p12 file found in the signing key. Place .p12 file at src\main\resources in the project folder then add the filename here.
`mastercard.consumer.key=`, this refers to your consumer key. Copy it from "Keys" section on your project page in [Mastercard Developers](https://developer.mastercard.com/dashboard)
`mastercard.keystore.alias=keyalias`, this is the default value of key alias. If it is modified, use the updated one from keys section in [Mastercard Developers](https://developer.mastercard.com/dashboard).
`mastercard.keystore.pass=keystorepassword`, this is the default value of key alias. If it is modified, use the updated one from keys section in [Mastercard Developers](https://developer.mastercard.com/dashboard).

`google.maps.api.key`, *OPTIONAL* if you want to show without error messages, just place your api key here.

### Build and Execute <a name="build-and-execute"> </a>
1. Run `mvn clean install` from the root of the project directory.
    * When install is run, the [OpenAPI Generator plugin](#integrating-with-openapi-generator) will generate the sources for connecting to the Places API.
2. run `java -jar target/location-intelligence-places-reference-app-X.X.X.jar` to start the project.
    - **Notice**: Replace `X` with version of the reference app.
    - **Example**: `java -jar target/location-intelligence-places-reference-app-1.0.0.jar`
3. Navigate to `http://localhost:8080/` in your browser.

### Reference Application Usage <a name="reference-application-usage"></a>
- Use the map to see locations in the sandbox.
- click on a marker to open more information on the right panel.
- click on right panel to center the marker on the map.

### Integrating with OpenAPI Generator <a name="integrating-with-openapi-generator"></a>
[OpenAPI Generator](https://github.com/OpenAPITools/openapi-generator) generates API client libraries from [OpenAPI Specs](https://github.com/OAI/OpenAPI-Specification). 
It provides generators and library templates for supporting multiple languages and frameworks.

See also:
* [OpenAPI Generator (maven Plugin)](https://mvnrepository.com/artifact/org.openapitools/openapi-generator-maven-plugin)
* [OpenAPI Generator (executable)](https://mvnrepository.com/artifact/org.openapitools/openapi-generator-cli)
* [CONFIG OPTIONS for java](https://github.com/OpenAPITools/openapi-generator/blob/master/docs/generators/java.md)

#### OpenAPI Generator Plugin Configuration
 
 ```xml
 <plugin>
     <groupId>org.openapitools</groupId>
     <artifactId>openapi-generator-maven-plugin</artifactId>
     <version>4.3.0</version>
     <executions>
         <execution>
             <goals>
                 <goal>generate</goal>
             </goals>
             <configuration>
                 <inputSpec>${project.basedir}/src/main/resources/places-api-spec.yaml</inputSpec>
                 <generatorName>java</generatorName>
                 <library>okhttp-gson</library>
                 <configurationFile>${project.basedir}/src/main/resources/openapi-config.json</configurationFile>
                 <generateApiTests>false</generateApiTests>
                 <generateModelTests>false</generateModelTests>
                 <configOptions>
                     <sourceFolder>src/gen/java/main</sourceFolder>
                 </configOptions>
             </configuration>
         </execution>
     </executions>
 </plugin>
 ```

## Use Cases <a name="use-cases"></a>

#### Parameters <a name="parameters"></a>
These are the parameters used for the Places API. All parameters are optional.

| Name      | Type      | Default Value      | Purpose       |
|-----------|-----------|--------------------|---------------|
| offset    | int       | 0                  | For Pagination; offset is used to offset the start of the list.        |
| limit     | int       | 25                 | For Pagination; limit is used to limit the number of entities returned |
| sort      | string    | +merchantCategoryCode **or** +industryName                 | Sorts the results + for ascending, - for descending and any property from the result. |

### MCC Codes <a name="mcc-codes"></a>
> Get a paginated list of all Merchant Category Codes. 

| Places API URL    | Method        | Parameters        | Request Model | Response model|
|-------------------|---------------|-------------------|---------------|---------------|
| /merchant-category-codes | GET | offset, limit, sort | -        | PageResponseMerchantCategoryCode |

> Get information on a specific Merchant Category code. 

| PlacesAPI URL     | Method        | Parameters        | Request Model | Response model|
|-------------------|---------------|-------------------|---------------|---------------|
| /merchant-category-codes/mcc-codes/{mccCode}   | GET | -    | int             | MerchantCategoryCode |

### Industry Codes <a name="industry-codes"></a>
> Get a paginated list of all Industry Codes. 

| PlacesAPI URL     | Method        | Parameters        | Request Model | Response model|
|-------------------|---------------|-------------------|---------------|---------------|
| /merchant-industry-codes | GET | offset, limit, sort | -        | PageResponseMerchantIndustryCode |

> Get information on a specific Industry code. 

| PlacesAPI URL     | Method        | Parameters        | Request Model | Response model|
|-------------------|---------------|-------------------|---------------|---------------|
| /merchant-industry-codes/industries/{industryCode} | GET | -    | string             | MerchantIndustryCode |

### Places <a name="places"></a>
> Get a paginated list of places that fit the search criteria. 

| PlacesAPI URL     | Method        | Parameters        | Request Model | Response model|
|-------------------|---------------|-------------------|---------------|---------------|
| /places/searches| POST     | offset, limit     | PlaceSearchRequest    | PageResponsePlaceInfo |

> Get information on a specific Industry code. 

| PlacesAPI URL     | Method        | Parameters        | Request Model | Response model|
|-------------------|---------------|-------------------|---------------|---------------|
| /places/{location_id} | GET | -    | int                          | placeInfo |

## API Reference <a name="api-reference"></a>

### Reference Application API Reference <a name="ref-app-api-reference"></a>

| Reference App URL                 | Parameters          | Reference App Usage             | Places Endpoint Used  |
|-----------------------------------|---------------------|---------------------------------|-----------------------|
|**/places/search**                 | latitude, longitude, country, distanceUnit| Search for all places 5 *distanceUnits* around lat, long | /places/searches |
|**/places/{locationId}**           | none | Lists details for a place by location id | /places/{location_id} |
|**/places/merchantCategoryCodes**  | none       | Retrieves 100 Merchant Category Codes | /merchant-category-codes        |
|**/places/merchantCategoryCodes/{mccCode}** | none | Lists details about Category Code  | /merchant-category-codes/mcc-codes/{mccCode} |
|**/places/merchantIndustryCodes**  | none | Retrieves 101 Merchant Industry Codes | /merchant-industry-codes |
|**/places/merchantIndustryCodes/{industryCode}** | none | Lists details about Industry Code | /merchant-industry-codes/industries/{industryCode} |

Example Search Request in a rest client (or browser) of your choice: `http://localhost:8080/places/search?latitude=38.7463959&longitude=-90.7475983&country=US&distanceUnit=MILE`

### Places API Reference <a name="places-api-reference"></a>

See the [API Reference](https://developer.mastercard.com/places-v2/documentation/api-reference) page in the documentation. 

| API Endpoint                  | Description                                                       |
| ----------------------------- | ----------------------------------------------------------------- |
| [Places Search](https://developer.mastercard.com/places-v2/documentation/api-reference#api)                 | Search for locations around specific coordinates or using a large set of filterable fields |
| [Get Places Details](https://developer.mastercard.com/places-v2/documentation/api-reference#api)            | List all details for a location based on location id              |
| [Get Merchant Category Codes](https://developer.mastercard.com/places-v2/documentation/api-reference#api)   | List all known Merchant Category Codes (mcc)                      |
| [Merchant Category Code Search](https://developer.mastercard.com/places-v2/documentation/api-reference#api) | Lists details about Category Code                                 |
| [Get Industry codes](https://developer.mastercard.com/places-v2/documentation/api-reference#api)            | Lists all known Industry Codes                                    |
| [Industry Codes Search](https://developer.mastercard.com/places-v2/documentation/api-reference#api)         | Lists details about Industry Code                                 |

## Authentication <a name="authentication"></a>

### Mastercard oauth1 Signer Library <a name="oauth-library"></a>
This dependency is required to properly call the API.
```xml
<dependency>
    <groupId>com.mastercard.developer</groupId>
    <artifactId>oauth1-signer</artifactId>
    <version>1.2.3</version>
</dependency>
```
[Link to the oauth1 library's Github](https://github.com/Mastercard/oauth1-signer-java)

[Looking for other languages?](https://github.com/Mastercard?q=oauth&type=&language=)

See the code used in this application to utilize the library.
```Java
Found in /src/java/com.mastercard.placesreferenceapplciation.config.ApiClientConfiguration

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
```

## Glossary <a name="glossary"></a>
|Acronym    | Meaning   |
|----------|-----------|
|mcc    | Merchant Category Codes |

## Support <a name="support"></a>
If you would like further information, please send an email to apisupport@mastercard.com

## License <a name="license"></a>
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

**Copyright © 1994-2020, All Rights Reserved by Mastercard.**
