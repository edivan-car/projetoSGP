package br.com.sgp.util;

import java.security.MessageDigest;

public final class PasswordUtil {

    private PasswordUtil() {
        // evita instância
    }
    
    public static final String SENHA_GENERICA = "abc@123";

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
    
    public static boolean isPrimeiroAcesso(String hashArmazenado) {
        return hash(SENHA_GENERICA).equals(hashArmazenado);
    }
}