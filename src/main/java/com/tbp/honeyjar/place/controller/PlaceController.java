package com.tbp.honeyjar.place.controller;

import com.tbp.honeyjar.place.dto.PlaceDTO;
import com.tbp.honeyjar.place.service.PlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PostMapping
    public ResponseEntity<Long> createPlace(@RequestBody PlaceDTO placeDTO) {
        Long placeId = placeService.createPlace(placeDTO);
        return ResponseEntity.ok(placeId);
    }

    @GetMapping("/{placeId}")
    public ResponseEntity<PlaceDTO> getPlaceById(@PathVariable Long placeId) {
        PlaceDTO place = placeService.getPlaceById(placeId);
        float rating = placeService.getRatingById(placeId);
        place.setRatingAvg(rating);

        return ResponseEntity.ok(place);
    }
}