package com.trizic.restapi.validator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * implement validation for enum type
 * @author Yuan
 *
 */
public class EnumStringValidator implements ConstraintValidator<EnumString, String>{

    private List<String> valueList;

    @Override
    public void initialize(EnumString constraintAnnotation) {
        valueList = new ArrayList<String>();
        for(String val : constraintAnnotation.acceptedValues())
            valueList.add(val.toUpperCase());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value!=null &&valueList.contains(value.toUpperCase());     
    }
}
