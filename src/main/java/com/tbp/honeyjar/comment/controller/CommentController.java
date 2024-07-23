package com.tbp.honeyjar.comment.controller;

import com.tbp.honeyjar.comment.dto.*;
import com.tbp.honeyjar.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RequiredArgsConstructor
@Controller
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    // 댓글 조회
    @GetMapping
    public String commentList(Model model) {

        List<CommentListDTO> commentList = commentService.findAllComment();
        model.addAttribute("commentList", commentList);

        return "pages/comment/comment";
    }

    // 댓글 등록
    @PostMapping("regist") // long id?????
    public String registComment(@ModelAttribute CommentRegistDTO newComment) {
//        System.out.println(newComment);

        commentService.registComment(newComment);
//        commentService.findAllComment();

        return "redirect:/comment";
    }

    @PostMapping("modify/{comment_id}")
    public String modifyComment(@PathVariable Long comment_id, CommentModifyDTO modifyComment) {
//        System.out.println(modifyComment);

        CommentRequestDTO comment = commentService.findCommentById(comment_id);
        if (comment != null) {
            modifyComment.setCommentId(comment.getCommentId());
            commentService.modifyComment(modifyComment);
        }

        return "redirect:/comment";
    }

    @GetMapping("{comment_id}")
    public String deleteComment(@PathVariable Long comment_id) {

        System.out.println("comment_id = " + comment_id);
        commentService.deleteComment(comment_id);

        return "redirect:/comment";
    }
}
