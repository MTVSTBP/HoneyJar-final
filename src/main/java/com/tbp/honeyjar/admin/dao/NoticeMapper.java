package com.tbp.honeyjar.admin.dao;

import com.tbp.honeyjar.admin.dto.notice.NoticeListResponseDto;
import com.tbp.honeyjar.admin.dto.notice.NoticeResponseDto;
import com.tbp.honeyjar.admin.dto.notice.NoticeSaveRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {

    List<NoticeListResponseDto> findAllNotices();

    NoticeResponseDto findById(Long id);

    void save(NoticeSaveRequestDto dto);

    Long delete(int postId);
}
