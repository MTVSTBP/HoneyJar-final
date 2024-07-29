package com.tbp.honeyjar.admin.dao;

import com.tbp.honeyjar.admin.dto.notice.NoticeCorrectionRequestDto;
import com.tbp.honeyjar.admin.dto.notice.AdminNoticeListResponseDto;
import com.tbp.honeyjar.admin.dto.notice.AdminNoticeResponseDto;
import com.tbp.honeyjar.admin.dto.notice.NoticeSaveRequestDto;
import com.tbp.honeyjar.notice.dto.NoticeListResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminNoticeMapper {

//    List<NoticeListResponseDto> findAllNotices();

    List<AdminNoticeListResponseDto> findAllNotices(@Param("params") Map<String, Object> params);

    AdminNoticeResponseDto findById(Long id);

    Long save(NoticeSaveRequestDto dto);

    Long correction(NoticeCorrectionRequestDto dto);

    Long delete(Long postId);

    int getNoticeCount();
}
