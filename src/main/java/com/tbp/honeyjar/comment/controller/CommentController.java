package com.tbp.honeyjar.comment.controller;

import com.tbp.honeyjar.comment.dto.*;
import com.tbp.honeyjar.comment.service.CommentService;
import com.tbp.honeyjar.login.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RequiredArgsConstructor
@Controller
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }
    // 컨트롤러 손보고 그거에 맞게 쿼리도 다시 손보고 그다음에 예준님 소환 => 해당 포스트 id에 맞는 comment로 이동
    //                  {postId}/comment

    // 댓글 조회
    @GetMapping("/{postId}")
    public String commentList(@PathVariable Long postId, Model model) {
        // postId를 받아주고 에러처리
        List<CommentListDTO> commentList = commentService.findAllCommentListById(postId);
//        List<CommentListDTO> commentList = commentService.findAllComment();
        model.addAttribute("commentList", commentList);

        return "pages/comment/comment";
    }

    // 댓글 등록
    @PostMapping("regist") //
    public String registComment(@ModelAttribute CommentRegistDTO newComment, Principal principal) {
        // userId 기반으로 등록을 하기때문에 코드 추가 해야함
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        newComment.setUserId(userId);

        commentService.registComment(newComment);

        return "redirect:/comment/{newComment.getPostId()}";
    }

    // 댓글 수정
    @PostMapping("modify/{comment_id}")
    public String modifyComment(@PathVariable Long comment_id, CommentModifyDTO modifyComment) {

        modifyComment.setCommentId(comment_id);
        commentService.modifyComment(modifyComment);

        return "redirect:/comment/{modifyComment.getPostId()}";
    }

    // 댓글 삭제
    @GetMapping("/delete/{comment_id}")
    public String deleteComment(@PathVariable Long comment_id) {

//        System.out.println("comment_id = " + comment_id);
        commentService.deleteComment(comment_id);

        return "redirect:/comment/{postId}";
    }

    // comment softDelete
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> softDeletePost(@PathVariable Long postId) {
        commentService.softDeleteComment(postId);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
