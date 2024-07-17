package com.tbp.honeyjar.admin.service;

import com.tbp.honeyjar.admin.dto.NoticeDto;
import com.tbp.honeyjar.admin.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<NoticeDto> findAllNotices() {
        return noticeRepository.findAllNotices();
    }

    public int findById(Long noticeId) {
        return noticeRepository.findById(noticeId);
    }
}
