package me.zort.acs.plane.api.domain.security;

import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;

public interface SecretKeyGenerator {

    SecretKey generateSecretKey();

    SignatureAlgorithm getSignatureAlgorithm();
}
