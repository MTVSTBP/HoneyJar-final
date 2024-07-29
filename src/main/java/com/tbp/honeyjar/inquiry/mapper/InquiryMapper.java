package com.tbp.honeyjar.inquiry.mapper;

import com.tbp.honeyjar.inquiry.dto.InquiryDto;
import com.tbp.honeyjar.inquiry.dto.InquiryUpdateDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InquiryMapper {
    List<InquiryDto> getInquiryList();
    List<InquiryDto> getInquiryListByUserId(Long userId);
    InquiryDto getInquiryById(Long id);
    int create(InquiryDto inquiryDto);
    int update(InquiryUpdateDto inquiryUpdateDto);
    int delete(Long id);
}
