package com.tbp.honeyjar.admin.dao;

import com.tbp.honeyjar.admin.dto.notice.NoticeCorrectionRequestDto;
import com.tbp.honeyjar.admin.dto.notice.NoticeListResponseDto;
import com.tbp.honeyjar.admin.dto.notice.NoticeResponseDto;
import com.tbp.honeyjar.admin.dto.notice.NoticeSaveRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface NoticeMapper {

//    List<NoticeListResponseDto> findAllNotices();

    List<NoticeListResponseDto> findAllNotices(@Param("params") Map<String, Object> params);

    NoticeResponseDto findById(Long id);

    Long save(NoticeSaveRequestDto dto);

    Long correction(NoticeCorrectionRequestDto dto);

    Long delete(Long postId);

    int getNoticeCount();
}
