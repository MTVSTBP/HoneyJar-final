package com.tbp.honeyjar.rating.controller;

import com.tbp.honeyjar.rating.DTO.RatingDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ratings")
public class RatingController {

    @GetMapping("/{postId}")
    public String getRating(@PathVariable Long postId, Model model) {
        // 임시 데이터로 평점 설정
        double averageRating = 4.5;  // 임시로 4.5로 설정

        RatingDTO ratingDTO = RatingDTO.builder()
                .postId(postId)
                .averageRating(averageRating)
                .build();

        model.addAttribute("ratingDetail", ratingDTO);
        return "pages/post/postDetail";
    }

}