package com.mastercard.placesreferenceapplication.controller;

import com.mastercard.placesreferenceapplication.services.PlaceService;
import org.junit.Test;
import org.junit.runner.RunWith;

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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PlacesController.class)
public class PlacesControllerTest {

    @MockBean
    private PlaceService mockPlaceService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void testIndexPage() throws Exception {
        mvc.perform(get("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasNoErrors());
    }

    @Test
    public void testMerchantCategoryCodes_successfulRequest() throws Exception {
        PagedMerchantCategoryCode mockedReturn = new PagedMerchantCategoryCode();
        mockedReturn.setTotal(1000);
        given(mockPlaceService.getMerchantCategoryCodes(anyInt(), anyInt())).willReturn(mockedReturn);

        mvc.perform(get("/places/merchantCategoryCodes")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("total").value(1000));
    }

    @Test
    public void testMerchantCategoryCodes_ErrorHandling() throws Exception {
        doThrow(new ApiException(400, "Something went wrong")).when(mockPlaceService).getMerchantCategoryCodes(anyInt(), anyInt());
        mvc.perform(get("/places/merchantCategoryCodes")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Something went wrong"));
    }

    @Test
    public void testMerchantCategoryByCode_successfulRequest() throws Exception {
        MerchantCategoryCode mockedReturn = new MerchantCategoryCode();
        mockedReturn.setMerchantCategoryName("Mocked Category");
        given(mockPlaceService.getMerchantCategoryByCode(anyString())).willReturn(mockedReturn);

        mvc.perform(get("/places/merchantCategoryCodes/12345")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("merchantCategoryName").value("Mocked Category"));
    }

    @Test
    public void testMerchantCategoryByCode_ErrorHandling() throws Exception {
        doThrow(new ApiException(400, "Something went wrong")).when(mockPlaceService).getMerchantCategoryByCode(anyString());
        mvc.perform(get("/places/merchantCategoryCodes/1234")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Something went wrong"));
    }

    @Test
    public void testMerchantIndustryCodes_successfulRequest() throws Exception {
        PagedMerchantIndustryCode mockedReturn = new PagedMerchantIndustryCode();
        mockedReturn.setTotal(1000);
        given(mockPlaceService.getMerchantIndustryCodes(anyInt(), anyInt())).willReturn(mockedReturn);

        mvc.perform(get("/places/merchantIndustryCodes")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("total").value(1000));
    }

    @Test
    public void testMerchantIndustryCodes_ErrorHandling() throws Exception {
        doThrow(new ApiException(400, "Something went wrong")).when(mockPlaceService).getMerchantIndustryCodes(anyInt(), anyInt());
        mvc.perform(get("/places/merchantIndustryCodes")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Something went wrong"));
    }

    @Test
    public void testGetIndustryByCode_successfulRequest() throws Exception {
        MerchantIndustryCode mockedReturn = new MerchantIndustryCode();
        mockedReturn.setIndustry("Mocked Industry");
        given(mockPlaceService.getMerchantIndustryByCode(anyString())).willReturn(mockedReturn);

        mvc.perform(get("/places/merchantIndustryCodes/ACC")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("industry").value("Mocked Industry"));
    }

    @Test
    public void testGetIndustryByCode_ErrorHandling() throws Exception {
        doThrow(new ApiException(400, "Something went wrong")).when(mockPlaceService).getMerchantIndustryByCode(anyString());
        mvc.perform(get("/places/merchantIndustryCodes/ACC")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Something went wrong"));
    }


    @Test
    public void testLocationDetailsByLocationId_successfulRequest() throws Exception {
        PlaceInfo mockedReturn = new PlaceInfo();
        mockedReturn.setLocationId(123456789L);
        given(mockPlaceService.getPlaceDetailsByLocationId(anyLong())).willReturn(mockedReturn);

        mvc.perform(get("/places/123456789")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("locationId").value(123456789L));
    }

    @Test
    public void testLocationDetailsByLocationId_ErrorHandling() throws Exception {
        doThrow(new ApiException(400, "Something went wrong")).when(mockPlaceService).getPlaceDetailsByLocationId(anyLong());
        mvc.perform(get("/places/123456789")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Something went wrong"));
    }

    @Test
    public void testPlacesSearch_successfulRequest() throws Exception {
        PlaceFilter placeFilter = new PlaceFilter();
        placeFilter.setLongitude("1.0");
        placeFilter.setLatitude("1.0");
        placeFilter.setCountryCode("US");
        PlaceSearchRequest placeSearchRequest = new PlaceSearchRequest();
        placeSearchRequest.setPlace(placeFilter);
        placeSearchRequest.setDistance(5L);
        placeSearchRequest.setRadiusSearch(true);
        placeSearchRequest.setUnit(PlaceSearchRequest.UnitEnum.KM);

        PagedPlaceInfo mockedReturn = new PagedPlaceInfo();
        mockedReturn.setTotal(1000);

        given(mockPlaceService.getPlacesSearch(placeSearchRequest, 20, 0)).willReturn(mockedReturn);

        mvc.perform(get("/places/search?latitude=1.0&longitude=1.0&distanceUnit=KM&country=US")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("total").value(1000));
    }

    @Test
    public void testPlacesSearch_ErrorHandling() throws Exception {
        doThrow(new ApiException(400, "Something went wrong")).when(mockPlaceService).getPlacesSearch(any(PlaceSearchRequest.class), anyInt(), anyInt());
        mvc.perform(get("/places/search?latitude=1.0&longitude=1.0&distanceUnit=KM&country=US")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Something went wrong"));
    }
}
