package com.example.umc9th.global.validator;

import com.example.umc9th.global.annotation.ValidPageNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageValidator implements ConstraintValidator<ValidPageNumber,Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        boolean isValid = true;

        if(value <= 0){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("페이지 번호는 양수이어야 합니다.")
                    .addConstraintViolation();
            isValid = false;
        }
        return isValid;
    }
}
