package com.tbp.honeyjar.inquiry.service;

import com.tbp.honeyjar.inquiry.dto.InquiryDto;
import com.tbp.honeyjar.inquiry.dto.InquiryWriteDto;
import com.tbp.honeyjar.inquiry.dto.InquiryUpdateDto;
import com.tbp.honeyjar.inquiry.mapper.InquiryMapper;
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

    public InquiryService(UserMapper userMapper, InquiryMapper inquiryMapper) {
        this.userMapper = userMapper;
        this.inquiryMapper = inquiryMapper;
    }

    @Transactional(readOnly = true)
    public Page<InquiryDto> getInquiryListByUserId(String kakaoId, Pageable pageable) {
        Long userId = userMapper.findByKakaoId(kakaoId).getUserId();
        int total = inquiryMapper.getInquiryCountByUserId(userId);

        Map<String, Object> params = new HashMap<>();

        params.put("userId", userId);
        params.put("pageSize", pageable.getPageSize());
        params.put("offset", pageable.getOffset());

        List<InquiryDto> inquiries = inquiryMapper.getInquiryListByUserId(params);
        return new PageImpl<>(inquiries, pageable, total);
    }

    @Transactional(readOnly = true)
    public List<InquiryDto> getInquiryList() {
        return inquiryMapper.getInquiryList();
    }

    @Transactional(readOnly = true)
    public List<InquiryDto> getInquiryListByUserId(String kakaoId) {
        Long userId = userMapper.findByKakaoId(kakaoId).getUserId();
        return inquiryMapper.getInquiryListByUserId(userId);
    }

    @Transactional(readOnly = true)
    public InquiryDto getInquiryById(Long id) {
        return inquiryMapper.getInquiryById(id);
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

    public void deleteInquiry(Long id) {
        inquiryMapper.delete(id);
    }
}
