package com.tbp.honeyjar.admin.service;

import com.tbp.honeyjar.admin.dao.QnaMapper;
import com.tbp.honeyjar.admin.dto.qna.QnaCorrectionRequestDto;
import com.tbp.honeyjar.admin.dto.qna.QnaListResponseDto;
import com.tbp.honeyjar.admin.dto.qna.QnaResponseDto;
import com.tbp.honeyjar.admin.dto.qna.QnaSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QnaService {

    private final QnaMapper qnaMapper;

    public List<QnaListResponseDto> findAllQna() {
        return qnaMapper.findAllQna();
    }

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
