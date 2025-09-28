package me.zort.acs.plane.api.domain.security;

import javax.crypto.SecretKey;

public interface SecretKeyEncoder {

    String encode(SecretKey key);

    SecretKey decode(String encodedKey);
}
