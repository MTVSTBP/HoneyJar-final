package com.tbp.honeyjar.comment.controller;

import com.tbp.honeyjar.comment.dto.*;
import com.tbp.honeyjar.comment.service.CommentService;
import com.tbp.honeyjar.login.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@RequiredArgsConstructor
@Controller
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    // 댓글 조회
    @GetMapping("{postId}")
    public String commentList(@PathVariable Long postId, Model model, Principal principal) {

        List<CommentListDTO> commentList = commentService.findAllCommentListById(postId);
        model.addAttribute("commentList", commentList);
        model.addAttribute("userId", userService.findUserIdByKakaoId(principal.getName()));
        return "pages/comment/comment";
    }

    // 댓글 등록
    @PostMapping("{postId}/regist") ///
    public String registComment(@ModelAttribute CommentRegistDTO newComment, Principal principal, @PathVariable Long postId) {
        // userId 기반으로 등록을 하기때문에 코드 추가 해야함
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        newComment.setUserId(userId);
        newComment.setPostId(postId);
        commentService.registComment(newComment);

        return "redirect:/comment/{postId}";
    }

    // 댓글 수정
    @PostMapping("modify/{post_id}/{comment_id}")
    public String modifyComment(@PathVariable Long post_id, @PathVariable Long comment_id,  @ModelAttribute CommentModifyDTO modifyComment, @RequestParam String comment) {
        System.out.println("comment controller calll!!!!! ");
        modifyComment.setPostId(post_id);
        modifyComment.setCommentId(comment_id);
        modifyComment.setComment(comment);

        commentService.modifyComment(modifyComment);

        logger.info("Modifying comment: " + modifyComment);
        commentService.modifyComment(modifyComment);

        return "redirect:/comment/" + post_id;
    }

    // 댓글 삭제
    @GetMapping("delete/{post_id}/{comment_id}")
    public String deleteComment(@PathVariable Long post_id, @PathVariable Long comment_id, @ModelAttribute CommentDeleteDTO deleteComment) {

        deleteComment.setCommentId(comment_id);
        deleteComment.setPostId(post_id); // postId를 설정하는 부분 추가

        commentService.deleteComment(comment_id);

        return "redirect:/comment/" + post_id;
    }

    // comment soft Delete
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> softDeletePost(@PathVariable Long postId) {
        commentService.softDeleteComment(postId);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
