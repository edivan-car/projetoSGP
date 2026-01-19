package br.com.sgp.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionTest {

	private static final String URL = "jdbc:seubanco://localhost:3306/sgp";
	private static final String USER = "usuario";
	private static final String PASS = "senha";

	public static boolean testConnection() {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
			return conn != null && !conn.isClosed();
		} catch (Exception e) {
			return false;
		}
	}
}
