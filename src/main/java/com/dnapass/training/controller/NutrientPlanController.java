package com.dnapass.training.controller;

import com.dnapass.training.entity.NutrientPlan;
import com.dnapass.training.service.DownStreamService;
import com.dnapass.training.service.NutrientPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
public class NutrientPlanController {
    @Autowired
    NutrientPlanService service;

    @Autowired
    DownStreamService downstreamService;


    @PostMapping("/api/nutrient-plans")
    public ResponseEntity<NutrientPlan> createNutrientPlan(@RequestBody NutrientPlan plan){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createNutrientPlan(plan));
    }

    @PutMapping("api/nutrient-plans/{id}")
    public ResponseEntity<NutrientPlan> updateNutrientPlan(@RequestBody NutrientPlan plan, @PathVariable long id){
        NutrientPlan nutrientPlan = service.updateNutrientPlan(id,plan);
        if(nutrientPlan == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(nutrientPlan);
    }

        @GetMapping("api/nutrient-plans/type/{nutrientType}")
    public ResponseEntity<List<NutrientPlan>> getNutrientPlansByType(@PathVariable String nutrientType){
        System.out.println("call came to the controller");
        return ResponseEntity.ok(service.getNutrientPlansByType(nutrientType));
    }

    @GetMapping("/api/nutrient-plans/calories")
    public ResponseEntity<List<NutrientPlan>> getNutrientPlansByCalorieRange(@RequestParam int minCalories,
                                                                             @RequestParam int maxCalories){
        if(minCalories<0){
            throw new IllegalArgumentException("calories must be more than zero");
        } else if (minCalories>maxCalories) {
            throw new IllegalArgumentException("min calories must be gretaer than max calories");
        }
        return ResponseEntity.ok(service.getNutrientPlansByCalorieRange(minCalories, maxCalories));
    }

    @GetMapping("api/nutrient-plans/{id}/macros")
    public ResponseEntity<Map<String, Double>> getMacronutrientDistribution(@PathVariable Long id){

        Map<String, Double> result = service.getMacronutrientDistribution(id);

        if(result == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);

    }

    @GetMapping("/api/nutrient-plans/active")
    public ResponseEntity<List<NutrientPlan>> getActiveDietPlansInDateRange(@RequestParam LocalDate startDate,@RequestParam LocalDate endDate){
        if(startDate.isAfter(endDate)){
            throw new IllegalArgumentException("start date must be before endDate");
        }
        List<NutrientPlan> result = service.getActiveDietPlansInDateRange(startDate, endDate);

        if(result == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getdetails")
    public String getDetails(){
        return "Called";
    }



}
