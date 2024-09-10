package com._fauthenticator;

import com.google.zxing.WriterException;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/2fa")
public class GoogleAuthController {

    private final GoogleAuthService googleAuthService;
    private String appName;

    public GoogleAuthController(GoogleAuthService googleAuthService) {
        this.googleAuthService = googleAuthService;
    }

    // Endpoint para generar la clave secreta y el código QR para el usuario
    @GetMapping("/generate-secret")
    public ResponseEntity<String> generateSecretKey(@RequestParam String username) throws IOException, WriterException {
        GoogleAuthenticatorKey key = googleAuthService.generateKey();
        String qrCodeUrl = googleAuthService.getQRBarcode(key, username, "2fauthenticator");
        return ResponseEntity.ok("Secret key: " + key.getKey() + "\nQR Code URL: " + qrCodeUrl);
    }

    // Endpoint para validar el código TOTP enviado por el usuario
    @PostMapping("/validate")
    public ResponseEntity<String> validate2FACode(@RequestBody TwoFactorAuthRequest request) {
        String secretKey = request.getSecretKey();
        int code = request.getCode();
        boolean isValid = googleAuthService.validateCode(secretKey, code);

        if (isValid) {
            return ResponseEntity.ok("2FA code is valid.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid 2FA code.");
        }
    }
}
