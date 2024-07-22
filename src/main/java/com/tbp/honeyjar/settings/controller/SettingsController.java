package com.tbp.honeyjar.settings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settings")
public class SettingsController {
    @GetMapping
    public String getSettings() {
        return "pages/settings/settings";
    }
}