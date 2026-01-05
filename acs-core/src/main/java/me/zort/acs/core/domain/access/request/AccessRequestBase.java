package me.zort.acs.core.domain.access.request;

import lombok.Getter;

@Getter
public class AccessRequestBase implements AccessRequest {
    private boolean granted;

    public AccessRequestBase() {
        this.granted = false;
    }

    public void grant() {
        this.granted = true;
    }
}
