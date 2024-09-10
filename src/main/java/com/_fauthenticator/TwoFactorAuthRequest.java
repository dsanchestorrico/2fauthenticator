package com._fauthenticator;

public class TwoFactorAuthRequest {
    private String secretKey;
    private int code;

    // Getters y Setters
    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
