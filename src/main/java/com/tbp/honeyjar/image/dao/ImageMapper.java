package com.tbp.honeyjar.image.dao;

import com.tbp.honeyjar.image.dto.ImageDTO;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;
import java.util.Map;


@Mapper
public interface ImageMapper {

    void insertImage(ImageDTO imageDTO);

    void insertImages(List<ImageDTO> images);

    void deleteImagesByPostId(Long postId);

    void deleteImageById(Long imageId);

    void updateMainImageStatus(Map<String, Object> params);

    List<ImageDTO> findImagesByPostId(Long postId);

    ImageDTO findMainImageByPostId(Long postId);

}