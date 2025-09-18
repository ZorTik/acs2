package me.zort.acs.plane.api.facade;

import jakarta.servlet.http.HttpServletRequest;
import me.zort.acs.plane.api.domain.security.ApiKey;
import me.zort.acs.plane.http.dto.auth.CreateApiKeyForm;
import me.zort.acs.plane.http.dto.model.ListedApiKey;
import me.zort.acs.plane.http.facade.util.Result;
import me.zort.acs.plane.http.dto.auth.RegisterForm;

import java.util.Set;

public interface AuthFacade {

    /**
     * Register user from register form.
     *
     * @param form The register form
     */
    Result<Void> register(RegisterForm form);

    /**
     * Forcibly login request.
     *
     * @param username The username of the user logging in
     * @param password The password of the user logging in
     * @param request The request
     */
    Result<Void> forceLogin(String username, String password, HttpServletRequest request);

    /**
     * Create API key.
     *
     * @param form The create form
     * @return The generated key
     */
    Result<String> createApiKey(CreateApiKeyForm form);

    /**
     * List API keys.
     *
     * @return Set of currently registered API keys
     */
    Result<Set<ListedApiKey>> listApiKeys();
}
