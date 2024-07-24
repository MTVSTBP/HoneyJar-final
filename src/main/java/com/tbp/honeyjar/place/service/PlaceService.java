package com.tbp.honeyjar.place.service;

import com.tbp.honeyjar.place.dao.PlaceMapper;
import org.springframework.stereotype.Service;
import com.tbp.honeyjar.place.dto.PlaceDTO;
import org.springframework.transaction.annotation.Transactional;

//@Service
//public class PlaceService {
//
//    private final PlaceMapper placeMapper;
//
//    public PlaceService(PlaceMapper placeMapper) {
//        this.placeMapper = placeMapper;
//    }
//
//    @Transactional
//    public Long createPlace(PlaceDTO placeDTO) {
//        placeMapper.insertPlace(placeDTO);
//        return placeDTO.getPlaceId();
//    }
//
//    public PlaceDTO getPlaceById(Long placeId) {
//        return placeMapper.findPlaceById(placeId);
//    }
//
//    @Transactional
//    public void updatePlace(PlaceDTO placeDTO) {
//        if (placeDTO.getPlaceId() == null) {
//            throw new IllegalArgumentException("placeId가 설정되지 않았습니다.");
//        }
//
//        // Avoid duplicate entries in the place table
//        PlaceDTO existingPlace = placeMapper.findPlaceByCoordinatesAndAddress(
//                placeDTO.getxCoordinate(), placeDTO.getyCoordinate(), placeDTO.getRoadAddressName());
//        if (existingPlace != null && !existingPlace.getPlaceId().equals(placeDTO.getPlaceId())) {
//            throw new IllegalArgumentException("Duplicate place data detected.");
//        }
//
//        placeMapper.updatePlace(placeDTO);
//    }
//}

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

    @Transactional
    public void updatePlace(PlaceDTO placeDTO) {
        System.out.println("Before update: " + placeMapper.findPlaceById(placeDTO.getPlaceId()));
        System.out.println("placeDTO = " + placeDTO);
        placeMapper.updatePlace(placeDTO);
        System.out.println("After update: " + placeMapper.findPlaceById(placeDTO.getPlaceId()));
    }

    @Transactional
    public void deletePlace(Long placeId) {
        placeMapper.deletePlaceById(placeId);
    }
}
