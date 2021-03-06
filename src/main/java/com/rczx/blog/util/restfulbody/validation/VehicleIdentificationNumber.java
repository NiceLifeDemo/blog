package com.rczx.blog.util.restfulbody.validation;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Constraint(
    validatedBy = {VehicleIdentificationNumberValidator.class}
)
public @interface VehicleIdentificationNumber {
    String message() default "格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
