package com.tbp.honeyjar.comment.dao;

import com.tbp.honeyjar.comment.dto.CommentListDTO;
import com.tbp.honeyjar.comment.dto.CommentModifyDTO;
import com.tbp.honeyjar.comment.dto.CommentRegistDTO;
import com.tbp.honeyjar.comment.dto.CommentRequestDTO;
import com.tbp.honeyjar.inquiry.dto.CreateInquiryCommentDTO;
import com.tbp.honeyjar.inquiry.dto.InquiryCommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<CommentListDTO> findAllComment();

    void registComment(CommentRegistDTO newComment);

    void modifyComment(CommentModifyDTO modifyComment);

//    CommentRequestDTO findById(Long commentId);

    void deleteCommentById(Long commentId);

    void softDeleteComment(Long postId);

    List<CommentListDTO> findAllCommentListById(Long postId);

    List<InquiryCommentDTO> getCommentListInquiryId(Long inquiryId);

    int insertInquiryComment(CreateInquiryCommentDTO createInquiryCommentDTO);
}
