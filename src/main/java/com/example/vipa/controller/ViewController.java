package com.example.vipa.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ViewController {

    @GetMapping("/homepage")
    public String getHomepage() {
        log.info("inside getHomepage()");
        return "/common/homepage-guest";
    }
}
