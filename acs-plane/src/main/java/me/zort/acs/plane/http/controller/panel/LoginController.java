package me.zort.acs.plane.http.controller.panel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/panel")
@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginGet() {
        return "panel/login";
    }
}
