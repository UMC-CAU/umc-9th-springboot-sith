package com.example.umc9th.domain.store.dto.req;

import com.example.umc9th.global.annotation.ExistDistrict;
import com.example.umc9th.global.annotation.ExistFood;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;


public class StoreReqDTO {
    @Builder
    public record StoreReq(
            @NotBlank
            String name,
            @NotBlank
            @Size(min = 3, max= 100)
            String detailAddress,
            @ExistDistrict
            @NotNull
            Long districtId,
            @ExistFood
            @NotNull
            Long foodId){}
}
