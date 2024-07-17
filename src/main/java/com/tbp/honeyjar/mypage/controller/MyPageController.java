package com.tbp.honeyjar.mypage.controller;
import com.tbp.honeyjar.mypage.DTO.MyPageCorrectionDTO;
import com.tbp.honeyjar.mypage.DTO.MyPageDTO;
import com.tbp.honeyjar.mypage.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
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
        // MyPageDTO myPage = myPageService.getMyPage(userId);
        MyPageDTO myPage = new MyPageDTO();
        myPage.setNumberOfPosts(6);
        model.addAttribute("myPage", myPage);
        return "pages/mypage/myPage";
    }
    @PostMapping("/{userId}/correction")
    public String updateMyPageCorrection(@PathVariable Long userId, Model model) {
        MyPageCorrectionDTO myPage = new MyPageCorrectionDTO();
        model.addAttribute("myPage", myPage);
        return "pages/mypage/myPageCorrection";
    }
    @GetMapping("/{userId}/correction")
    public String getMyPageCorrection(@PathVariable Long userId, Model model) {
        MyPageCorrectionDTO myPage = new MyPageCorrectionDTO();
        model.addAttribute("myPage", myPage);
        return "pages/mypage/myPageCorrection";
    }
}