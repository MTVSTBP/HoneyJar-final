package com.tbp.honeyjar.inquiry.service;

import com.tbp.honeyjar.inquiry.dto.InquiryDto;
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
        return getDummyInquiries().stream()
                .filter(inquiry -> inquiry.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /* inquiryWrite.html */
    public int createInquiry(InquiryDto inquiryDto) {
        int result;
        InquiryDto inquirydto;
        result = inquiryMapper.create(inquiryDto);

        System.out.println("Save inquiry called : " + inquiryDto);
        return result;
    }


    public void updateInquiry(Long id, String title, Long categoryId, String content) {
        // 실제 구현에서는 DB에서 데이터를 수정해야 함
        System.out.println("Updating inquiry id: " + id + ", title: " + title + ", categoryId: " + categoryId + ", content: " + content);
    }
}
