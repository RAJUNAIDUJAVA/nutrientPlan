package com.dnapass.training;

import com.dnapass.training.controller.NutrientPlanController;
import com.dnapass.training.entity.NutrientPlan;
import com.dnapass.training.service.NutrientPlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NutrientPlanController.class)
@ExtendWith(MockitoExtension.class)
class NutrientPlanControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private NutrientPlanController controller;
    @MockBean
    private NutrientPlanService service;


    private NutrientPlan samplePlan;
    @BeforeEach
    public void setUp(){
        samplePlan = new NutrientPlan();
        samplePlan.setId(1L);
        samplePlan.setDailyCalories(1800);
        samplePlan.setName("Weight Loss Plan");
        samplePlan.setProteinGrams(120);
        samplePlan.setCarbGrams(150);
        samplePlan.setFatGrams(50);
        samplePlan.setStartDate(LocalDate.of(2025,10,1));
        samplePlan.setEndDate(LocalDate.of(2025,12,01));
        samplePlan.setNutrientType("Protein");
        samplePlan.setIsActive(true);

    }
    @Test
    public void testCreateNutrientPlan() throws Exception {
        when(service.createNutrientPlan(any(NutrientPlan.class))).thenReturn(samplePlan);
        mockMvc.perform(post("/api/nutrient-plans")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Weight Loss Plan\",\"dailyCalories\":1800,\"proteinGrams\":120,\"carbGrams\":150,\"fatGrams\":50,\"startDate\":\"2025-10-01\",\"endDate\":\"2025-12-01\",\"nutrientType\":\"Protein\",\"isActive\":true}"))
                .andDo(result -> {
                    System.out.println("result is"+ result.getResponse().getContentAsString());
                }).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Weight Loss Plan"));

    }
    @Test
    public void testUpdateNutrientPlan_Success() throws Exception {
        when(service.updateNutrientPlan(eq(1L),any(NutrientPlan.class))).thenReturn(samplePlan);
        mockMvc.perform(put("/api/nutrient-plans/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Weight Loss Plan\",\"dailyCalories\":1800}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Weight Loss Plan"));
    }

    @Test
    public void testUpdateNutrientPlan_NotFound() throws Exception{
        when(service.updateNutrientPlan(eq(2L),any(NutrientPlan.class))).thenReturn(null);
        mockMvc.perform(put("/api/nutrient-plans/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Non Existing plan\",\"dailyCalories\":1800}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetNutrientPlansByType() throws Exception {
        when(service.getNutrientPlansByType("Protein")).thenReturn(Arrays.asList(samplePlan));
        mockMvc.perform(get("/api/nutrient-plans/type/Protein"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nutrientType").value("Protein"));
    }
@Test
    public void testGetNutrientPlansByCalorieRange_ValidRange_ReturnsOk() throws Exception{
        List<NutrientPlan> mockPlans = Arrays.asList(
                new NutrientPlan(1l,"Plan A",1200),
                new NutrientPlan(2L, "Plan B",1500)
        );

        when(service.getNutrientPlansByCalorieRange(1000, 1600)).thenReturn(mockPlans);

        mockMvc.perform(get("/api/nutrient-plans/calories")
                .param("minCalories", "1000")
                .param("maxCalories", "1600")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Plan A"))
                .andExpect(jsonPath("$[1].dailyCalories").value(1500));
    }

    @Test
    public void testGetNutrientPlansByCalorieRange_Empty_Result_ReturnsOkWithEmptyList() throws Exception{
        when(service.getNutrientPlansByCalorieRange(1000,1200)).thenReturn(List.of());
        mockMvc.perform(get("/api/nutrient-plans/calories")
                .param("minCalories", "1000")
                .param("maxCalories", "1600")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
    @Test
    public void testGetMacronutrientDistribution_ValidId_ReturnsOk() throws Exception{
        Map<String, Double> mockMacros = Map.of(
                "protein", 40.0,"carbs", 35.0,"fat",25.0
        );
        when(service.getMacronutrientDistribution(1L)).thenReturn(mockMacros);

        mockMvc.perform(get("/api/nutrient-plans/1/macros")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.protein").value(40.0))
                .andExpect(jsonPath("$.carbs").value(35.0))
                .andExpect(jsonPath("$.fat").value(25.0));
    }
@Test
    public void testGetMacronutrientDistribution_InvalidReturnsNotFound() throws Exception{
        when(service.getMacronutrientDistribution(99L)).thenReturn(null);

        mockMvc.perform(get("/api/nutrient-plans/99/macros")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
@Test
    public void testGetActiveDietPlansInDateRange_validRangeReturnsOk() throws Exception{
        LocalDate startDate = LocalDate.of(2025,1,1);
        LocalDate endDate = LocalDate.of(2025,12,31);

        List<NutrientPlan> mockPlans = Arrays.asList(
                new NutrientPlan(1L,"Weight Loss Plan",1500),
                new NutrientPlan(2L, "Muscle Gain Plan", 2200)
        );

        when(service.getActiveDietPlansInDateRange(startDate,endDate)).thenReturn(mockPlans);

        mockMvc.perform(get("/api/nutrient-plans/active")
                .param("startDate","2025-01-01")
                .param("endDate", "2025-12-31")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Weight Loss Plan"))
                .andExpect(jsonPath("$[1].dailyCalories").value(2200));
    }
    @Test
    public void testGetActivePlansInDateRange_EmptyResult_ReturnsOkWithEmptyList() throws Exception{
        LocalDate startDate = LocalDate.of(2025,5,1);
        LocalDate endDate = LocalDate.of(2025,5,31);

        when(service.getActiveDietPlansInDateRange(startDate,endDate)).thenReturn(List.of());

        mockMvc.perform(get("/api/nutrient-plans/active")
                .param("startDate", "2025-05-01")
                .param("endDate", "2025-05-31")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

}