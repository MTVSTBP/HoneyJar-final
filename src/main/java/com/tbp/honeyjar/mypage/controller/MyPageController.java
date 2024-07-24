package com.tbp.honeyjar.mypage.controller;
import com.tbp.honeyjar.login.service.user.UserService;
import com.tbp.honeyjar.mypage.DTO.CategoryDTO;
import com.tbp.honeyjar.mypage.DTO.MyPageCorrectionDTO;
import com.tbp.honeyjar.mypage.DTO.MyPageDTO;
import com.tbp.honeyjar.mypage.service.MyPageService;
import com.tbp.honeyjar.post.dto.PostListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
public class MyPageController {
    private final MyPageService myPageService;
    private final UserService userService;

    @Autowired
    public MyPageController(MyPageService myPageService, UserService userService) {
        this.myPageService = myPageService;
        this.userService = userService;
    }
    @GetMapping
    public String getMyPage(Model model, Principal principal) {
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        System.out.println(userId);
        MyPageDTO myPage = myPageService.getMyPage(userId);
        List<PostListDTO> postList =myPageService.getMyPost(userId);
        model.addAttribute("myPage", myPage);
        model.addAttribute("posts", postList);
        return "pages/mypage/myPage";
    }
    @PostMapping("/correction")
    @ResponseBody
    public ResponseEntity<MyPageCorrectionDTO> updateMyPageCorrection(@RequestBody MyPageCorrectionDTO myPageCorrectionDTO, Principal principal) {
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        myPageCorrectionDTO.setUserId(userId);
        myPageService.updateMyProfile(myPageCorrectionDTO);

        return new ResponseEntity<>(myPageCorrectionDTO, HttpStatus.OK);
    }
    @GetMapping("/correction")
    public String getMyPageCorrection(Model model, Principal principal) {
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        MyPageCorrectionDTO myPage = myPageService.getMyProfile(userId);
        model.addAttribute("myPage", myPage);
        return "pages/mypage/myPageCorrection";
    }
    @GetMapping(value="category", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<CategoryDTO> getMyPageCategory() {return myPageService.getCategoryList();}
}