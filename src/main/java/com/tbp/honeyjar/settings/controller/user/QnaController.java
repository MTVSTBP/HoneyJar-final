//package com.tbp.honeyjar.settings.controller.user;
//
//import com.tbp.honeyjar.admin.dto.category.QnaCategoryListResponseDto;
//import com.tbp.honeyjar.admin.service.CategoryService;
//import com.tbp.honeyjar.login.entity.user.User;
//import com.tbp.honeyjar.login.mapper.user.UserMapper;
//import com.tbp.honeyjar.qna.dto.QnaListResponseDto;
//import com.tbp.honeyjar.qna.service.QnaService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Slf4j
//@Controller
//@RequestMapping(value = "/settings")
//public class QnaController {
//
//    private final UserMapper userMapper;
//    private final QnaService qnaService;
//    private final CategoryService categoryService;
//
//    public QnaController(UserMapper userMapper, QnaService qnaService, CategoryService categoryService) {
//        this.userMapper = userMapper;
//        this.qnaService = qnaService;
//        this.categoryService = categoryService;
//    }
//
//    @GetMapping(value = "/qna")
//    public String qnaView(Model model, Authentication authentication) {
//        log.debug("authentication = {}", authentication);
//
//        List<QnaListResponseDto> qnaList = qnaService.findAllQna();
//        List<QnaCategoryListResponseDto> qnaCategoryList = categoryService.findAllQnaCategory();
//
//        if (authentication != null && authentication.isAuthenticated()) {
//            String kakaoId = authentication.getName();
//
//            User user = userMapper.findByKakaoId(kakaoId);
//
//            if (user != null) {
//                if (!qnaList.isEmpty()) {
//                    model.addAttribute("qnaList", qnaList);
//                    model.addAttribute("qnaCategoryList", qnaCategoryList);
//                }
//                return "pages/settings/qna/qna";
//            } else {
//                log.warn("User not found for Kakao ID: {}", kakaoId);
//            }
//        }
//
//        log.warn("# No Authentication #");
//        return "redirect:/login";
//    }
//}
