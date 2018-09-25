package com.rczx.blog.util.restfulbody.validation;

import com.zm.system.utils.restfulbody.validation.PhoneNumberValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(
    validatedBy = {PhoneNumberValidator.class}
)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {
    String message() default "格式错误";

    boolean allowEmpty() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
