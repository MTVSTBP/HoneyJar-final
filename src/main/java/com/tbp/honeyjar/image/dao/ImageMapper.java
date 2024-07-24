package com.tbp.honeyjar.image.dao;

import com.tbp.honeyjar.image.dto.ImageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


//@Mapper
//public interface ImageMapper {
//    void insertImage(ImageDTO image);
//
//    void insertImages(@Param("images") List<ImageDTO> images);
//
//    List<ImageDTO> findImagesByPostId(Long postId);
//
//    ImageDTO findMainImageByPostId(Long postId);
//
//    void deleteImagesByPostId(Long postId);
//
//}

//@Mapper
//public interface ImageMapper {
//
//    void insertImage(ImageDTO image);
//
//    void insertImages(List<ImageDTO> images);
//
//    List<ImageDTO> findImagesByPostId(Long postId);
//
//    ImageDTO findMainImageByPostId(Long postId);
//
//    void deleteImagesByPostId(Long postId);
//
//    void deleteImageById(Long imageId);
//}

@Mapper
public interface ImageMapper {

    void insertImage(ImageDTO imageDTO);

    void insertImages(List<ImageDTO> images);

    void deleteImagesByPostId(Long postId);

    void deleteImage(Long imageId);

    List<ImageDTO> findImagesByPostId(Long postId);

    ImageDTO findMainImageByPostId(Long postId);
}