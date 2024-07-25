package com.tbp.honeyjar.place.service;

import com.tbp.honeyjar.place.dao.PlaceMapper;
import org.springframework.stereotype.Service;
import com.tbp.honeyjar.place.dto.PlaceDTO;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlaceService {

    private final PlaceMapper placeMapper;

    public PlaceService(PlaceMapper placeMapper) {
        this.placeMapper = placeMapper;
    }

    @Transactional
    public Long createPlace(PlaceDTO placeDTO) {
        placeMapper.insertPlace(placeDTO);
        return placeDTO.getPlaceId();
    }

    public PlaceDTO getPlaceById(Long placeId) {
        return placeMapper.findPlaceById(placeId);
    }

    public float getRatingById(Long placeId) {
        return placeMapper.getRatingById(placeId);
    }
}