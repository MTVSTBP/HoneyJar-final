package com.tbp.honeyjar.comment.controller;

import com.tbp.honeyjar.comment.dto.CommentListDTO;
import com.tbp.honeyjar.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

//        List<CommentListDTO> comments = getAllComments();
//        model.addAttribute("comments", comments);
        List<CommentListDTO> commentList = commentService.findAllComment();
        model.addAttribute("commentList", commentList);

        return "pages/comment/comment";
    }

//    @GetMapping
    // 댓글 수정

    // 댓글 등록
//    @PostMapping
//    public String registComment(CommentListDTO commentDto, Model model) {
//
//        commentService.registComment(commentDto);
//
//
//    }
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
}
