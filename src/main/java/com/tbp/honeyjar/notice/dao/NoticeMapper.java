package com.tbp.honeyjar.notice.dao;

import com.tbp.honeyjar.notice.dto.NoticeListResponseDto;
import com.tbp.honeyjar.notice.dto.NoticeResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {

    List<NoticeListResponseDto> findAllNotices();

    NoticeResponseDto findById(Long id);
}
