package com.dnapass.training.controller.advice;

import com.dnapass.training.exception.NutrientPlanNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NutrientPlanControllerAdvice {

    @ExceptionHandler(NutrientPlanNotFoundException.class)
    public ResponseEntity<Void> handleNotFoundException(RuntimeException exception){
     return ResponseEntity.notFound().build();
    }
}
