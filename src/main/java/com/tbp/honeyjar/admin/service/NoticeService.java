package com.tbp.honeyjar.admin.service;

import com.tbp.honeyjar.admin.dao.NoticeMapper;
import com.tbp.honeyjar.admin.dto.NoticeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeMapper noticeMapper;

    public List<NoticeDto> findAllNotices() {
        return noticeMapper.findAllNotices();
    }

    public NoticeDto findById(Long id) {
        return noticeMapper.findById(id);
    }
}
