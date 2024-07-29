package com.tbp.honeyjar.home.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tbp.honeyjar.login.oauth.entity.RoleType;
import com.tbp.honeyjar.place.dto.PlaceDTO;
import com.tbp.honeyjar.post.dto.PostClusterDTO;
import com.tbp.honeyjar.post.dto.PostListDTO;
import com.tbp.honeyjar.post.service.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final PostService postService;

    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping({"/home", "/admin"})
    public String home(Model model, Authentication authentication) {
        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(RoleType.ADMIN.getCode()));

        model.addAttribute("isAdmin", isAdmin);

//        List<PostClusterDTO> clusters = postService.findAllPostClusters();
//        model.addAttribute("clusters", new Gson().toJson(clusters));

        // 모든 포스트 좌표 데이터 가져오기
        List<Map<String, Double>> coordinates = postService.findAllPostCoordinates();

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        // 가져온 데이터를 JSON 형식으로 변환하여 모델에 추가
        model.addAttribute("coordinates", gson.toJson(coordinates));

        return "pages/home/home";
    }
}
