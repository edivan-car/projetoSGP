package br.com.sgp.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;

import br.com.sgp.model.Resource;

class ResourceDAOIntegrationTest {

    @TempDir
    Path temporaryDirectory;

    private ResourceDAO dao;

    @BeforeEach
    void prepareDatabase() throws Exception {
        Path databasePath =
                temporaryDirectory.resolve("resource-test.accdb");

        try (Database database = DatabaseBuilder.create(
                Database.FileFormat.V2010,
                databasePath.toFile())) {
            // Cria somente o arquivo Access vazio.
        }

        String url =
                "jdbc:ucanaccess://" + databasePath.toAbsolutePath();

        try (Connection connection =
                     DriverManager.getConnection(url);
             Statement statement =
                     connection.createStatement()) {

            statement.execute(
                    "CREATE TABLE tb_resource (" +
                    "id COUNTER PRIMARY KEY, " +
                    "[code] TEXT(20) NOT NULL, " +
                    "legacy_code TEXT(20), " +
                    "[description] TEXT(150) NOT NULL, " +
                    "category TEXT(30) NOT NULL, " +
                    "resource_type TEXT(20) NOT NULL, " +
                    "unit_code TEXT(10), " +
                    "active YESNO NOT NULL, " +
                    "created_at DATETIME NOT NULL, " +
                    "updated_at DATETIME" +
                    ")");

            statement.execute(
                    "CREATE UNIQUE INDEX ux_tb_resource_code " +
                    "ON tb_resource ([code])");
        }

        dao = new ResourceDAO(
                () -> DriverManager.getConnection(url));
    }

    @Test
    void shouldInsertAndFindActiveResource() {
        Resource resource = new Resource(
                0,
                "TESTE.101",
                null,
                "EQUIPAMENTO DE TESTE",
                "TESTE",
                "EQUIPAMENTO",
                null,
                true,
                null,
                null);

        assertTrue(dao.insert(resource));
        assertTrue(dao.existsByCode("TESTE.101", 0));

        List<Resource> allResources = dao.findAll();
        List<Resource> activeResources = dao.findAllActive();

        assertEquals(1, allResources.size());
        assertEquals(1, activeResources.size());

        Resource saved = allResources.get(0);

        assertTrue(saved.getId() > 0);
        assertEquals("TESTE.101", saved.getCode());
        assertEquals(
                "EQUIPAMENTO DE TESTE",
                saved.getDescription());
        assertTrue(saved.isActive());
        assertNotNull(saved.getCreatedAt());
        assertFalse(saved.getCode().isEmpty());
    }
    
    @Test
    void shouldIdentifyDuplicateCode() {
        Resource resource = new Resource(
                0,
                "TESTE.101",
                null,
                "EQUIPAMENTO DE TESTE",
                "TESTE",
                "EQUIPAMENTO",
                null,
                true,
                null,
                null);

        assertTrue(dao.insert(resource));

        Resource saved = dao.findAll().get(0);

        assertTrue(
                dao.existsByCode("TESTE.101", 0));

        assertFalse(
                dao.existsByCode(
                        "TESTE.101",
                        saved.getId()));
    }
    
    @Test
    void shouldUpdateResource() {
        Resource resource = new Resource(
                0,
                "TESTE.101",
                null,
                "DESCRIÇÃO ORIGINAL",
                "TESTE",
                "EQUIPAMENTO",
                null,
                true,
                null,
                null);

        assertTrue(dao.insert(resource));

        Resource saved = dao.findAll().get(0);

        Resource updated = new Resource(
                saved.getId(),
                saved.getCode(),
                "CODIGO-ANTIGO",
                "DESCRIÇÃO ATUALIZADA",
                "PRENSA",
                saved.getResourceType(),
                "CRI",
                true,
                saved.getCreatedAt(),
                null);

        assertTrue(dao.update(updated));

        Resource result = dao.findAll().get(0);

        assertEquals(
                "DESCRIÇÃO ATUALIZADA",
                result.getDescription());
        assertEquals("CODIGO-ANTIGO", result.getLegacyCode());
        assertEquals("PRENSA", result.getCategory());
        assertEquals("CRI", result.getUnitCode());
        assertNotNull(result.getUpdatedAt());
    }
    
    @Test
    void shouldDeactivateAndReactivateResource() {
        Resource resource = new Resource(
                0,
                "TESTE.101",
                null,
                "EQUIPAMENTO DE TESTE",
                "TESTE",
                "EQUIPAMENTO",
                null,
                true,
                null,
                null);

        assertTrue(dao.insert(resource));

        Resource saved = dao.findAll().get(0);

        assertTrue(dao.setActive(saved.getId(), false));

        assertEquals(1, dao.findAll().size());
        assertTrue(dao.findAllActive().isEmpty());
        assertFalse(dao.findAll().get(0).isActive());
        assertNotNull(dao.findAll().get(0).getUpdatedAt());

        assertTrue(dao.setActive(saved.getId(), true));

        assertEquals(1, dao.findAllActive().size());
        assertTrue(dao.findAll().get(0).isActive());
    }
}