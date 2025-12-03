package com.example.umc9th.global.annotation;

import com.example.umc9th.global.validator.PageValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PageValidator.class)
@Target({ElementType.FIELD,ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPageNumber {
    String message() default "페이지 번호가 유효하지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
