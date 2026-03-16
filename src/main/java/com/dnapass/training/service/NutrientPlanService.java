package com.dnapass.training.service;

import com.dnapass.training.entity.NutrientPlan;
import com.dnapass.training.repository.NutrientPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NutrientPlanService {
    @Autowired
    NutrientPlanRepository repository;

    public NutrientPlan createNutrientPlan(NutrientPlan plan){
        if(plan.getDailyCalories()<=0){
            throw new IllegalArgumentException("dailyCalories must be greter than 0");
        }

        if(plan.getEndDate().isBefore(plan.getStartDate())){
            throw new IllegalArgumentException("end date must be after start date");
        }
        return repository.save(plan);
    }

    public NutrientPlan updateNutrientPlan(Long id, NutrientPlan nutrientPlan){
        Optional<NutrientPlan> getObject = repository.findById(id);
        return getObject.map(nutrientPlan1 -> {
            nutrientPlan1.setNutrientType(nutrientPlan.getNutrientType());
            nutrientPlan1.setCarbGrams(nutrientPlan.getCarbGrams());
            nutrientPlan1.setDailyCalories(nutrientPlan.getDailyCalories());
            nutrientPlan1.setEndDate(nutrientPlan.getEndDate());
            nutrientPlan1.setFatGrams(nutrientPlan.getFatGrams());
            nutrientPlan1.setIsActive(nutrientPlan.getIsActive());
            nutrientPlan1.setName(nutrientPlan.getName());
            nutrientPlan1.setStartDate(nutrientPlan.getStartDate());
            nutrientPlan1.setProteinGrams(nutrientPlan.getProteinGrams());
            nutrientPlan1.setDescription(nutrientPlan.getDescription());

            return repository.save(nutrientPlan1);


        }).orElse(null);
    }

    public List<NutrientPlan> getNutrientPlansByType(String nutrientType){
        return repository.findByNutrientTypeIgnoreCase(nutrientType);
    }

    public List<NutrientPlan>getNutrientPlansByCalorieRange(int minCalories, int maxCalories){
        return repository.findByDailyCaloriesBetween(minCalories,maxCalories);
    }

    public Map<String, Double> getMacronutrientDistribution(Long id){
        Optional<NutrientPlan> plan = repository.findById(id);

        return plan.map(nutrientPlan -> {
            Integer totalNutrientValue = nutrientPlan.getProteinGrams()+nutrientPlan.getCarbGrams()+
                    nutrientPlan.getFatGrams();

            Map<String, Double> result = new HashMap<>();

            result.put("Protein", ((double)nutrientPlan.getProteinGrams()/totalNutrientValue)*100);
            result.put("Carbs", ((double)nutrientPlan.getCarbGrams()/totalNutrientValue)*100);
            result.put("Fat",((double)nutrientPlan.getFatGrams()/totalNutrientValue)*100);
            return result;
        }).orElse(null);
    }

    public List<NutrientPlan> getActiveDietPlansInDateRange(LocalDate startDate, LocalDate endDate){
        return repository.findActivePlansInDateRange(startDate, endDate);
    }
}
