package me.zort.acs.plane.api.facade;

import me.zort.acs.plane.api.http.error.HttpError;

public interface FacadeExecuteOperation {

    void execute() throws HttpError;
}
