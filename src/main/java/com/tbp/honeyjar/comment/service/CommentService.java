package com.tbp.honeyjar.comment.service;

import com.tbp.honeyjar.comment.dao.CommentMapper;
import com.tbp.honeyjar.comment.dto.CommentListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentMapper commentMapper;


    public List<CommentListDTO> findAllComment() {
        return commentMapper.findAllComment();
    }

    @Transactional
    public void registComment(CommentListDTO newComment) {
        commentMapper.registComment(newComment);
    }

    public void modifyComment(CommentListDTO newComment) {
        commentMapper.modifyComment(newComment);
    }

    // js에서 내 userId 서로 일치하는지 확인
//    public int registComment(CommentListDTO newComment) {
//        return commentMapper.registComment(newComment);
//    }
}
