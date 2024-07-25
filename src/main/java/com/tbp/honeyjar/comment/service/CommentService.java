package com.tbp.honeyjar.comment.service;

import com.tbp.honeyjar.comment.dao.CommentMapper;
import com.tbp.honeyjar.comment.dto.CommentListDTO;
import com.tbp.honeyjar.comment.dto.CommentModifyDTO;
import com.tbp.honeyjar.comment.dto.CommentRegistDTO;
import com.tbp.honeyjar.comment.dto.CommentRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {

    private final CommentMapper commentMapper;
    private final SecurityAutoConfiguration securityAutoConfiguration;

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
        System.out.println("코멘트 아이디: " + commentId);
         commentMapper.deleteCommentById(commentId);
    }

    public void softDeleteComment(Long postId) {
        commentMapper.softDeleteComment(postId);
    }

    public List<CommentListDTO> findAllCommentListById(Long postId) {
        return commentMapper.findAllCommentListById(postId);
    }
}
