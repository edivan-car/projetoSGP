package br.com.sgp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.sgp.model.Resource;
import br.com.sgp.util.ConnectionFactory;
import br.com.sgp.util.ConnectionProvider;

public class ResourceDAO {

	private final ConnectionProvider connectionProvider;

	public ResourceDAO() {
	    this(ConnectionFactory::getConnection);
	}

	public ResourceDAO(ConnectionProvider connectionProvider) {
	    this.connectionProvider = connectionProvider;
	}

    private static final String SQL_FIND_ALL_ACTIVE =
            "SELECT id, [code], legacy_code, [description], category, " +
            "resource_type, unit_code, active, created_at, updated_at " +
            "FROM tb_resource " +
            "WHERE active = TRUE " +
            "ORDER BY [code]";

    private static final String SQL_FIND_ALL =
            "SELECT id, [code], legacy_code, [description], category, " +
            "resource_type, unit_code, active, created_at, updated_at " +
            "FROM tb_resource " +
            "ORDER BY [code]";

    public List<Resource> findAllActive() {
        List<Resource> resources = new ArrayList<Resource>();

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_FIND_ALL_ACTIVE);
             ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				resources.add(mapResource(resultSet));
			}

            return resources;

        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Não foi possível carregar os recursos ativos.",
                    exception);
        }
    }

    public List<Resource> findAll() {
        List<Resource> resources = new ArrayList<Resource>();

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_FIND_ALL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                resources.add(mapResource(resultSet));
            }

            return resources;

        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Não foi possível carregar os recursos.",
                    exception);
        }
    }

    private Resource mapResource(
            ResultSet resultSet) throws SQLException {

        return new Resource(
                resultSet.getInt("id"),
                resultSet.getString("code"),
                resultSet.getString("legacy_code"),
                resultSet.getString("description"),
                resultSet.getString("category"),
                resultSet.getString("resource_type"),
                resultSet.getString("unit_code"),
                resultSet.getBoolean("active"),
                getLocalDateTime(resultSet, "created_at"),
                getLocalDateTime(resultSet, "updated_at"));
    }

    private LocalDateTime getLocalDateTime(
            ResultSet resultSet,
            String column) throws SQLException {

        Timestamp timestamp = resultSet.getTimestamp(column);

        return timestamp == null
                ? null
                : timestamp.toLocalDateTime();
    }

    public boolean existsByCode(
            String code,
            int excludedId) {

        String sql =
                "SELECT Count(*) " +
                "FROM tb_resource " +
                "WHERE [code] = ? AND id <> ?";

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, code);
            statement.setInt(2, excludedId);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next()
                        && resultSet.getInt(1) > 0;
            }

        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Não foi possível validar o código do recurso.",
                    exception);
        }
    }

    public boolean insert(Resource resource) {
        String sql =
                "INSERT INTO tb_resource (" +
                "[code], legacy_code, [description], category, " +
                "resource_type, unit_code, active, created_at" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, resource.getCode());
            statement.setString(2, resource.getLegacyCode());
            statement.setString(3, resource.getDescription());
            statement.setString(4, resource.getCategory());
            statement.setString(5, resource.getResourceType());
            statement.setString(6, resource.getUnitCode());
            statement.setBoolean(7, resource.isActive());
            statement.setTimestamp(
                    8,
                    Timestamp.valueOf(LocalDateTime.now()));

            return statement.executeUpdate() == 1;

        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Não foi possível cadastrar o recurso.",
                    exception);
        }
    }

    public boolean update(Resource resource) {
        String sql =
                "UPDATE tb_resource SET " +
                "[code] = ?, " +
                "legacy_code = ?, " +
                "[description] = ?, " +
                "category = ?, " +
                "resource_type = ?, " +
                "unit_code = ?, " +
                "active = ?, " +
                "updated_at = ? " +
                "WHERE id = ?";

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, resource.getCode());
            statement.setString(2, resource.getLegacyCode());
            statement.setString(3, resource.getDescription());
            statement.setString(4, resource.getCategory());
            statement.setString(5, resource.getResourceType());
            statement.setString(6, resource.getUnitCode());
            statement.setBoolean(7, resource.isActive());
            statement.setTimestamp(
                    8,
                    Timestamp.valueOf(LocalDateTime.now()));
            statement.setInt(9, resource.getId());

            return statement.executeUpdate() == 1;

        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Não foi possível atualizar o recurso.",
                    exception);
        }
    }

    public boolean setActive(
            int resourceId,
            boolean active) {

        String sql =
                "UPDATE tb_resource SET " +
                "active = ?, updated_at = ? " +
                "WHERE id = ?";

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setBoolean(1, active);
            statement.setTimestamp(
                    2,
                    Timestamp.valueOf(LocalDateTime.now()));
            statement.setInt(3, resourceId);

            return statement.executeUpdate() == 1;

        } catch (Exception exception) {
            throw new IllegalStateException(
                    active
                            ? "Não foi possível ativar o recurso."
                            : "Não foi possível inativar o recurso.",
                    exception);
        }
    }
}