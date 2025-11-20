package com.example.umc9th.domain.store.converter;

import com.example.umc9th.domain.store.dto.req.StoreReqDTO;
import com.example.umc9th.domain.store.dto.res.StoreResDTO;
import com.example.umc9th.domain.store.entity.Store;

public class StoreConverter {
    public static Store toStore(StoreReqDTO.StoreReq dto){
        return Store.builder()
                .name(dto.name())
                .detailAddress(dto.detailAddress())
                .build();
    }

    public static StoreResDTO.StoreRes toStoreRes(Store store){
        return StoreResDTO.StoreRes.builder()
                .id(store.getId())
                .createdAt(store.getCreatedAt())
                .build();
    }
}
