package com.dnapass.training.repository;

import com.dnapass.training.entity.NutrientPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NutrientPlanRepository extends JpaRepository<NutrientPlan,Long> {

    List<NutrientPlan> findByNutrientTypeIgnoreCase(String nutrientType);
    @Query(value = "select * from NUTRIENT_PLAN where daily_calories between :startRange and :endRange", nativeQuery = true)
    List<NutrientPlan> findByDailyCaloriesBetween(@Param("endRange") int startDate, @Param("endRange") int endRange);

    @Query(value = "select * from NUTRIENT_PLAN where start_date>=:endDate and end_date >=:startDate", nativeQuery = true)
    List<NutrientPlan> findActivePlansInDateRange(LocalDate startDate, LocalDate endDate);
}
