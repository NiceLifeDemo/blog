package com.rczx.blog.util.restfulbody.validation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy={StringSizeValidator.class})
@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringSize
{
  String message() default "长度不正确";
  
  int[] lengths() default {};
  
  boolean allowNull() default false;
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}



/* Location:           C:\Users\Administrator\Desktop\pavilion-1.5.2.jar

 * Qualified Name:     cn.com.zdht.pavilion.validation.StringSize

 * JD-Core Version:    0.7.0.1

 */