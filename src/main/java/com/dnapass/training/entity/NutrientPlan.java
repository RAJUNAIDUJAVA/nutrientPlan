package com.dnapass.training.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NutrientPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer proteinGrams;

    @Column(nullable = false)
    private Integer dailyCalories;

    @Column(nullable = false)
    private Integer fatGrams;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String nutrientType;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    private Integer carbGrams;

    public NutrientPlan(long id, String name, int dailyCalories) {
        this.id = id;
        this.name= name;
        this.dailyCalories= dailyCalories;
    }

    public NutrientPlan(long id, String name, int dailyCalories, String nutrientType) {
    }
}
