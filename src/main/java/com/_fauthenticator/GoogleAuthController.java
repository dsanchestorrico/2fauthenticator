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
    public ResponseEntity<GoogleAuthResponse> generateSecretKey(@RequestParam String username) throws IOException, WriterException {
        GoogleAuthenticatorKey key = googleAuthService.generateKey();
        String qrCodeBase64 = googleAuthService.getQRBarcode(key, username, "2fauthenticator");
        GoogleAuthResponse response = new GoogleAuthResponse(
                "Secret key generated successfully.",
                qrCodeBase64,
                key.getKey()
        );
        return ResponseEntity.ok(response);
    }

    // Endpoint para validar el código TOTP enviado por el usuario
    @PostMapping("/validate")
    public ResponseEntity<GoogleAuthResponse> validate2FACode(@RequestBody TwoFactorAuthRequest request) {
        String secretKey = request.getSecretKey();
        int code = request.getCode();
        boolean isValid = googleAuthService.validateCode(secretKey, code);

        GoogleAuthResponse response;
        if (isValid) {
            response = new GoogleAuthResponse("2FA code is valid.");
            return ResponseEntity.ok(response);
        } else {
            response = new GoogleAuthResponse("Invalid 2FA code.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
