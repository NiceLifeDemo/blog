package com.rczx.blog.util.restfulbody.validation;


import java.util.ArrayList;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringSizeValidator implements ConstraintValidator<StringSize, String> {
    private StringSize stringSize;

    public StringSizeValidator() {
    }

    public void initialize(StringSize constraintAnnotation) {
        this.stringSize = constraintAnnotation;
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) {
            return this.stringSize.allowNull();
        } else {
            ArrayList integerList = new ArrayList();
            int[] var4 = this.stringSize.lengths();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                int i = var4[var6];
                integerList.add(Integer.valueOf(i));
            }

            return integerList.contains(Integer.valueOf(value.length()));
        }
    }
}
