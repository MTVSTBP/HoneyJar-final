package com.tbp.honeyjar.qna.service;

import com.tbp.honeyjar.qna.dao.QnaMapper;
import com.tbp.honeyjar.qna.dto.QnaListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class QnaService {

    private final QnaMapper qnaMapper;

    @Transactional(readOnly = true)
    public List<QnaListResponseDto> findAllQna() {
        return qnaMapper.findAllQna();
    }

}
