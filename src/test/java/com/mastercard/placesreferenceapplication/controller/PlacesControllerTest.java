package com.mastercard.placesreferenceapplication.controller;

import com.mastercard.placesreferenceapplication.services.PlaceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openapitools.client.ApiException;
import org.openapitools.client.model.PlaceSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.*;
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
    public void testGetIndexPage() throws Exception {
        mvc.perform(get("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasNoErrors());
    }

    @Test
    public void testGetMccCodes_successfulRequest() throws Exception {
        mvc.perform(get("/getMccCodes")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.flash().attribute("success", "Merchant Category Codes retrieved successfully!"));
    }

    @Test
    public void testGetMccCodes_ErrorHandling() throws Exception {
        doThrow(new ApiException("Something went wrong")).when(mockPlaceService).getMccCodes(anyInt(), anyInt(),anyString());
        mvc.perform(get("/getMccCodes")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(flash().attribute("error", "Something went wrong"));
    }
    @Test
    public void testGetMccByCode_successfulRequest() throws Exception {
        mvc.perform(get("/getMccByCode")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("mccCode", "0001"))
                .andExpect(MockMvcResultMatchers.flash().attribute("success", "Category Name retrieved successfully!"));
    }

    @Test
    public void testGetMccByCode_ErrorHandling() throws Exception {
        doThrow(new ApiException("Something went wrong")).when(mockPlaceService).getMccByCode(anyString());
        mvc.perform(get("/getMccByCode")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("mccCode", "0001"))
                .andExpect(flash().attribute("error", "Something went wrong"));
    }

    @Test
    public void testGetIndustryCodes_successfulRequest() throws Exception {
        mvc.perform(get("/getIndustryCodes")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.flash().attribute("success", "Industry Codes retrieved successfully!"));
    }

    @Test
    public void testGetIndustryCodes_ErrorHandling() throws Exception {
        doThrow(new ApiException("Something went wrong")).when(mockPlaceService).getIndustryCodes(anyInt(), anyInt(), anyString());
        mvc.perform(get("/getIndustryCodes")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(flash().attribute("error", "Something went wrong"));
    }

    @Test
    public void testGetIndustryByCode_successfulRequest() throws Exception {
        mvc.perform(get("/getIndustryByCode")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("industryCode", "ACC"))
                .andExpect(MockMvcResultMatchers.flash().attribute("success", "Industry Name retrieved successfully!"));
    }

    @Test
    public void testGetIndustryByCode_ErrorHandling() throws Exception {
        doThrow(new ApiException("Something went wrong")).when(mockPlaceService).getIndustryByCode(anyString());
        mvc.perform(get("/getIndustryByCode")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("industryCode", "ACC"))
                .andExpect(flash().attribute("error", "Something went wrong"));
    }


    @Test
    public void testGetLocationDetails_successfulRequest() throws Exception {
        mvc.perform(get("/getLocationDetails")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.flash().attribute("success", "Place retrieved successfully!"));
    }

    @Test
    public void testGetLocationDetails_ErrorHandling() throws Exception {
        doThrow(new ApiException("Something went wrong")).when(mockPlaceService).getLocationDetails(anyLong());
        mvc.perform(get("/getLocationDetails")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(flash().attribute("error", "Something went wrong"));
    }

    @Test
    public void testPostPlacesSearch_successfulRequest() throws Exception {
        mvc.perform(get("/postPlacesSearch")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.flash().attribute("success", "Places Search retrieved successfully!"));
    }

    @Test
    public void testPostPlacesSearch_ErrorHandling() throws Exception {
        doThrow(new ApiException("Something went wrong")).when(mockPlaceService).postPlacesSearch(any(PlaceSearchRequest.class), anyInt(), anyInt());
        mvc.perform(get("/postPlacesSearch")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(flash().attribute("error", "Something went wrong"));
    }
}
