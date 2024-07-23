package com.tbp.honeyjar.comment.dao;

import com.tbp.honeyjar.comment.dto.CommentListDTO;
import com.tbp.honeyjar.comment.dto.CommentModifyDTO;
import com.tbp.honeyjar.comment.dto.CommentRegistDTO;
import com.tbp.honeyjar.comment.dto.CommentRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<CommentListDTO> findAllComment();

    void registComment(CommentRegistDTO newComment);

    void modifyComment(CommentModifyDTO modifyComment);

    CommentRequestDTO findById(Long commentId);

    void deleteCommentById(Long commentId);
}
