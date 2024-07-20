package com.tbp.honeyjar.comment.dao;

import com.tbp.honeyjar.comment.dto.CommentListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<CommentListDTO> findAllComment();

    int registComment(CommentListDTO newComment);
}
