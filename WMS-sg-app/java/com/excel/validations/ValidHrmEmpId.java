package com.excel.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = HrmEmpIdValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidHrmEmpId {
	String message() default "Invalid employee ID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    boolean isNullable() default false;  // Default to not nullable
}
