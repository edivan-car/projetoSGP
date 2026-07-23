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

public class ResourceDAO {

    private static final String SQL_FIND_ALL_ACTIVE =
            "SELECT id, [code], legacy_code, [description], category, " +
            "resource_type, unit_code, active, created_at, updated_at " +
            "FROM tb_resource " +
            "WHERE active = TRUE " +
            "ORDER BY [code]";

    public List<Resource> findAllActive() {
        List<Resource> resources = new ArrayList<Resource>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_FIND_ALL_ACTIVE);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                resources.add(new Resource(
                        resultSet.getInt("id"),
                        resultSet.getString("code"),
                        resultSet.getString("legacy_code"),
                        resultSet.getString("description"),
                        resultSet.getString("category"),
                        resultSet.getString("resource_type"),
                        resultSet.getString("unit_code"),
                        resultSet.getBoolean("active"),
                        getLocalDateTime(resultSet, "created_at"),
                        getLocalDateTime(resultSet, "updated_at")));
            }

            return resources;

        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Não foi possível carregar os recursos ativos.",
                    exception);
        }
    }

    private LocalDateTime getLocalDateTime(
            ResultSet resultSet,
            String column) throws SQLException {

        Timestamp timestamp = resultSet.getTimestamp(column);

        return timestamp == null
                ? null
                : timestamp.toLocalDateTime();
    }
}