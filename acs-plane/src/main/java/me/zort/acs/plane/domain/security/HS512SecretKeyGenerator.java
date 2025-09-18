package me.zort.acs.plane.domain.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.zort.acs.plane.api.domain.security.SecretKeyGenerator;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class HS512SecretKeyGenerator implements SecretKeyGenerator {

    @Override
    public SecretKey generateSecretKey() {
        return Jwts.SIG.HS512.key().build();
    }

    @Override
    public SignatureAlgorithm getSignatureAlgorithm() {
        return SignatureAlgorithm.HS512;
    }
}
