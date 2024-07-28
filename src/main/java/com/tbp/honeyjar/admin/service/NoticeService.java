package com.tbp.honeyjar.admin.service;

import com.tbp.honeyjar.admin.dao.NoticeMapper;
import com.tbp.honeyjar.admin.dto.notice.NoticeCorrectionRequestDto;
import com.tbp.honeyjar.admin.dto.notice.NoticeListResponseDto;
import com.tbp.honeyjar.admin.dto.notice.NoticeResponseDto;
import com.tbp.honeyjar.admin.dto.notice.NoticeSaveRequestDto;
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
public class NoticeService {

    private final NoticeMapper noticeMapper;

    //    @Transactional(readOnly = true)
//    public List<NoticeListResponseDto> findAllNotices(Pageable pageable) {
//
//        int totalPageCount = noticeMapper.getNoticeCount();
//        Map<String, Object> parmas = Map.of("start", 0, "end", 10);
//        return noticeMapper.findAllNotices();
//    }
    @Transactional(readOnly = true)
    public Page<NoticeListResponseDto> findAllNotices(Pageable pageable) {
        int totalPageCount = noticeMapper.getNoticeCount();

        Map<String, Object> params = new HashMap<>();

        params.put("offset", pageable.getOffset());
        params.put("pageSize", pageable.getPageSize());
        List<NoticeListResponseDto> notices = noticeMapper.findAllNotices(params);
        return new PageImpl<>(notices, pageable, totalPageCount);
    }

    @Transactional(readOnly = true)
    public NoticeResponseDto findById(Long id) {
        return noticeMapper.findById(id);
    }

    public Long save(NoticeSaveRequestDto requestDto) {
        return noticeMapper.save(requestDto);
    }

    public Long delete(Long postId) {
        return noticeMapper.delete(postId);
    }

    public Long correction(NoticeCorrectionRequestDto requestDto) {
        return noticeMapper.correction(requestDto);
    }
}
