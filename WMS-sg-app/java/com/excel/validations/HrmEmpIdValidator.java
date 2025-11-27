package com.excel.validations;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HrmEmpIdValidator implements ConstraintValidator<ValidHrmEmpId, String>{

	private static final String EMP_ID_PATTERN = "^E\\d{5}$";
    private final Pattern pattern = Pattern.compile(EMP_ID_PATTERN);
    private boolean isNullable;

    @Override
    public void initialize(ValidHrmEmpId constraintAnnotation) {
    	this.isNullable = constraintAnnotation.isNullable();
    }

    @Override
    public boolean isValid(String empId, ConstraintValidatorContext context) {
    	if (empId == null) {
            return isNullable;  // Return true if null values are allowed
        }
        return pattern.matcher(empId).matches();
    }

}
