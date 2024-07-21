package com.tbp.honeyjar.admin.service;

import com.tbp.honeyjar.admin.dao.QnaMapper;
import com.tbp.honeyjar.admin.dto.qna.QnaListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QnAService {

    private final QnaMapper qnaMapper;

    public List<QnaListResponseDto> findAllQna() {
        return qnaMapper.findAllQna();
    }
}
