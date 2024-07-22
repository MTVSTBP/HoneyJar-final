package com.tbp.honeyjar.mypage.controller;
import com.tbp.honeyjar.mypage.DTO.MyPageCorrectionDTO;
import com.tbp.honeyjar.mypage.DTO.MyPageDTO;
import com.tbp.honeyjar.mypage.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
        // MyPageDTO myPage = myPageService.getMyPage(userId);
        MyPageDTO myPage = new MyPageDTO();
        myPage.setNumberOfPosts(6);
        model.addAttribute("myPage", myPage);
        Map<String, Object> props = new HashMap<>();
        props.put("detail", "모달 내용 넣을 것");
        props.put("url", "확인버튼 눌렀을 때 이동할 페이지 주소 넣을 것 이동 없어도 입력해야 함");
        model.addAttribute("props", props);
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