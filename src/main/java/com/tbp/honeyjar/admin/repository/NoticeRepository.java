package com.tbp.honeyjar.admin.repository;

import com.tbp.honeyjar.admin.dto.NoticeDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NoticeRepository {

    public List<NoticeDto> findAllNotices() {
        return new ArrayList<NoticeDto>();
    }

    public int findById(Long noticeId) {
        return 0;
    }
}
