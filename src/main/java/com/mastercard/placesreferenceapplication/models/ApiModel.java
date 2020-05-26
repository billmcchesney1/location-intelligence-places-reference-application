package com.mastercard.placesreferenceapplication.models;

public class ApiModel {

    //Limit and Offset are used for All Mcc and All industry codes. As well as Places search.
    private int limit = 25;
    private int offset = 0;

    //Sort used for All Mcc and All industry codes.
    private String sort = "";

    //mccCode is used for retrieving data from merchant category code.
    private String mccCode = "0001";

    //industryCode is used for retrieving data from industry code.
    private String industryCode = "";

    //locationId is used for retrieving data from location id.
    private long locationId = 266120267;

    private String searchInput = "{\n" +
            "\"distance\": 5,\n" +
            "\"place\": {\n" +
            "\"countryCode\": \"US\",\n" +
            "\"latitude\": \"38.7463959\",\n" +
            "\"longitude\": \"-90.7475983\"\n" +
            "},\n" +
            "\"radiusSearch\": true,\n" +
            "\"unit\": \"MILE\"\n" +
            "}";

    //Getters and Setters
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getMccCode() {
        return mccCode;
    }

    public void setMccCode(String mccCode) {
        this.mccCode = mccCode;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public String getSearchInput() {
        return searchInput;
    }

    public void setSearchInput(String searchInput) {
        this.searchInput = searchInput;
    }
}
