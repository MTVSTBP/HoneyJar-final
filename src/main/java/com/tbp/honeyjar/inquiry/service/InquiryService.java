package com.tbp.honeyjar.inquiry.service;

import com.tbp.honeyjar.inquiry.dto.InquiryDto;
import com.tbp.honeyjar.inquiry.dto.InquiryWriteDto;
import com.tbp.honeyjar.inquiry.dto.InquiryUpdateDto;
import com.tbp.honeyjar.inquiry.mapper.InquiryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class InquiryService {
    InquiryMapper inquiryMapper;

    @Autowired(required = true)
    public InquiryService(InquiryMapper inquiryMapper) {
        this.inquiryMapper = inquiryMapper;
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
