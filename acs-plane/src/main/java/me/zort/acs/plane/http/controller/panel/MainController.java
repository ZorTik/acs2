package me.zort.acs.plane.http.controller.panel;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.realm.RealmService;
import me.zort.acs.plane.api.domain.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/panel")
@RequiredArgsConstructor
public class MainController {
    private final RealmService realmService;
    private final UserService userService;

    @GetMapping
    public String index(Model model) {
        long realmCount = realmService.getAllRealms().size();
        long userCount = userService.getUserCount();

        model.addAttribute("realmCount", realmCount);
        model.addAttribute("userCount", userCount);

        return "panel/index";
    }
}
