package me.zort.acs.plane.http.error.exception;

import me.zort.acs.plane.api.http.error.HttpError;

public class PanelNoDefaultRealmException extends HttpError {

    public PanelNoDefaultRealmException() {
        super(500, "No default realm exists for requesting user.");
    }
}
