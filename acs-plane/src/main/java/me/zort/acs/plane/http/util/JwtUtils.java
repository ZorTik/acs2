package me.zort.acs.plane.http.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.util.Base64;
import java.util.Map;

@UtilityClass
public final class JwtUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Extracts subject from signed jwt without verifying
     *
     * @param jwt The jwt
     * @return The parsed subject
     * @throws IllegalArgumentException If the jwt is invalid
     */
    @SuppressWarnings("unchecked")
    public static String extractSubject(String jwt) throws IllegalArgumentException {
        try {
            String[] parts = jwt.split("\\.");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid JWT format");
            }

            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

            Map<String, Object> claims = objectMapper.readValue(payload, Map.class);

            Object sub = claims.get("sub");
            if (sub == null) {
                throw new IllegalArgumentException("Subject claim (sub) missing");
            }
            return sub.toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to extract subject", e);
        }
    }
}
