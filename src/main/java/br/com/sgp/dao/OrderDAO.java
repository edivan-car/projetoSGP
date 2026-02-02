package br.com.sgp.dao;

import br.com.sgp.model.Order;
//import br.com.sgp.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    private static final int DEFAULT_LIMIT = 100;

    public List<Order> findRecent() {

        List<Order> list = new ArrayList<>();

        String sql = "SELECT"
        		+ "id,"
        		+ "pedido, "
        		+ "projeto, "
                + "linha_montagem, "
                + "programacao_mes, "
                + "data_corte, "
                + "turno_corte, "
                + "data_montagem, "
                + "turno_montagem, "
                + "data_solda_pescoco, "
                + "turno_solda_pescoco "
            + "FROM tb_order WHERE active = 1 ORDER BY id DESC LIMIT ?";
		/*
		 * try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement
		 * ps = conn.prepareStatement(sql)) {
		 * 
		 * ps.setInt(1, DEFAULT_LIMIT);
		 * 
		 * ResultSet rs = ps.executeQuery();
		 * 
		 * while (rs.next()) { Order o = new Order( rs.getInt("id"),
		 * rs.getString("pedido"), rs.getString("projeto"),
		 * rs.getString("linha_montagem"), rs.getString("programacao_mes"),
		 * rs.getDate("data_corte"), rs.getString("turno_corte"),
		 * rs.getDate("data_montagem"), rs.getString("turno_montagem"),
		 * rs.getDate("data_solda_pescoco"), rs.getString("turno_solda_pescoco") );
		 * list.add(o); }
		 * 
		 * } catch (Exception e) { throw new
		 * RuntimeException("Erro ao carregar pedidos", e); }
		 */
        return list;
    }
    
}
