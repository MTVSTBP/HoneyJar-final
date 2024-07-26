package com.tbp.honeyjar.follow.controller;

import com.tbp.honeyjar.follow.DTO.FollowerDTO;
import com.tbp.honeyjar.follow.service.FollowService;
import com.tbp.honeyjar.login.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/follow")
public class FollowController {
    FollowService followService;
    UserService userService;
    @Autowired
    public FollowController(FollowService followService, UserService userService) {
        this.followService = followService;
        this.userService = userService;
    }
    @GetMapping("/follower")
    public String follower(Model model, Principal principal) {
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        FollowerDTO followerDTO = followService.getFollower(userId);
        model.addAttribute("follows", followerDTO);
        return "pages/follow/follower";
    }
}
