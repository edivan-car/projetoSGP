package br.com.sgp.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import br.com.sgp.Main;

public final class ConnectionFactory {

    private static final String DB_FILE = "db_production.accdb";
    private static final String DB_PATH = "F:/trabalho/SGP-SistemaGestãoProdução/DB" + "/" + DB_FILE;
    private static final String URL = "jdbc:ucanaccess://" + DB_PATH;
    
    //private static final String DB_PATH;
    //private static final String URL;
        
//    static {
//        try {
//            String jarDir = new File(
//                    Main.class.getProtectionDomain()
//                            .getCodeSource()
//                            .getLocation()
//                            .toURI()
//            ).getParent();
//
//            DB_PATH = jarDir + File.separator + DB_FILE;
//            URL = "jdbc:ucanaccess://" + DB_PATH;
//
//            System.out.println("Banco localizado em: " + DB_PATH);
//
//        } catch (Exception e) {
//            throw new RuntimeException("Erro ao localizar banco de dados", e);
//        }
//    }

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
