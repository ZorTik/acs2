package me.zort.acs.plane.http.controller.panel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping
    public String index() {
        return "redirect:/panel/realms";
    }
}
