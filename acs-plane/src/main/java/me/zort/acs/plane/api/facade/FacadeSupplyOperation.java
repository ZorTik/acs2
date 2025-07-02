package me.zort.acs.plane.api.facade;

import me.zort.acs.plane.api.http.error.HttpError;

@FunctionalInterface
public interface FacadeSupplyOperation<T> {

    T get() throws HttpError;
}
