package com.dnapass.training.annotations;

import jakarta.validation.Constraint;
import org.hibernate.annotations.TargetEmbeddable;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CaloriesValidatorClass.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CaloriesValidator {
}
