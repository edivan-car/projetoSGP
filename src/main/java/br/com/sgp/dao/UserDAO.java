package br.com.sgp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.sgp.model.User;
import br.com.sgp.util.AccessConnection;

public class UserDAO {

	private static final String SQL_LOGIN = 
			"SELECT id, username, nome, perfil " +
			"FROM tb_usuarios " + 
			"WHERE username = ? AND senha = ? AND ativo = TRUE";

	public User login(String username, String senha) {

		try (Connection conn = AccessConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(SQL_LOGIN)) {
			ps.setString(1, username);
			ps.setString(2, senha);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return new User(rs.getInt("id"), rs.getString("username"), rs.getString("nome"),
						rs.getString("perfil"));
			}

		} catch (Exception e) {
			System.err.println("Erro login: " + e.getMessage());
		}

		return null;
	}
}
