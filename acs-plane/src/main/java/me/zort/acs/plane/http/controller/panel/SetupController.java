package me.zort.acs.plane.http.controller.panel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/panel")
@Controller
public class SetupController {

    // TODO: Post pro registraci admin uživatele

    @GetMapping("/setup")
    public String setupGet() {
        return "panel/setup";
    }
}
