package com.app.config;

import com.app.service.id.IdGenerator;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationKeyGenerator implements AuthenticationKeyGenerator {
    private static final String CLIENT_ID = "client_id";
    private static final String SCOPE = "scope";
    private static final String USERNAME = "username";
    private static final String DEVICE_ID = "device_id";

    @Override
    public String extractKey(OAuth2Authentication authentication) {
        Map<String, String> values = extractValues(authentication);
        String valueString = values.entrySet().stream()
                .map(entry -> entry.getKey() + ":" + entry.getValue())
                .collect(Collectors.joining("|"));
        return generateMD5(valueString);
    }

    private Map<String, String> extractValues(OAuth2Authentication authentication) {
        return Map.of(
                CLIENT_ID, authentication.getOAuth2Request().getClientId(),
                SCOPE, OAuth2Utils.formatParameterList(authentication.getOAuth2Request().getScope()),
                USERNAME, authentication.isClientOnly() ? "" : authentication.getName(),
                DEVICE_ID, getOrGenerateDeviceId(authentication)
        );
    }

    private String getOrGenerateDeviceId(OAuth2Authentication authentication) {
        String deviceId = authentication.getOAuth2Request().getRequestParameters().get(DEVICE_ID);
        return deviceId != null && !deviceId.isEmpty() ? deviceId : String.valueOf(new IdGenerator().nextId());
    }

    private String generateMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
