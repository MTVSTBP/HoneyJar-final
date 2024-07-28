package com.tbp.honeyjar.follow.controller;

import com.tbp.honeyjar.follow.DTO.FollowerDTO;
import com.tbp.honeyjar.follow.DTO.FollowingDTO;
import com.tbp.honeyjar.follow.service.FollowService;
import com.tbp.honeyjar.login.DTO.UserDTO;
import com.tbp.honeyjar.login.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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

    // 추가
    @GetMapping("/following")
    public String following(Model model, Principal principal) {
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        FollowingDTO followingDTO = followService.getFollowing(userId);
        model.addAttribute("followings", followingDTO);
        return "pages/follow/following";
    }

    @PostMapping("/follow/{followUserId}")
    public String followUser(@PathVariable Long followUserId, Principal principal) {
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        followService.followUser(userId, followUserId);
        return "redirect:/follow/following";
    }

    @PostMapping("/unfollow/{followUserId}")
    public String unfollowUser(@PathVariable Long followUserId, Principal principal) {
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        followService.unfollowUser(userId, followUserId);
        return "redirect:/follow/following";
    }

    @GetMapping("/search")
    public String searchUsers(@RequestParam(name = "name", required = false, defaultValue = "") String name, Model model, Principal principal) {
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        List<UserDTO> users = userService.searchUsersByName(name);
        for (UserDTO user : users) {
            boolean isFollowed = followService.isFollowing(userId, user.getUserId());
            user.setFollowed(isFollowed); // 오류 해결: UserDTO에 setFollowed 메서드 추가
        }
        model.addAttribute("users", users);
        return "pages/follow/search";
    }
}
