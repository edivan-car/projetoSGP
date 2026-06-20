package br.com.sgp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import br.com.sgp.util.PasswordUtil;

import br.com.sgp.model.User;
import br.com.sgp.util.ConnectionFactory;

public class UserDAO {

    private static final String SQL_LOGIN =
            "SELECT id, username, name, profile, sector " +
            "FROM tb_users " +
            "WHERE username = ? AND password = ? AND active = TRUE";

    // ======================
    // LOGIN
    // ======================
    public User login(String username, String password) {

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_LOGIN)) {

            ps.setString(1, username);
            ps.setString(2, PasswordUtil.hash(password));

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
    // LISTAR USUÁRIOS (APENAS ATIVOS)
    // ======================
    public List<User> findAll() {

        List<User> users = new ArrayList<>();

        String sql = "SELECT id, username, name, profile, sector " +
                     "FROM tb_users WHERE active = TRUE";

        try (Connection conn = ConnectionFactory.getConnection();
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

    // ======================
    // INSERIR USUÁRIO
    // ======================
    public boolean insert(User user) {

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Senha é obrigatória para novo usuário");
        }

        String sql =
                "INSERT INTO tb_users (username, password, name, profile, sector, active) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, PasswordUtil.hash(user.getPassword()));
            ps.setString(3, user.getName());
            ps.setString(4, user.getProfile());
            ps.setString(5, user.getSector());
            ps.setBoolean(6, user.isActive());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
            return false;
        }
    }

    // ======================
    // ATUALIZAR USUÁRIO (SEM SENHA)
    // ======================
    public boolean update(User user) {

        String sql =
                "UPDATE tb_users SET " +
                "username = ?, name = ?, profile = ?, sector = ?, active = ? " +
                "WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getName());
            ps.setString(3, user.getProfile());
            ps.setString(4, user.getSector());
            ps.setBoolean(5, user.isActive());
            ps.setInt(6, user.getId());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            return false;
        }
    }
    
    public boolean resetPassword(int userId) {
        String sql = "UPDATE tb_users SET password = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, PasswordUtil.hash(PasswordUtil.SENHA_GENERICA));
            ps.setInt(2, userId);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.err.println("Erro ao resetar senha: " + e.getMessage());
            return false;
        }
    }

    public boolean updatePassword(int userId, String newPassword) {
        String sql = "UPDATE tb_users SET password = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, PasswordUtil.hash(newPassword));
            ps.setInt(2, userId);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.err.println("Erro ao atualizar senha: " + e.getMessage());
            return false;
        }
    }
    
    public String getPasswordHash(int userId) {
        String sql = "SELECT password FROM tb_users WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("password");
            }

        } catch (Exception e) {
            System.err.println("Erro ao buscar hash: " + e.getMessage());
        }
        return null;
    }
}
