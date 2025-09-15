package me.zort.acs.plane.http.controller.panel;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.facade.UsersFacade;
import me.zort.acs.plane.http.dto.model.ListedUser;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/panel/users")
@Controller
public class UsersController {
    private final UsersFacade usersFacade;

    @GetMapping
    public String listUsersGet(Model model) {
        List<ListedUser> users = usersFacade.listUsers(PageRequest.of(0, 10)).orError();

        model.addAttribute("users", users);

        return "panel/users/index";
    }
}
