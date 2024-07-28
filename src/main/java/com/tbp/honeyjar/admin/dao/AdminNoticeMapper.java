package com.tbp.honeyjar.admin.dao;

import com.tbp.honeyjar.admin.dto.notice.NoticeCorrectionRequestDto;
import com.tbp.honeyjar.admin.dto.notice.AdminNoticeListResponseDto;
import com.tbp.honeyjar.admin.dto.notice.AdminNoticeResponseDto;
import com.tbp.honeyjar.admin.dto.notice.NoticeSaveRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminNoticeMapper {

    List<AdminNoticeListResponseDto> findAllNotices();

    AdminNoticeResponseDto findById(Long id);

    Long save(NoticeSaveRequestDto dto);

    Long correction(NoticeCorrectionRequestDto dto);

    Long delete(Long postId);
}
