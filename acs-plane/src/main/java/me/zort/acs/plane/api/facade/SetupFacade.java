package me.zort.acs.plane.api.facade;

import jakarta.servlet.http.HttpServletRequest;
import me.zort.acs.plane.http.facade.util.Result;

public interface SetupFacade {

    Result<String> executeSetupPage(HttpServletRequest request);

    Result<String> getSetupPage();
}
