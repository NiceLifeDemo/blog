package com.rczx.blog.util.restfulbody.validation;


import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdentityCardNumberValidator implements ConstraintValidator<IdentityCardNumber, String> {
    public IdentityCardNumberValidator() {
    }

    public void initialize(IdentityCardNumber constraintAnnotation) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !Objects.equals(value, "")?(new IDCard(value.toUpperCase())).validate():true;
    }
}
