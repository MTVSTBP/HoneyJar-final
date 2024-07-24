package com.tbp.honeyjar.image.service;

import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import com.tbp.honeyjar.global.Firebase.FireBaseService;
import com.tbp.honeyjar.image.dao.ImageMapper;
import com.tbp.honeyjar.image.dto.ImageDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



//@Service
//public class ImageService {
//
//    private final ImageMapper imageMapper;
//    private final FireBaseService fireBaseService;
//
//    public ImageService(ImageMapper imageMapper, FireBaseService fireBaseService) {
//        this.imageMapper = imageMapper;
//        this.fireBaseService = fireBaseService;
//    }
//
//    @Transactional
//    public void saveMainImage(String mainImageUrl, Long userId, Long postId) {
//        ImageDTO mainImage = new ImageDTO();
//        mainImage.setUrl(mainImageUrl);
//        mainImage.setUserId(userId);
//        mainImage.setPostId(postId);
//        mainImage.setMain(true);
//        imageMapper.insertImage(mainImage);
//    }
//
//    @Transactional
//    public void saveImages(List<String> imageUrls, Long userId, Long postId) {
//        List<ImageDTO> images = new ArrayList<>();
//        for (String imageUrl : imageUrls) {
//            ImageDTO image = new ImageDTO();
//            image.setUrl(imageUrl);
//            image.setUserId(userId);
//            image.setPostId(postId);
//            image.setMain(false);
//            images.add(image);
//        }
//        imageMapper.insertImages(images);
//    }
//
//    public List<ImageDTO> getImagesByPostId(Long postId) {
//        return imageMapper.findImagesByPostId(postId);
//    }
//
//    public String getMainImageUrl(Long postId) {
//        ImageDTO mainImage = imageMapper.findMainImageByPostId(postId);
//        if (mainImage != null) {
//            try {
//                return fireBaseService.getFileUrl(mainImage.getUrl());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//}

//@Service
//public class ImageService {
//
//    private final ImageMapper imageMapper;
//    private final FireBaseService fireBaseService;
//
//    public ImageService(ImageMapper imageMapper, FireBaseService fireBaseService) {
//        this.imageMapper = imageMapper;
//        this.fireBaseService = fireBaseService;
//    }
//
//    @Transactional
//    public void saveMainImage(String mainImageUrl, Long userId, Long postId) {
//        ImageDTO mainImage = new ImageDTO();
//        mainImage.setUrl(mainImageUrl);
//        mainImage.setUserId(userId);
//        mainImage.setPostId(postId);
//        mainImage.setMain(true);
//        imageMapper.insertImage(mainImage);
//    }
//
//    @Transactional
//    public void saveImages(List<String> imageUrls, Long userId, Long postId) {
//        List<ImageDTO> images = new ArrayList<>();
//        for (String imageUrl : imageUrls) {
//            ImageDTO image = new ImageDTO();
//            image.setUrl(imageUrl);
//            image.setUserId(userId);
//            image.setPostId(postId);
//            image.setMain(false);
//            images.add(image);
//        }
//        imageMapper.insertImages(images);
//    }
//
//    @Transactional
//    public void deleteImagesByPostId(Long postId) {
//        imageMapper.deleteImagesByPostId(postId);
//    }
//
//    public List<ImageDTO> getImagesByPostId(Long postId) {
//        return imageMapper.findImagesByPostId(postId);
//    }
//
//    public String getMainImageUrl(Long postId) {
//        ImageDTO mainImage = imageMapper.findMainImageByPostId(postId);
//        if (mainImage != null) {
//            try {
//                return fireBaseService.getFileUrl(mainImage.getUrl());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//}

//@Service
//public class ImageService {
//
//    private final ImageMapper imageMapper;
//    private final FireBaseService fireBaseService;
//
//    public ImageService(ImageMapper imageMapper, FireBaseService fireBaseService) {
//        this.imageMapper = imageMapper;
//        this.fireBaseService = fireBaseService;
//    }
//
//    @Transactional
//    public void saveMainImage(String mainImageUrl, Long userId, Long postId) {
//        ImageDTO mainImage = new ImageDTO();
//        mainImage.setUrl(mainImageUrl);
//        mainImage.setUserId(userId);
//        mainImage.setPostId(postId);
//        mainImage.setMain(true);
//        imageMapper.insertImage(mainImage);
//    }
//
//    @Transactional
//    public void saveImages(List<String> imageUrls, Long userId, Long postId) {
//        if (CollectionUtils.isEmpty(imageUrls)) {
//            return; // imageUrls가 null이거나 비어있으면 아무 작업도 하지 않음
//        }
//
//        List<ImageDTO> images = new ArrayList<>();
//        for (String imageUrl : imageUrls) {
//            ImageDTO image = new ImageDTO();
//            image.setUrl(imageUrl);
//            image.setUserId(userId);
//            image.setPostId(postId);
//            image.setMain(false);
//            images.add(image);
//        }
//        imageMapper.insertImages(images);
//    }
//
//    @Transactional
//    public void deleteImagesByPostId(Long postId) {
//        imageMapper.deleteImagesByPostId(postId);
//    }
//
//    public List<ImageDTO> getImagesByPostId(Long postId) {
//        return imageMapper.findImagesByPostId(postId);
//    }
//
//    public String getMainImageUrl(Long postId) {
//        ImageDTO mainImage = imageMapper.findMainImageByPostId(postId);
//        if (mainImage != null) {
//            try {
//                return fireBaseService.getFileUrl(mainImage.getUrl());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//}


@Service
public class ImageService {

    private final ImageMapper imageMapper;
    private final FireBaseService fireBaseService;

    public ImageService(ImageMapper imageMapper, FireBaseService fireBaseService) {
        this.imageMapper = imageMapper;
        this.fireBaseService = fireBaseService;
    }

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
        if (CollectionUtils.isEmpty(imageUrls)) {
            return; // imageUrls가 null이거나 비어있으면 아무 작업도 하지 않음
        }

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

    @Transactional
    public void deleteImagesByPostId(Long postId) {
        imageMapper.deleteImagesByPostId(postId);
    }

    public List<ImageDTO> getImagesByPostId(Long postId) {
        return imageMapper.findImagesByPostId(postId);
    }

    public String getMainImageUrl(Long postId) {
        ImageDTO mainImage = imageMapper.findMainImageByPostId(postId);
        if (mainImage != null) {
            try {
                return fireBaseService.getFileUrl(mainImage.getUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
