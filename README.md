# Aplicacion para doble factor de authenticacion

Servicio externo encargado de validar el segundo factor de autenticación (2FA) utiliza Google Authenticator.

## Generación de Códigos 2FA

Se utiliza TOTP (Time-Based One-Time Password) para generar códigos 2FA. La librería `otp-java` permite generar códigos que expiran tras un breve periodo de tiempo.

## Flujo Completo de la API

1. **Generación de código 2FA:** El cliente o usuario puede generar un código 2FA con el endpoint `/api/2fa/generate` (si lo permites). Alternativamente, el sistema podría utilizar una aplicación de autenticación para este propósito.

2. **Validación de código 2FA:** El sistema principal envía una solicitud POST a `/api/2fa/validate` con el código OTP, y tu servicio Spring Boot verifica su validez.
   ```{
    "secretKey": "2JOPHB7ZFT4PIJ4A",
    "code": 503468
}```
