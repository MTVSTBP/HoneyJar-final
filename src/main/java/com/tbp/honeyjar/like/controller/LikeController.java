package com.tbp.honeyjar.like.controller;

import com.tbp.honeyjar.like.DTO.LikeDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/likes")
public class LikeController {

    @GetMapping("/{postId}")
    public String getLikeCount(@PathVariable Long postId, Model model) {
        // 임시 데이터로 좋아요 수를 설정합니다.
        int likeCount = 10; // 임시로 10개로 설정

        LikeDTO likeDTO = LikeDTO.builder()
                .postId(postId)
                .likeCount(likeCount)
                .build();

        model.addAttribute("likeDetail", likeDTO);
        return "pages/post/postDetail";  // 실제 상세 페이지 경로로 변경해야 합니다.
    }
}