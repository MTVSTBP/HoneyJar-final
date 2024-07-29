package com.tbp.honeyjar.notice.service;

import com.tbp.honeyjar.notice.dao.NoticeMapper;
import com.tbp.honeyjar.notice.dto.NoticeListResponseDto;
import com.tbp.honeyjar.notice.dto.NoticeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class NoticeService {

    private final NoticeMapper noticeMapper;

    public List<NoticeListResponseDto> findAllNotices() {
        return noticeMapper.findAllNotices();
    }

    public NoticeResponseDto findById(Long noticeId) {
        return noticeMapper.findById(noticeId);
    }
}
