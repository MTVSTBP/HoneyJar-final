package com.tbp.honeyjar.place.dao;

import com.tbp.honeyjar.place.dto.PlaceDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlaceMapper {

    // Place 삽입
    void insertPlace(PlaceDTO placeDTO);

    // Place 조회
    PlaceDTO findPlaceById(Long placeId);

    // Place 수정
    void updatePlace(PlaceDTO placeDTO);
}