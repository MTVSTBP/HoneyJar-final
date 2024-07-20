package com.tbp.honeyjar.comment.service;

import com.tbp.honeyjar.comment.dao.CommentMapper;
import com.tbp.honeyjar.comment.dto.CommentListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentMapper commentMapper;

    public List<CommentListDTO> findAllComment() {
        return commentMapper.findAllComment();
    }

    public int registComment(CommentListDTO commentDto) {
        return commentMapper.registComment();
    }
}
