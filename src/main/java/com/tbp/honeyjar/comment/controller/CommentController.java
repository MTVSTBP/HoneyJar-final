package com.tbp.honeyjar.comment.controller;

import com.tbp.honeyjar.comment.DTO.CommentListDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("comments")
public class CommentController {

    // 댓글 조회
    @GetMapping("comment")
    public String commentList(Model model) {
        CommentListDTO comDTO = new CommentListDTO();
        model.addAttribute("comDTO", comDTO);

        List<CommentListDTO> comments = getAllComments();
        model.addAttribute("comments", comments);

        return "pages/comment/comment";
    }


    private List<CommentListDTO> getAllComments() {
        List<CommentListDTO> comments = new ArrayList<>();
        comments.add(CommentListDTO.builder()
                .commentId(1L)
                .nickName("홍길동")
                .profileImg("/assets/svg/base_profile.svg")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry.\n" +
                        "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,\n" +
                        "when an unknown printer took a galley of type and scrambled it to make a type specimen book.\n" +
                        "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.")
                .date(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .build());
        comments.add(CommentListDTO.builder()
                .commentId(2L)
                .nickName("Seok")
                .profileImg("/assets/svg/base_profile.svg")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry.\n" +
                        "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,\n" +
                        "when an unknown printer took a galley of type and scrambled it to make a type specimen book.\n" +
                        "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.")
                .date(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .build());

        return comments;
    }

    // 수정

    // 등록


}
