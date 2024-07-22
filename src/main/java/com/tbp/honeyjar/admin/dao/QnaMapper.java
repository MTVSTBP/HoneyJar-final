package com.tbp.honeyjar.admin.dao;

import com.tbp.honeyjar.admin.dto.qna.QnaListResponseDto;
import com.tbp.honeyjar.admin.dto.qna.QnaResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaMapper {

    List<QnaListResponseDto> findAllQna();

    QnaResponseDto findById(Long id);
}
