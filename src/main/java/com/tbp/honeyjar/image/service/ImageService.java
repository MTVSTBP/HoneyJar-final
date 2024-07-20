package com.tbp.honeyjar.image.service;

import com.tbp.honeyjar.image.dao.ImageMapper;
import com.tbp.honeyjar.image.dto.ImageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageMapper imageMapper;

    @Transactional
    public void saveImages(List<String> imageUrls, Long userId, Long postId, String mainImageUrl) {
        List<ImageDTO> images = imageUrls.stream()
                .map(url -> new ImageDTO(null, url, userId, postId, url.equals(mainImageUrl)))
                .collect(Collectors.toList());
        imageMapper.insertImages(images);
    }

    public List<ImageDTO> getImagesByPostId(Long postId) {
        return imageMapper.findImagesByPostId(postId);
    }
}
