package com.tbp.honeyjar.inquiry.service;

import com.tbp.honeyjar.inquiry.dto.InquiryDto;
import com.tbp.honeyjar.inquiry.dto.InquiryWriteDto;
import com.tbp.honeyjar.inquiry.dto.InquiryUpdateDto;
import com.tbp.honeyjar.inquiry.mapper.InquiryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InquiryService {
    InquiryMapper inquiryMapper;

    @Autowired(required = true)
    public InquiryService(InquiryMapper inquiryMapper) {
        this.inquiryMapper = inquiryMapper;
    }

    public List<InquiryDto> getDummyInquiries() {
        List<InquiryDto> inquiries = new ArrayList<>();
        inquiries.add(InquiryDto.builder()
                .id(1L)
                .title("문의드립니다.")
                .post("팔로우 차단할 수 있는 기능은 없나요??")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .userId(1L)
                .categoryId(1L)
                .build());
        inquiries.add(InquiryDto.builder()
                .id(2L)
                .title("다른문의")
                .post("문의내용입니다.")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .userId(2L)
                .categoryId(2L)
                .build());
        return inquiries;
    }

    public List<InquiryDto> getInquiryList() {
        return inquiryMapper.getInquiryList();
    }

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
        int result = inquiryMapper.create(inquiryDto);

        return result;
    }

    public void updateInquiry(InquiryUpdateDto inquiryUpdateDto) {
        inquiryMapper.update(inquiryUpdateDto);
    }

    public void deleteInquiry(Long id) {
        inquiryMapper.delete(id);
    }
}
