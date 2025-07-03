package me.zort.acs.plane.http.controller.panel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RealmsController {

    @GetMapping("/realms/create")
    public String createRealmGet() {
        return "realms/create";
    }
}
