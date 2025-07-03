package me.zort.acs.plane.http.controller.panel;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.facade.RealmsFacade;
import me.zort.acs.plane.api.http.error.HttpErrorPropagator;
import me.zort.acs.plane.facade.util.Result;
import me.zort.acs.plane.http.dto.realms.RealmsCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Controller
public class RealmsController {
    private final RealmsFacade realmsFacade;
    private final HttpErrorPropagator errorPropagator;

    @PostMapping("/realms/create")
    public String createRealmPost(@ModelAttribute RealmsCreateForm form, Model model) {
        String name = form.getName();
        Result<Void> result = realmsFacade.createRealm(name);
        if (result.isError()) {
            errorPropagator.propagateErrorToModel(result.getError(), model);

            return createRealmGet();
        }

        return "redirect:/panel/definitions/raw?realm=" + name;
    }

    @GetMapping("/realms/create")
    public String createRealmGet() {
        return "realms/create";
    }
}
