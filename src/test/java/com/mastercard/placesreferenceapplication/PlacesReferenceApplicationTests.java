package com.mastercard.placesreferenceapplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PlacesReferenceApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testGetMccCodes() throws Exception {
        mvc.perform(get("/getMccCodes")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.flash().attribute("success", "Merchant Category Codes retrieved successfully!"));
    }

    @Test
    public void testGetMccByCode() throws Exception {
        mvc.perform(get("/getMccByCode")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("mccCode", "0001"))
                .andExpect(MockMvcResultMatchers.flash().attribute("success", "Category Name retrieved successfully!"));
    }

    @Test
    public void testGetIndustryCodes() throws Exception {
        mvc.perform(get("/getIndustryCodes")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.flash().attribute("success", "Industry Codes retrieved successfully!"));
    }

    @Test
    public void testGetIndustryByCode() throws Exception {
        mvc.perform(get("/getIndustryByCode")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("industryCode", "ACC"))
                .andExpect(MockMvcResultMatchers.flash().attribute("success", "Industry Name retrieved successfully!"));
    }

    @Test
    public void testGetLocationDetails() throws Exception {
        mvc.perform(get("/getLocationDetails")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.flash().attribute("success", "Place retrieved successfully!"));
    }
    @Test
    public void testPostPlacesSearch() throws Exception {
        mvc.perform(get("/postPlacesSearch")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.flash().attribute("success", "Places Search retrieved successfully!"));
    }
}
