package com.tbp.honeyjar.inquiry.mapper;

import com.tbp.honeyjar.inquiry.dto.InquiryDto;
import com.tbp.honeyjar.inquiry.dto.InquiryUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

@Mapper
public interface InquiryMapper {
    /* Find pagenaed Inquiry List by userid */
    List<InquiryDto> getInquiryListByUserId(@Param("params") Map<String, Object> params);

    /* for count total number of pages */
    int getInquiryCountByUserId(@Param("userId") Long userId);

    /* find ALL Inquiry List  for admin */
    List<InquiryDto> getInquiryList();

    /* Find ALL Inquiry List for a user */
    List<InquiryDto> getInquiryListByUserId(Long userId);

    InquiryDto getInquiryById(Long id);

    // Called when user write inquiry
    int create(InquiryDto inquiryDto);
    // Called when user modifies inquiry
    int update(InquiryUpdateDto inquiryUpdateDto);
    // Called when admin deletes inquiry
    int delete(Long id);
}
