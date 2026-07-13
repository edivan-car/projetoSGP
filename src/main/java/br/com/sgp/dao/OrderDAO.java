package br.com.sgp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import br.com.sgp.model.Order;
import br.com.sgp.util.ConnectionFactory;

public class OrderDAO {

	public Order findByOrder(String orderNumber) {

		System.out.println(">> OrderDAO.findByPedido(" + orderNumber + ")");

		String sql = "SELECT [PEDIDO], [PROJETO], [Linha], [Programação], [Data_Plano], "
				+ "[Data_Entrega], [Data_Corte], [Turno], " + "[Data_Mont], [Turno_Mont], "
				+ "[Data_SoldaPesc], [Turno_SoldaPesc], [Prog_Corte], [Duplicada], [Rack], [Observacoes] " + "FROM tb_orders "
				+ "WHERE [PEDIDO] = ?";

		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, orderNumber);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					return new Order(rs.getString("PEDIDO"), rs.getString("PROJETO"), rs.getString("Linha"),
							rs.getString("Programação"), rs.getDate("Data_Plano"), rs.getDate("Data_Entrega"),
							rs.getDate("Data_Corte"), rs.getString("Turno"), rs.getDate("Data_Mont"),
							rs.getString("Turno_Mont"), rs.getDate("Data_SoldaPesc"), rs.getString("Turno_SoldaPesc"),
							rs.getString("Prog_Corte"), rs.getString("Duplicada"), rs.getString("Rack"), rs.getString("Observacoes"));
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar pedido", e);
		}
		
		return null;
	}

	public void insertThermalCutting(String orderNumber, String linha, Date dataPlano, Date dataEntrega, String progCorte,
			String observacao, String duplicada) {

		String sql = "INSERT INTO tb_orders "
				+ "([PEDIDO], [Linha], [Data_Plano], [Data_Entrega], [Prog_Corte], [Observacoes], [Duplicada]) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, orderNumber);
			ps.setString(2, linha);
			ps.setDate(3, new java.sql.Date(dataPlano.getTime()));
			ps.setDate(4, new java.sql.Date(dataEntrega.getTime()));
			ps.setString(5, progCorte);
			ps.setString(6, observacao);
			ps.setString(7, duplicada);

			ps.executeUpdate();

		} catch (Exception e) {
			throw new RuntimeException(
					"Erro ao cadastrar apontamento térmico", e);
		}

	}

	public void updateThermalCutting(String orderNumber, String linha, Date dataPlano, Date dataEntrega, String progCorte,
			String observacao, String duplicada) {

		String sql = "UPDATE tb_orders SET " + "[Linha] = ?, " + "[Data_Plano] = ?, " + "[Data_Entrega] = ?, "
				+ "[Prog_Corte] = ?, " + "[Observacoes] = ?, " + "[Duplicada] = ? " + "WHERE [PEDIDO] = ?";

		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, linha);
			ps.setDate(2, new java.sql.Date(dataPlano.getTime()));
			ps.setDate(3, new java.sql.Date(dataEntrega.getTime()));
			ps.setString(4, progCorte);
			ps.setString(5, observacao);
			ps.setString(6, duplicada);
			ps.setString(7, orderNumber);

			ps.executeUpdate();

		} catch (Exception e) {
			throw new RuntimeException(
			        "Erro ao atualizar apontamento térmico", e);
		}
	}
	
	public void updatePlasmaCutting(String orderNumber, Date dataCorte,
	        String turno, String rack, String observacao) {

	    // busca observação atual para concatenar
	    String obsAtual = "";
	    Order existing = findByOrder(orderNumber);
	    if (existing != null && existing.getObservacao() != null 
	            && !existing.getObservacao().trim().isEmpty()) {
	        obsAtual = existing.getObservacao().trim();
	    }

	    String obsFinal;
	    if (observacao == null || observacao.trim().isEmpty()) {
	        obsFinal = obsAtual;
	    } else if (obsAtual.isEmpty()) {
	        obsFinal = observacao.trim();
	    } else {
	        obsFinal = obsAtual + " | " + observacao.trim();
	    }

	    String sql = "UPDATE tb_orders SET "
	            + "[Data_Corte] = ?, "
	            + "[Turno] = ?, "
	            + "[Rack] = ?, "
	            + "[Observacoes] = ? "
	            + "WHERE [PEDIDO] = ?";

	    try (Connection conn = ConnectionFactory.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setDate(1, new java.sql.Date(dataCorte.getTime()));
	        ps.setString(2, turno);
	        ps.setString(3, rack);
	        ps.setString(4, obsFinal);
	        ps.setString(5, orderNumber);

	        ps.executeUpdate();

	    } catch (Exception e) {
	        throw new RuntimeException("Erro ao atualizar apontamento de corte a plasma", e);
	    }
	}                                               
}
