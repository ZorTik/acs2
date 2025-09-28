package me.zort.acs.plane.data.security.model;

import java.util.List;

public interface ApiKeyModel {

    int getId();

    String getName();

    String getSecret();

    List<String> getClaims();
}
