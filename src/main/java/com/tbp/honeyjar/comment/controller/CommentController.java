package com.tbp.honeyjar.comment.controller;

import com.tbp.honeyjar.comment.dto.CommentListDTO;
import com.tbp.honeyjar.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


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
    @PostMapping("/regist")
    public ResponseEntity<Map<String, String>> registerComment(@RequestBody CommentListDTO comment) {
        System.out.println(comment.toString()); // 전달된 데이터 확인을 위해 로그 출력

        Map<String, String> response = new HashMap<>();
        response.put("postid", String.valueOf(comment.getPostId())); // 실제 저장된 포스트 ID를 반환.
        return ResponseEntity.ok(response);
    }
}
//    private List<CommentListDTO> getAllComments() {
//        List<CommentListDTO> comments = new ArrayList<>();
//        comments.add(CommentListDTO.builder()
//                .commentId(1L)
//                .nickName("홍길동")
//                .profileImg("/assets/svg/base_profile.svg")
//                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry.\n" +
//                        "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,\n" +
//                        "when an unknown printer took a galley of type and scrambled it to make a type specimen book.\n" +
//                        "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.")
//                .date(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
//                .build());
//        comments.add(CommentListDTO.builder()
//                .commentId(2L)
//                .nickName("Seok")
//                .profileImg("/assets/svg/base_profile.svg")
//                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry.\n" +
//                        "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,\n" +
//                        "when an unknown printer took a galley of type and scrambled it to make a type specimen book.\n" +
//                        "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.")
//                .date(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
//                .build());
//
//        return comments;
//    }
//}
