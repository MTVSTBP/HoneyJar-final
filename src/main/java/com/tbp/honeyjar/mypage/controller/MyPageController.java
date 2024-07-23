package com.tbp.honeyjar.mypage.controller;
import com.tbp.honeyjar.mypage.DTO.CategoryDTO;
import com.tbp.honeyjar.mypage.DTO.MyPageCorrectionDTO;
import com.tbp.honeyjar.mypage.DTO.MyPageDTO;
import com.tbp.honeyjar.mypage.service.MyPageService;
import com.tbp.honeyjar.post.dto.PostListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
public class MyPageController {
    private final MyPageService myPageService;
    @Autowired
    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }
    @GetMapping("/{userId}")
    public String getMyPage(@PathVariable Long userId, Model model) {
        MyPageDTO myPage = myPageService.getMyPage(userId);
        List<PostListDTO> postList =myPageService.getMyPost(userId);
        model.addAttribute("myPage", myPage);
        model.addAttribute("posts", postList);
        return "pages/mypage/myPage";
    }
    @PostMapping("/{userId}/correction")
    public void updateMyPageCorrection(@PathVariable Long userId, MyPageCorrectionDTO myPageCorrectionDTO) {
        myPageCorrectionDTO.setUserId(userId);
        myPageService.updateMyProfile(myPageCorrectionDTO);
    }
    @GetMapping("/{userId}/correction")
    public String getMyPageCorrection(@PathVariable Long userId, Model model) {
        MyPageCorrectionDTO myPage = myPageService.getMyProfile(userId);
        model.addAttribute("myPage", myPage);
        return "pages/mypage/myPageCorrection";
    }
    @GetMapping(value="category", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<CategoryDTO> getMyPageCategory() {return myPageService.getCategoryList();}
}