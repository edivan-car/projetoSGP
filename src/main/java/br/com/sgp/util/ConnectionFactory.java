package br.com.sgp.util;

import java.sql.Connection;
import java.sql.DriverManager;

public final class ConnectionFactory {

    private static final String DB_FILE = "db_production.accdb";
    private static final String DB_PATH = "F:/trabalho/SGP-SistemaGestãoProdução/DB" + "/" + DB_FILE;
    //private static final String DB_PATH = System.getProperty("user.dir") + "/" + DB_FILE;

    private static final String URL =
            "jdbc:ucanaccess://" + DB_PATH;

    private ConnectionFactory() {
        // evita instância
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL);
    }

    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (Exception e) {
            System.err.println("Erro conexão Access: " + e.getMessage());
            return false;
        }
    }
}
