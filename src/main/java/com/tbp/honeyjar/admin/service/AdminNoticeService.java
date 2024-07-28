package com.tbp.honeyjar.admin.service;

import com.tbp.honeyjar.admin.dao.AdminNoticeMapper;
import com.tbp.honeyjar.admin.dto.notice.NoticeCorrectionRequestDto;
import com.tbp.honeyjar.admin.dto.notice.AdminNoticeListResponseDto;
import com.tbp.honeyjar.admin.dto.notice.AdminNoticeResponseDto;
import com.tbp.honeyjar.admin.dto.notice.NoticeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class AdminNoticeService {

    private final AdminNoticeMapper adminNoticeMapper;

    @Transactional(readOnly = true)
    public List<AdminNoticeListResponseDto> findAllNotices() {
        return adminNoticeMapper.findAllNotices();
    }

    @Transactional(readOnly = true)
    public AdminNoticeResponseDto findById(Long id) {
        return adminNoticeMapper.findById(id);
    }

    public Long save(NoticeSaveRequestDto requestDto) {
        return adminNoticeMapper.save(requestDto);
    }

    public Long delete(Long postId) {
        return adminNoticeMapper.delete(postId);
    }

    public Long correction(NoticeCorrectionRequestDto requestDto) {
        return adminNoticeMapper.correction(requestDto);
    }
}
