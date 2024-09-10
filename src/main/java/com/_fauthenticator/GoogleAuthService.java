package com._fauthenticator;
import com.google.zxing.WriterException;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GoogleAuthService {

    private final GoogleAuthenticator gAuth;
    @Autowired
    private QRCodeService qrCodeService;

    public GoogleAuthService() {
        this.gAuth = new GoogleAuthenticator();
    }

    // Genera las credenciales 2FA que incluyen la clave secreta (GoogleAuthenticatorKey)
    public GoogleAuthenticatorKey generateKey() {
        return gAuth.createCredentials();
    }

    // Genera la URL del código QR para que el usuario la escanee en Google Authenticator
    public String getQRBarcodeURL(GoogleAuthenticatorKey key, String username, String issuer) {
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL(issuer, username, key);
    }

    public String getQRBarcode(GoogleAuthenticatorKey key, String username, String issuer) throws IOException, WriterException {
        String data =  GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL(issuer, username, key);
        System.out.println(data);
        String base64QRCode = qrCodeService.generateQRCodeBase64(data, 300, 300);
        return base64QRCode;
    }

    // Valida el código TOTP generado por Google Authenticator
    public boolean validateCode(String secretKey, int code) {
        return gAuth.authorize(secretKey, code);
    }
}
