package com.example.vipa.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ViewController {

    @GetMapping("/homepage-guest")
    public String getGuestHomepage() {
        log.info("inside getHomepage()");
        return "/common/homepage-guest";
    }

    @GetMapping("/homepage-client")
    public String getClientHomepage() {
        log.info("inside getHomepage()");
        return "/common/homepage-client";
    }
}
