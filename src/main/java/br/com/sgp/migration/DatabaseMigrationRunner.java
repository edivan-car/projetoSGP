package br.com.sgp.migration;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

import br.com.sgp.util.ConnectionFactory;
import br.com.sgp.util.ConnectionProvider;

public class DatabaseMigrationRunner {

    private static final String MIGRATION_TABLE =
            "tb_schema_migration";

    private final ConnectionProvider connectionProvider;

    public DatabaseMigrationRunner() {
        this(ConnectionFactory::getConnection);
    }

    public DatabaseMigrationRunner(
            ConnectionProvider connectionProvider) {

        this.connectionProvider = connectionProvider;
    }

    public void migrate() {
        try (Connection connection =
                     connectionProvider.getConnection()) {

            ensureMigrationTable(connection);

        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Não foi possível preparar as migrações do banco.",
                    exception);
        }
    }

    private void ensureMigrationTable(
            Connection connection) throws Exception {

        if (tableExists(connection, MIGRATION_TABLE)) {
            return;
        }

        String sql =
                "CREATE TABLE tb_schema_migration (" +
                "version LONG NOT NULL, " +
                "description TEXT(150) NOT NULL, " +
                "applied_at DATETIME NOT NULL, " +
                "CONSTRAINT pk_schema_migration " +
                "PRIMARY KEY (version)" +
                ")";

        try (Statement statement =
                     connection.createStatement()) {

            statement.execute(sql);
        }
    }

    private boolean tableExists(
            Connection connection,
            String tableName) throws Exception {

        DatabaseMetaData metadata =
                connection.getMetaData();

        try (ResultSet tables =
                     metadata.getTables(
                             null,
                             null,
                             null,
                             new String[] {"TABLE"})) {

            while (tables.next()) {
                String existingTable =
                        tables.getString("TABLE_NAME");

                if (tableName.equalsIgnoreCase(
                        existingTable)) {
                    return true;
                }
            }
        }

        return false;
    }
}