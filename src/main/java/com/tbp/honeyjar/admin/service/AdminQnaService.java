package com.tbp.honeyjar.admin.service;

import com.tbp.honeyjar.admin.dao.AdminQnaMapper;
import com.tbp.honeyjar.admin.dto.qna.QnaCorrectionRequestDto;
import com.tbp.honeyjar.admin.dto.qna.AdminQnaListResponseDto;
import com.tbp.honeyjar.admin.dto.qna.QnaResponseDto;
import com.tbp.honeyjar.admin.dto.qna.QnaSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class AdminQnaService {

    private final AdminQnaMapper adminQnaMapper;

    @Transactional(readOnly = true)
    public List<AdminQnaListResponseDto> findAllQna() {
        return adminQnaMapper.findAllQna();
    }

    @Transactional(readOnly = true)
    public QnaResponseDto findById(Long id) {
        return adminQnaMapper.findById(id);
    }

    public Long save(QnaSaveRequestDto requestDto) {
        return adminQnaMapper.save(requestDto);
    }

    public Long correction(QnaCorrectionRequestDto requestDto) {
        return adminQnaMapper.correction(requestDto);
    }

    public Long delete(Long qnaId) {
        return adminQnaMapper.delete(qnaId);
    }
}
