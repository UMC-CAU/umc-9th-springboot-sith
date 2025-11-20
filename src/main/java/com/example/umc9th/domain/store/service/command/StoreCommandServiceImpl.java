package com.example.umc9th.domain.store.service.command;

import com.example.umc9th.domain.member.entity.Food;
import com.example.umc9th.domain.member.exception.MemberException;
import com.example.umc9th.domain.member.exception.code.FoodErrorCode;
import com.example.umc9th.domain.member.repository.FoodRepository;
import com.example.umc9th.domain.store.converter.StoreConverter;
import com.example.umc9th.domain.store.dto.req.StoreReqDTO;
import com.example.umc9th.domain.store.dto.res.StoreResDTO;
import com.example.umc9th.domain.store.entity.District;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.exception.StoreException;
import com.example.umc9th.domain.store.exception.code.DistrictErrorCode;
import com.example.umc9th.domain.store.repository.DistrictRepository;
import com.example.umc9th.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class StoreCommandServiceImpl implements StoreCommandService {

    private final StoreRepository storeRepository;
    private final DistrictRepository districtRepository;
    private final FoodRepository foodRepository;

    @Override
    public StoreResDTO.StoreRes createStore(StoreReqDTO.StoreReq dto){

        Store store = StoreConverter.toStore(dto);
        District district = districtRepository.findById(dto.districtId())
                .orElseThrow(()->new StoreException(DistrictErrorCode.NO_DISTRICT));
        Food food = foodRepository.findById(dto.foodId())
                .orElseThrow(()->new MemberException(FoodErrorCode.NO_FOOD));

        store.setDistrict(district);
        store.setFood(food);

        String[] str = String.valueOf(LocalDateTime.now()).split("\\.");
        store.setBossNumber(str[1]);

        storeRepository.save(store);

        return StoreConverter.toStoreRes(store);
    }
}
