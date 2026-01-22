package br.com.sgp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.sgp.model.User;
import br.com.sgp.util.AccessConnection;

public class UserDAO {

    private static final String SQL_LOGIN =
            "SELECT id, username, name, profile, sector " +
            "FROM tb_users " +
            "WHERE username = ? AND password = ? AND active = TRUE";

    // ======================
    // LOGIN
    // ======================
    public User login(String username, String password) {

        try (Connection conn = AccessConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_LOGIN)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("profile"),
                        rs.getString("sector")
                );
            }

        } catch (Exception e) {
            System.err.println("Erro login: " + e.getMessage());
        }

        return null;
    }

    // ======================
    // LISTAR USUÁRIOS
    // ======================
    public List<User> findAll() {

        List<User> users = new ArrayList<>();

        String sql = "SELECT id, username, name, profile, sector FROM tb_users";

        try (Connection conn = AccessConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(
                        new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("name"),
                                rs.getString("profile"),
                                rs.getString("sector")
                        )
                );
            }

        } catch (Exception e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
        }

        return users;
    }
}
