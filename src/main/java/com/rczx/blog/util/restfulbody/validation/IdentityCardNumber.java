package com.rczx.blog.util.restfulbody.validation;

import com.zm.system.utils.restfulbody.validation.IdentityCardNumberValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(
    validatedBy = {IdentityCardNumberValidator.class}
)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IdentityCardNumber {
    String message() default "格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
