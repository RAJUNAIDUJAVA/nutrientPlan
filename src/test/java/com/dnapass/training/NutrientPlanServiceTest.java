package com.dnapass.training;

import com.dnapass.training.entity.NutrientPlan;
import com.dnapass.training.repository.NutrientPlanRepository;
import com.dnapass.training.service.NutrientPlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NutrientPlanServiceTest {
    @InjectMocks
    private NutrientPlanService service;
    @Mock
    private NutrientPlanRepository repo;

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
    public void testCreateNutrientPlan_success(){

        when(repo.save(any(NutrientPlan.class))).thenReturn(samplePlan);

        NutrientPlan result = service.createNutrientPlan(samplePlan);
        assertNotNull(result);
        assertEquals("Weight Loss Plan",result.getName());
        verify(repo, times(1)).save(samplePlan);
    }

    @Test
    public void testCreateNutrientPlan_EndDateBeforeStartDate(){
        samplePlan.setEndDate(LocalDate.of(2025,9,1));
        assertThrows(IllegalArgumentException.class, () -> service.createNutrientPlan(samplePlan));
    }


    @Test
    public void testCreateNutrientPlan_InvalidCalories(){
        samplePlan.setEndDate(LocalDate.of(2025,9,1));
        assertThrows(IllegalArgumentException.class, () -> service.createNutrientPlan(samplePlan));
    }
    /*@Test
    public void testUpdateNutrientPlan_Success(){
        when(repo.findById(1L)).thenReturn(Optional.of(samplePlan));
        when(repo.save(any(NutrientPlan.class))).thenReturn(samplePlan);

        NutrientPlan updatedPlan = new NutrientPlan();
        updatedPlan.setName("Updated plan");
        updatedPlan.setDailyCalories(1900);

        NutrientPlan result = service.updateNutrientPlan(1L, updatedPlan);
        assertNotNull(result);
        assertEquals("Updated Plan", result.getName());
        assertEquals(1900, result.getDailyCalories());
    }

  /*   @Test
    public void testGetMacronutrientDistribution(){
        when(repo.findById(1L)).thenReturn(Optional.of(samplePlan));
        Map<String, Double> macros =service.getMacronutrientDistribution(1L);
        assertNotNull(macros);
        assertEquals(120*100.0/320,macros.get("Protein"),0.01);
        assertEquals(150*100.0/320,macros.get("Carbs"),0.01);
        assertEquals(120*100.0/320,macros.get("Fat"),0.01);
    }
        */
    /*@Test
    public void testGetNutrientPlansByType_ReturnsPlans(){
        List<NutrientPlan> mockPlans = Arrays.asList(
                new NutrientPlan(1L, "Protein Plan",1800,"Protein"),
                new NutrientPlan(2L,"Carb Plan",2000,"Carb")
        );

        when(repo.findByNutrientTypeIgnoreCase("Protein")).thenReturn(List.of(mockPlans.get(0)));

        List<NutrientPlan> result = service.getNutrientPlansByType("Protein");
        assertEquals(1,result.size());
        assertEquals("Protein Plan", result.get(0).getName());
        verify(repo,times(1)).findByNutrientTypeIgnoreCase("Protein");

    }
    /*@Test
    public void testGetNutrientPlansByCalorieRange_ReturnsPlans(){
        List<NutrientPlan> mockPlans=  Arrays.asList(
                new NutrientPlan(1L, "Protein Plan",1800,"Protein"),
                new NutrientPlan(2L,"Carb Plan",2000,"Carb")
        );

        when(repo.findByDailyCaloriesBetween(1000,2000)).thenReturn(mockPlans);
        List<NutrientPlan> result = service.getNutrientPlansByCalorieRange(1000,2000);

        assertEquals(2,result.size());
        assertEquals("Low cal Plan", result.get(0).getName());
        verify(repo, times(1)).findByDailyCaloriesBetween(1000,2000);
    }
   /* 
@Test
    public void testGetActiveDietPlansInDateRanggge_ReturnPlans() {
    LocalDate startDate = LocalDate.of(2025, 10, 1);
    LocalDate endDate = LocalDate.of(2025, 10, 31);
    List<NutrientPlan> mockPlans = Arrays.asList(
            new NutrientPlan(1L, "Keto Plan", 1600, "Fat"),
            new NutrientPlan(2L, "Vegan Plan", 1800, "Carb")
    );

    when(repo.findActivePlansInDateRange(startDate, endDate)).thenReturn(mockPlans);
    List<NutrientPlan> result = service.getActiveDietPlansInDateRange(startDate, endDate);
    assertEquals(2, result.size());
    assertEquals("Keto  Plan", result.get(0).getName());
    verify(repo, times(1)).findActivePlansInDateRange(startDate, endDate);
}
    */

@Test
    public void testGetActiveDietPlansInDateRange_EmptyResult(){
        LocalDate startDate = LocalDate.of(2025,1,1);
        LocalDate endDate = LocalDate.of(2025,1,31);
        when(repo.findActivePlansInDateRange(startDate,endDate)).thenReturn(List.of());
        List<NutrientPlan>result = service.getActiveDietPlansInDateRange(startDate,endDate);
        assertTrue(result.isEmpty());
        verify(repo, times(1)).findActivePlansInDateRange(startDate,endDate);
}




}