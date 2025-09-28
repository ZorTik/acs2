package me.zort.acs.plane.domain.security;

import io.jsonwebtoken.security.Keys;
import me.zort.acs.plane.api.domain.security.SecretKeyEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;

@Service
public class Base64SecretKeyEncoder implements SecretKeyEncoder {

    @Override
    public String encode(SecretKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    @Override
    public SecretKey decode(String encodedKey) {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(encodedKey));
    }
}
