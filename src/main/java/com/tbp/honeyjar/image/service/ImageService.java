package com.tbp.honeyjar.image.service;

import com.tbp.honeyjar.image.dao.ImageMapper;
import com.tbp.honeyjar.image.dto.ImageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



@Service
public class ImageService {

    private final ImageMapper imageMapper;

    public ImageService(ImageMapper imageMapper) {
        this.imageMapper = imageMapper;
    }

    @Transactional
    public void saveImages(List<ImageDTO> images) {
        imageMapper.insertImages(images);
    }

    @Transactional
    public void saveMainImage(ImageDTO mainImage) {
        imageMapper.insertImage(mainImage);
    }

    public List<ImageDTO> getImagesByPostId(Long postId) {
        return imageMapper.findImagesByPostId(postId);
    }
}