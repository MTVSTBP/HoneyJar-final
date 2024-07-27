package com.tbp.honeyjar.admin.dao;

import com.tbp.honeyjar.inquiry.dto.InquiryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminInquiryMapper {



    /* get total-tount of Inquiries */
    int getAdminInquiryCount();

    /* find ALL Inquiry List  for admin */
    List<InquiryDto> getInquiryList(@Param("params") Map<String, Object> params);

    InquiryDto getInquiryById(Long inquiryId);

    // Called when admin deletes inquiry
    boolean delete(Long inquiryId);

}
