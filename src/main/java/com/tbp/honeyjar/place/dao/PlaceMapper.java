package com.tbp.honeyjar.place.dao;

import com.tbp.honeyjar.place.dto.PlaceDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PlaceMapper {

    void insertPlace(PlaceDTO placeDTO);

    PlaceDTO findPlaceById(Long placeId);

    void updatePlace(PlaceDTO placeDTO);

    void deletePlaceById(Long placeId);

    float getRatingById(Long placeId);
}