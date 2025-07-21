package me.zort.acs.plane.http.controller.panel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/panel")
public class MainController {

    @GetMapping
    public String index() {
        return "panel/index";
    }
}
