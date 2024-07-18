package com.tbp.honeyjar.admin.dao;

import com.tbp.honeyjar.admin.dto.NoticeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {

    List<NoticeDto> findAllNotices();
}
