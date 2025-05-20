package me.zort.acs.client.http.model.check;

public interface CheckAccessResponse {

    boolean grants(String... nodes);
}
