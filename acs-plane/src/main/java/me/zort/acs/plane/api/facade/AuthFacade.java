package me.zort.acs.plane.api.facade;

import jakarta.servlet.http.HttpServletRequest;
import me.zort.acs.plane.facade.util.Result;
import me.zort.acs.plane.http.dto.auth.RegisterForm;

public interface AuthFacade {

    Result<Void> register(RegisterForm form);

    Result<Void> forceLogin(String username, String password, HttpServletRequest request);
}
