package br.com.sgp.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class AccessConnection {

	//private static final String DB_PATH = "F:/trabalho/SG_Producao/back_up/12122025/prg_producao.accdb";
	private static final String DB_PATH =
		    System.getProperty("user.dir") + "/prg_producao.accdb";


	private static final String URL = "jdbc:ucanaccess://" + DB_PATH;

	private AccessConnection() {
		// evita instância
	}

	public static Connection getConnection() throws Exception {
		return DriverManager.getConnection(URL);
	}

	public static boolean testConnection() {
		try (Connection conn = getConnection()) {
			return conn != null && !conn.isClosed();
		} catch (Exception e) {
			return false;
		}
	}
}