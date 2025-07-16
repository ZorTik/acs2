package me.zort.acs.plane.api.domain.credentials;

public interface Credentials {

    long getId();

    long getUserId();

    void setUsername(String username);

    String getUsername();

    void setHashedPassword(String hashedPassword);

    String getHashedPassword();
}
