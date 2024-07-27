package com.tbp.honeyjar.inquiry.service;

import com.tbp.honeyjar.admin.dao.AdminInquiryMapper;
import com.tbp.honeyjar.inquiry.dto.InquiryDto;
import com.tbp.honeyjar.inquiry.dto.InquiryWriteDto;
import com.tbp.honeyjar.inquiry.dto.InquiryUpdateDto;
import com.tbp.honeyjar.inquiry.mapper.InquiryMapper;
import com.tbp.honeyjar.login.entity.user.User;
import com.tbp.honeyjar.login.mapper.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class InquiryService {
    InquiryMapper inquiryMapper;
    UserMapper userMapper;

    public InquiryService(InquiryMapper inquiryMapper, UserMapper userMapper) {
        this.inquiryMapper = inquiryMapper;
        this.userMapper = userMapper;
    }

    /*  */

    @Transactional(readOnly = true)
    public User findByKakaoId(String kakaoId) {
        return userMapper.findByKakaoId(kakaoId);
    }

    /* User find inquiry in page-unit */
    @Transactional(readOnly = true)
    public Page<InquiryDto> getInquiryListByUserId(String kakaoId, Pageable pageable) {
        Long    userId = 0L;
        int     totalPageCount = 0;
        List<InquiryDto> inquiries = null;
        Map<String, Object> params = new HashMap<>();
        try {
            userId         = userMapper.findByKakaoId(kakaoId).getUserId();
            totalPageCount = inquiryMapper.getInquiryCountByUserId(userId);

            params.put("pageSize",  pageable.getPageSize());
            params.put("offset",    pageable.getOffset());
            params.put("userId",    userId);
            inquiries = inquiryMapper.getInquiryListByUserId(params);
        } catch (NullPointerException e) {
            System.out.println("User not found. You might be loggined in admin account!\n");
        }
        return new PageImpl<>(inquiries, pageable, totalPageCount);
    }

    @Transactional(readOnly = true)
    public List<InquiryDto> getInquiryListByUserId(String kakaoId) {
        Long userId = userMapper.findByKakaoId(kakaoId).getUserId();
        return inquiryMapper.getInquiryListByUserId(userId);
    }

    /* get inquiryDetail by inquiryId */
    @Transactional(readOnly = true)
    public InquiryDto getInquiryById(Long inquiryId) {
        return inquiryMapper.getInquiryById(inquiryId);
    }

    public int createInquiry(InquiryWriteDto inquiryWriteDto) {
        InquiryDto inquiryDto = InquiryDto.builder()
                .userId(inquiryWriteDto.getUserId())
                .title(inquiryWriteDto.getTitle())
                .categoryId(inquiryWriteDto.getCategoryId())
                .post(inquiryWriteDto.getPost())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return inquiryMapper.create(inquiryDto);
    }

    public void updateInquiry(InquiryUpdateDto inquiryUpdateDto) {
        inquiryMapper.update(inquiryUpdateDto);
    }
}
