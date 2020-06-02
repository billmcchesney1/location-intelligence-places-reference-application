# Places API Reference Application
This project showcases the use case of retrieving places and its data using the Places API v2 [here](https://developer.mastercard.com/documentation/places/2)

## Frameworks Used
- Spring
- Thymeleaf
- OpenAPI Generator

## Requirements
- Java 8
- [Maven](https://maven.apache.org/download.cgi)

## Author
[Enterprise Data Services](mailto:apisupport@mastercard.com)

## Setup
1. Create an account at [Mastercard Developers](https://developer.mastercard.com).
2. Create a new project and add the `Places` API to your project. A `.p12` file is downloaded automatically.
3. Take note of the given **consumer key, keyalias, and keystore password** given upon the project creation.
4. Copy the downloaded `.p12` file to `/src/main/resources`.
5. Update the properties found under `/src/main/resources/application.properties`.
6. Run `mvn clean install` from the root of the project directory.
7. run `java -jar target/location-intelligence-places-reference-app-1.0.0.jar` to start the project.
8. Navigate to `http://localhost:8080/` in your browser.
9. Start hitting endpoints! Sample parameters have been included in the fields automatically.

## Tutorial
A tutorial can be found [here](https://developer.mastercard.com/documentation/places/2#tutorials-section) 
for setting up and using this service.

## Mastercard oauth1 Signer
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

## Places Client Library
The client library used to generate the API Calls and object models for this application can be seen in the pom.xml file
in the project's root directory.
```xml
<plugin>
    <groupId>org.openapitools</groupId>
    <artifactId>openapi-generator-maven-plugin</artifactId>
    <version>4.1.1</version>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <inputSpec>${project.basedir}/src/main/resources/places-reference.yaml</inputSpec>
                <generatorName>java</generatorName>
                <library>okhttp-gson</library>
                <configurationFile>${project.basedir}/src/main/resources/openapi-config.json</configurationFile>
                <generateApiTests>false</generateApiTests>
                <generateModelTests>false</generateModelTests>
                <generateApiTests>false</generateApiTests>
                <configOptions>
                    <sourceFolder>src/gen/java/main</sourceFolder>
                </configOptions>
            </configuration>
        </execution>
    </executions>
</plugin>
```
    
For more information on how this client generator works please consult the official [Github repository](https://github.com/OpenAPITools/openapi-generator)

## Reference Application Usage
- Select an endpoint from the dropdown menu at the top of the application
- Fill in the required parameters and click submit
- Click on 'JSON Response' to view the raw JSON returned by the API call 

## API Usage
Merchant Category Codes and Industry Codes endpoints are used to retrieve a list of codes for the specific category.

The endpoints `/mcc-codes/{mccCode}` and `/industry-codes/industries/{industry}` provide the details for the code you provide.

`/places/{locationId}` endpoint will retrieve more information about a location.

`/places/search` endpoint allows you to search for merchants within a specified area from longitude/latitude coordinates.

## License
[Apache 2 License](https://apache.org/licenses/LICENSE-2.0)

## Glossary
|Acronym    | Meaning   |
|----------|-----------|
|mcc    | Merchant Category Codes

**Copyright © 1994-2020, All Rights Reserved by Mastercard.**