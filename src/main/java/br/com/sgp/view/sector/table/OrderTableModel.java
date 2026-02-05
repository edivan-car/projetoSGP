package br.com.sgp.view.sector.table;

import br.com.sgp.model.Order;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private final String[] columns = { "Pedido", "Projeto", "Linha Montagem", "Prog/Mês", "Corte", "Turno", "Montagem",
			"Turno", "Solda Pesc.", "Turno", "Observações" };

	private List<Order> orders = new ArrayList<>();
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public int getRowCount() {
		return orders.size();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Order o = orders.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return o.getPedido();
		case 1:
			return o.getProjeto();
		case 2:
			return o.getLinhaMontagem();
		case 3:
			return o.getProgramacaoMes();
		case 4:
			return format(o.getDataCorte());
		case 5:
			return o.getTurnoCorte();
		case 6:
			return format(o.getDataMontagem());
		case 7:
			return o.getTurnoMontagem();
		case 8:
			return format(o.getDataSoldaPescoco());
		case 9:
			return o.getTurnoSoldaPescoco();
		case 10:
			return o.getObservacao();
		default:
			return null;
		}
	}

	public void setOrders(List<Order> orders) {
		System.out.println(">> TableModel.setData(): " + orders.size() + " linhas");
		this.orders = orders;
		fireTableDataChanged();
	}

	public Order getOrderAt(int row) {
		return orders.get(row);
	}

	public void clear() {
		orders.clear();
		fireTableDataChanged();
	}

	  private String format(java.util.Date date) { return date == null ? "" :
	  sdf.format(date); }
	  
//	  public void setData(List<Order> orders) { 
//		  orders.clear(); 
//		  orders.addAll(orders);
//		  fireTableDataChanged(); }
}
