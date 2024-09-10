package com._fauthenticator;

public class GoogleAuthResponse {
    private String message;
    private String qrCodeBase64;
    private String secretKey;

    // Constructor para la respuesta con clave secreta y URL del código QR
    public GoogleAuthResponse(String message, String qrCodeBase64, String secretKey) {
        this.message = message;
        this.qrCodeBase64 = qrCodeBase64;
        this.secretKey = secretKey;
    }

    // Constructor para la respuesta de validación
    public GoogleAuthResponse(String message) {
        this.message = message;
    }

    // Getters y setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getQrCodeUrl() {
        return qrCodeBase64;
    }

    public void setQrCodeUrl(String qrCodeBase64) {
        this.qrCodeBase64 = qrCodeBase64;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
