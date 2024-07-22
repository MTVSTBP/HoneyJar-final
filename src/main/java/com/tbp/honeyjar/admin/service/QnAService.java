package com.tbp.honeyjar.admin.service;

import com.tbp.honeyjar.admin.dao.QnaMapper;
import com.tbp.honeyjar.admin.dto.qna.QnaCorrectionRequestDto;
import com.tbp.honeyjar.admin.dto.qna.QnaListResponseDto;
import com.tbp.honeyjar.admin.dto.qna.QnaResponseDto;
import com.tbp.honeyjar.admin.dto.qna.QnaSaveRequestDto;
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

    @Transactional(readOnly = true)
    public QnaResponseDto findById(Long id) {
        return qnaMapper.findById(id);
    }

    public Long save(QnaSaveRequestDto requestDto) {
        return qnaMapper.save(requestDto);
    }

    public Long correction(QnaCorrectionRequestDto requestDto) {
        return qnaMapper.correction(requestDto);
    }

    public Long delete(Long qnaId) {
        return qnaMapper.delete(qnaId);
    }
}
