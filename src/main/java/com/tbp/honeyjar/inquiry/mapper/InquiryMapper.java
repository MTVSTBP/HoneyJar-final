package com.tbp.honeyjar.inquiry.mapper;

import com.tbp.honeyjar.inquiry.dto.InquiryDto;
import com.tbp.honeyjar.inquiry.dto.InquiryUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InquiryMapper {
    List<InquiryDto> getInquiryList();

    InquiryDto getInquiryById(Long id);

    int create(InquiryDto inquiryDto);

    int update(InquiryUpdateDto inquiryUpdateDto);

    int delete(Long id);
}
