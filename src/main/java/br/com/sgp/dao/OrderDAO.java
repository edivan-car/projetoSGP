package br.com.sgp.dao;

import br.com.sgp.model.Order;
import br.com.sgp.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public Order findByPedido(String pedido) {

        System.out.println(">> OrderDAO.findByPedido(" + pedido + ")");

        String sql =
            "SELECT [PEDIDO], [PROJETO], [Linha], [Programação], " +
            "[Data_Corte], [Turno], " +
            "[Data_Mont], [Turno_Mont], " +
            "[Data_SoldaPesc], [Turno_SoldaPesc], [Observacoes] " +
            "FROM tb_orders " +
            "WHERE [PEDIDO] = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pedido);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new Order(
                        rs.getString("PEDIDO"),
                        rs.getString("PROJETO"),
                        rs.getString("Linha"),
                        rs.getString("Programação"),
                        rs.getDate("Data_Corte"),
                        rs.getString("Turno"),
                        rs.getDate("Data_Mont"),
                        rs.getString("Turno_Mont"),
                        rs.getDate("Data_SoldaPesc"),
                        rs.getString("Turno_SoldaPesc"),
                        rs.getString("Observacoes")
                    );
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar pedido", e);
        }

        return null;
    }
}
