package me.zort.acs.plane.http.controller.panel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/panel/settings")
public class SettingsController {

    @GetMapping
    public String settingsGet() {
        return "panel/settings/index";
    }
}
