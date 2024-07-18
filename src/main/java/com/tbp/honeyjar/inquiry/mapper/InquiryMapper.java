package com.tbp.honeyjar.inquiry.mapper;

import com.tbp.honeyjar.inquiry.dto.InquiryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InquiryMapper {
    List<InquiryDto> getInquiryList();
    int create(InquiryDto inquiryDto);
}

