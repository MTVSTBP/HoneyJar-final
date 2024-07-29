package com.tbp.honeyjar.admin.service;

import com.tbp.honeyjar.admin.dao.AdminNoticeMapper;
import com.tbp.honeyjar.admin.dto.notice.NoticeCorrectionRequestDto;
import com.tbp.honeyjar.admin.dto.notice.AdminNoticeListResponseDto;
import com.tbp.honeyjar.admin.dto.notice.AdminNoticeResponseDto;
import com.tbp.honeyjar.admin.dto.notice.NoticeSaveRequestDto;
import com.tbp.honeyjar.notice.dto.NoticeListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Transactional
public class AdminNoticeService {

    private final AdminNoticeMapper adminNoticeMapper;

    //    @Transactional(readOnly = true)
//    public List<NoticeListResponseDto> findAllNotices(Pageable pageable) {
//
//        int totalPageCount = noticeMapper.getNoticeCount();
//        Map<String, Object> parmas = Map.of("start", 0, "end", 10);
//        return noticeMapper.findAllNotices();
//    }
    @Transactional(readOnly = true)
    public Page<AdminNoticeListResponseDto> findAllNotices(Pageable pageable) {
        int totalPageCount = adminNoticeMapper.getNoticeCount();

        Map<String, Object> params = new HashMap<>();

        params.put("offset", pageable.getOffset());
        params.put("pageSize", pageable.getPageSize());
        List<AdminNoticeListResponseDto> notices = adminNoticeMapper.findAllNotices(params);
        return new PageImpl<>(notices, pageable, totalPageCount);
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
