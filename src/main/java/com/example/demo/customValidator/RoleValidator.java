package com.example.demo.customValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class RoleValidator implements ConstraintValidator<RoleValidation, String> {
    @Override
    public boolean isValid(String roleName, ConstraintValidatorContext ctx) {
        List<String> list = Arrays.asList("user","admin");
        return list.contains(roleName);
    }
}
