package dev.prabhjotaulakh.fuel.api.data;

public class TokenResponse {
    private String bearerToken;

    public TokenResponse() {
    }

    public TokenResponse(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}
