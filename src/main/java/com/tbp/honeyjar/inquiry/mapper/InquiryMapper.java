package com.tbp.honeyjar.inquiry.mapper;

import com.tbp.honeyjar.inquiry.dto.InquiryDto;
import com.tbp.honeyjar.inquiry.dto.InquiryUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InquiryMapper {
    @Select("SELECT * FROM inquiry")
    List<InquiryDto> getInquiryList();

    @Select("SELECT * FROM inquiry WHERE inquiry_id = #{id}")
    InquiryDto getInquiryById(@Param("id") Long id);

    int create(InquiryDto inquiryDto);

    int update(InquiryUpdateDto inquiryUpdateDto);

    int delete(Long id);
}
