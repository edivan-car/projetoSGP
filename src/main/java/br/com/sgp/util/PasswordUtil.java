package br.com.sgp.util;

import java.security.MessageDigest;

public final class PasswordUtil {

    private PasswordUtil() {
        // evita instância
    }

    /**
     * Converte uma senha em hash SHA-256.
     * Exemplo: "admin123" -> "ecd71870d1963316a97e3ac3408c9835..."
     */
    public static String hash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(password.getBytes("UTF-8"));

            StringBuilder hex = new StringBuilder();
            for (byte b : bytes) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar hash da senha", e);
        }
    }
}