package br.com.sgp.dao;

import br.com.sgp.model.Order;
import br.com.sgp.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    private static final int DEFAULT_LIMIT = 100;

    public List<Order> findRecent() {
    	
    	System.out.println(">> OrderDAO.findRecent()");

        List<Order> list = new ArrayList<>();

        String sql =
        	    "SELECT TOP " + DEFAULT_LIMIT + " " +
        	    "[PEDIDO], [PROJETO], [Linha], [Programação], " +
        	    "[Data_Corte], [Turno], " +
        	    "[Data_Mont], [Turno_Mont], " +
        	    "[Data_SoldaPesc], [Turno_SoldaPesc], [Observacoes] " +
        	    "FROM tb_orders " +
        	    "ORDER BY [PEDIDO] DESC";

        System.out.println(">> SQL final:\n" + sql);

        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
        	
        	System.out.println(">> SQL executado");

        	int count = 0;
            while (rs.next()) {
                list.add(new Order(
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
                ));
                count++;
                System.out.println(">> Pedido carregado: " + rs.getString("PEDIDO"));
            }
            System.out.println(">> Total de registros: " + count);
            
            System.out.println(">> registros carregados do banco: " + list.size());

        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar pedidos recentes", e);
        }

        return list;
    }
}
