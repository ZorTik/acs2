package me.zort.acs.plane.api.facade;

import me.zort.acs.plane.facade.util.Result;
import me.zort.acs.plane.http.dto.auth.RegisterForm;

public interface AuthFacade {

    Result<Void> register(RegisterForm form);
}
