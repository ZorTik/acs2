package me.zort.acs.plane.http.controller.panel;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/panel/users")
@Controller
public class UsersController {

    @GetMapping
    public String listUsersGet(Model model) {
        // TODO: Implement user listing logic

        return "panel/users/index";
    }
}
