package com.example.umc9th.global.validator;

import com.example.umc9th.domain.store.exception.code.DistrictErrorCode;
import com.example.umc9th.domain.store.repository.DistrictRepository;
import com.example.umc9th.global.annotation.ExistDistrict;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DistrictExistValidator implements ConstraintValidator<ExistDistrict,Long> {

    private final DistrictRepository districtRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        boolean isValid = districtRepository.existsById(value);

        if(!isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(DistrictErrorCode.NO_DISTRICT.getMessage())
                    .addConstraintViolation();
        }
        return isValid;
    }
}
