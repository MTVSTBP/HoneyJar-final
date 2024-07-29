package com.tbp.honeyjar.comment.controller;

import com.tbp.honeyjar.comment.dto.*;
import com.tbp.honeyjar.comment.service.CommentService;
import com.tbp.honeyjar.login.service.user.UserService;
import com.tbp.honeyjar.mypage.DTO.MyPageDTO;
import com.tbp.honeyjar.mypage.service.MyPageService;
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
    private final MyPageService myPageService;

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    public CommentController(CommentService commentService, UserService userService, MyPageService myPageService) {
        this.commentService = commentService;
        this.userService = userService;
        this.myPageService = myPageService;
    }

    // 댓글 조회
    @GetMapping("{postId}")
    public String commentList(@PathVariable Long postId, Model model, Principal principal) {

        Long userId = null;
        if (principal != null) {
            userId = userService.findUserIdByKakaoId(principal.getName());
        }

        List<CommentListDTO> commentList = commentService.findAllCommentListById(postId);
        // MyPageDTO 객체 생성 (또는 서비스에서 가져오기)
        MyPageDTO myPage = myPageService.getMyPage(userId);

        model.addAttribute("myPage", myPage);
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
//        System.out.println("comment controller calll!!!!! ");
        modifyComment.setPostId(post_id);
        modifyComment.setCommentId(comment_id);
        modifyComment.setComment(comment);

        commentService.modifyComment(modifyComment);

//        logger.info("Modifying comment: " + modifyComment);
        commentService.modifyComment(modifyComment);

        return "redirect:/comment/" + post_id;
    }

    // 댓글 삭제
    @GetMapping("delete/{post_id}/{comment_id}")
    public String deleteComment(@PathVariable Long post_id, @PathVariable Long comment_id, @ModelAttribute CommentDeleteDTO deleteComment) {
        logger.info("테스트중 ------ -=== === Deleting comment: postId={}, commentId={}", post_id, comment_id);
        deleteComment.setPostId(post_id); // postId를 설정하는 부분 추가
        deleteComment.setCommentId(comment_id);

//        commentService.deleteComment(post_id);
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
