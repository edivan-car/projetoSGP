package br.com.sgp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public final class ConnectionFactory {

	private static final String CONFIG_FILE = "config.properties";
	private static final String DB_PATH_PROPERTY = "db.path";
	private static String dbPath;
	private static String url;

	private static File getApplicationDirectory() throws Exception {
		File location = new File(ConnectionFactory.class.getProtectionDomain().getCodeSource().getLocation().toURI());

		if (location.isFile()) {
			// Execução pelo JAR: usa a pasta onde o JAR está.
			return location.getParentFile();
		}

		// Execução pela IDE: usa o diretório de trabalho do projeto.
		return new File(System.getProperty("user.dir"));
	}

	private static Properties loadConfiguration(File configFile) throws Exception {
		if (!configFile.isFile()) {
			throw new IllegalStateException("Arquivo de configuração não encontrado: " + configFile.getAbsolutePath());
		}

		Properties properties = new Properties();

		try (Reader reader = new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8)) {
			properties.load(reader);
		}

		return properties;
	}

	private static synchronized void initialize() throws Exception {
		if (url != null) {
			return;
		}

		File applicationDirectory = getApplicationDirectory();
		File configFile = new File(applicationDirectory, CONFIG_FILE);

		Properties properties = loadConfiguration(configFile);
		String configuredPath = properties.getProperty(DB_PATH_PROPERTY);

		if (configuredPath == null || configuredPath.trim().isEmpty()) {
			throw new IllegalStateException(
					"A propriedade " + DB_PATH_PROPERTY + " não foi informada em " + configFile.getAbsolutePath());
		}

		File databaseFile = new File(configuredPath.trim());

		if (!databaseFile.isAbsolute()) {
			databaseFile = new File(applicationDirectory, configuredPath.trim());
		}

		if (!databaseFile.isFile()) {
			throw new IllegalStateException("Banco de dados não encontrado: " + databaseFile.getAbsolutePath());
		}

		dbPath = databaseFile.getAbsolutePath();
		url = "jdbc:ucanaccess://" + dbPath;
	}

	private ConnectionFactory() {
		// evita instância
	}

	public static Connection getConnection() throws Exception {
		initialize();
		return DriverManager.getConnection(url);
	}

	public static boolean testConnection() {
		try (Connection conn = getConnection()) {
			return conn != null && !conn.isClosed();
		} catch (Exception e) {
			System.err.println("Erro conexão Access: " + e.getMessage());
			return false;
		}
	}

	public static String getDbPath() {
		try {
			initialize();
			return dbPath;
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
