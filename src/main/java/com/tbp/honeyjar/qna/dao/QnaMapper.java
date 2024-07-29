package com.tbp.honeyjar.qna.dao;

import com.tbp.honeyjar.qna.dto.QnaListResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaMapper {

    List<QnaListResponseDto> findAllQna();
}
