package com.leancode.formspring.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdentificadorRegexValidador implements ConstraintValidator<IdentificadorRegex, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.matches("[0-9]{3}[-][\\d]{3}[-][\\d]{3}[-][a-zA-Z]{1}")) return true;
        else return false;
    }
    
}
