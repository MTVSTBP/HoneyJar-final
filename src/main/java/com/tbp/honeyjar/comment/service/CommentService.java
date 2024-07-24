package com.tbp.honeyjar.comment.service;

import com.tbp.honeyjar.comment.dao.CommentMapper;
import com.tbp.honeyjar.comment.dto.CommentListDTO;
import com.tbp.honeyjar.comment.dto.CommentModifyDTO;
import com.tbp.honeyjar.comment.dto.CommentRegistDTO;
import com.tbp.honeyjar.comment.dto.CommentRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {

    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    public List<CommentListDTO> findAllComment() {
        return commentMapper.findAllComment();
    }

    public void registComment(CommentRegistDTO newComment) {
        commentMapper.registComment(newComment);
    }

    public void modifyComment(CommentModifyDTO modifyComment) {
        commentMapper.modifyComment(modifyComment);
    }

    public CommentRequestDTO findCommentById(Long commentId) {
        return commentMapper.findById(commentId);
    }

    public void deleteComment(Long commentId) {
         commentMapper.deleteCommentById(commentId);
    }

    public void softDeleteComment(Long postId) {
        commentMapper.softDeleteComment(postId);
    }
}
