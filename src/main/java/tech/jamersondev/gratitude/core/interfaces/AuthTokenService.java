package tech.jamersondev.gratitude.core.interfaces;

public interface AuthTokenService {
    String refreshAccessToken(String token);
}
