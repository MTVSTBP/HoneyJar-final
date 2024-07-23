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
@RequiredArgsConstructor
public class ImageService {

    private final ImageMapper imageMapper;

    @Transactional
    public void saveMainImage(String mainImageUrl, Long userId, Long postId) {
        ImageDTO mainImage = new ImageDTO();
        mainImage.setUrl(mainImageUrl);
        mainImage.setUserId(userId);
        mainImage.setPostId(postId);
        mainImage.setMain(true);
        imageMapper.insertImage(mainImage);
    }

    @Transactional
    public void saveImages(List<String> imageUrls, Long userId, Long postId) {
        List<ImageDTO> images = new ArrayList<>();
        for (String imageUrl : imageUrls) {
            ImageDTO image = new ImageDTO();
            image.setUrl(imageUrl);
            image.setUserId(userId);
            image.setPostId(postId);
            image.setMain(false);
            images.add(image);
        }
        imageMapper.insertImages(images);
    }

    public List<ImageDTO> getImagesByPostId(Long postId) {
        return imageMapper.findImagesByPostId(postId);
    }
}