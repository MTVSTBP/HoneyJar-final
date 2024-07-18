package com.tbp.honeyjar.image.controller;

import com.tbp.honeyjar.image.dto.ImageDTO;
import com.tbp.honeyjar.image.service.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/post/{postId}")
    public List<ImageDTO> getImagesByPostId(@PathVariable Long postId) {
        return imageService.getImagesByPostId(postId);
    }
}
