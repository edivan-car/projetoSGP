package br.com.sgp.util;

import java.sql.Connection;

@FunctionalInterface
public interface ConnectionProvider {

    Connection getConnection() throws Exception;
}