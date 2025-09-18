package me.zort.acs.plane.http.controller.panel;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.facade.AuthFacade;
import me.zort.acs.plane.api.http.error.HttpAlertPropagator;
import me.zort.acs.plane.http.dto.auth.CreateApiKeyForm;
import me.zort.acs.plane.http.dto.model.ListedApiKey;
import me.zort.acs.plane.http.facade.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RequestMapping("/panel/keys")
@Controller
public class ApiKeysController {
    private final AuthFacade authFacade;
    private final HttpAlertPropagator alertPropagator;

    @GetMapping
    public String listApiKeysGet(Model model) {
        Set<ListedApiKey> keys = authFacade.listApiKeys().orError();

        model.addAttribute("keys", keys);

        return "panel/keys/index";
    }

    @GetMapping("/create")
    public String createApiKeyGet() {
        return "panel/keys/create";
    }

    @PostMapping("/create")
    public String createApiKeyPost(@ModelAttribute @Valid CreateApiKeyForm form, Model model) {
        String key = authFacade.createApiKey(form).orError();

        alertPropagator.propagateAlertToModel("Key generated: " + key, model);

        return listApiKeysGet(model);
    }

    @GetMapping("/delete")
    public String deleteApiKeyGet(@RequestParam int id, Model model) {
        Result<Void> result = authFacade.deleteApiKey(id);

        if (result.isError()) {
            alertPropagator.propagateErrorToModel(result.getError(), model);
        } else {
            alertPropagator.propagateAlertToModel("Api key deleted.", model);
        }
        return listApiKeysGet(model);
    }
}
