package com.tbp.honeyjar.admin.service;

import com.tbp.honeyjar.admin.dao.NoticeMapper;
import com.tbp.honeyjar.admin.dto.notice.NoticeListResponseDto;
import com.tbp.honeyjar.admin.dto.notice.NoticeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class NoticeService {

    private final NoticeMapper noticeMapper;

    @Transactional(readOnly = true)
    public List<NoticeListResponseDto> findAllNotices() {
        return noticeMapper.findAllNotices();
    }

    @Transactional(readOnly = true)
    public NoticeResponseDto findById(Long id) {
        return noticeMapper.findById(id);
    }
}
