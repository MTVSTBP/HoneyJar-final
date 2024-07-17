package com.tbp.honeyjar.admin.controller;

import com.tbp.honeyjar.admin.dto.CategoryDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {

    @GetMapping
    public String category(Model model) {

        CategoryDto dto = new CategoryDto(1L, "text test");
        model.addAttribute("dto", dto);

        return "pages/admin/category/adminCategory";
    }

    @GetMapping("/write")
    public String write() {
        return "pages/admin/category/adminCategoryWrite";
    }
}
