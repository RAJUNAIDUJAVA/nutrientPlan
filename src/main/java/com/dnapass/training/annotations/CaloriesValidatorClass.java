package com.dnapass.training.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CaloriesValidatorClass implements ConstraintValidator<CaloriesValidator, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        if(value >50){
            return true;
        }
        else {
            return false;
        }
    }
}
