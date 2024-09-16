package com._fauthenticator.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecretKeyFilter implements Filter {

    private static final String SECRET_KEY = "123"; // Puede ser cargado desde application.properties

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización, si es necesario
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String secretKeyFromHeader = httpRequest.getHeader("X-Secret-Key");

        if (isValidSecretKey(secretKeyFromHeader)) {
            chain.doFilter(request, response); // Continúa con el siguiente filtro o el endpoint
        } else {
            httpResponse.setContentType("application/json");
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN); // Establece el código de estado HTTP
            httpResponse.getWriter().write("{\"error\":\"Invalid secret key\"}");
        }
    }

    @Override
    public void destroy() {
        // Limpieza, si es necesario
    }

    private boolean isValidSecretKey(String secretKeyFromHeader) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            String hashedSecretKey = hexString.toString();
            return hashedSecretKey.equals(secretKeyFromHeader);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}
