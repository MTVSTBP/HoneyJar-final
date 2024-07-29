package com.tbp.honeyjar.admin.dao;

import com.tbp.honeyjar.admin.dto.qna.QnaCorrectionRequestDto;
import com.tbp.honeyjar.admin.dto.qna.AdminQnaListResponseDto;
import com.tbp.honeyjar.admin.dto.qna.QnaResponseDto;
import com.tbp.honeyjar.admin.dto.qna.QnaSaveRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminQnaMapper {

    List<AdminQnaListResponseDto> findAllQna();

    QnaResponseDto findById(Long id);

    Long save(QnaSaveRequestDto requestDto);

    Long correction(QnaCorrectionRequestDto requestDto);

    Long delete(Long qnaId);
}
