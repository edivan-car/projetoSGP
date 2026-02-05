package br.com.sgp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.sgp.dao.OrderDAO;
import br.com.sgp.model.Order;
import br.com.sgp.view.sector.SectorBaseView;
import br.com.sgp.view.sector.table.OrderTableModel;

public class SectorBaseController {

    private final SectorBaseView view;
    private final OrderDAO dao;
    private final OrderTableModel tableModel;
    
    private Order selectedOrder;

    private final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public SectorBaseController(SectorBaseView view) {
    	
    	// DEBUG
    	System.out.println(">> SectorBaseController criado");
    	
    	this.view = view;
        this.dao = new OrderDAO();

        this.tableModel = new OrderTableModel();
        view.setTableModel(tableModel);

        loadTable();
        initController();
    }
    
    private void initController() {
    	view.getTable().getSelectionModel()
        .addListSelectionListener(e -> {

            if (e.getValueIsAdjusting()) return;

            int row = view.getTable().getSelectedRow();
            selectedOrder = (row == -1)
                    ? null
                    : tableModel.getOrderAt(row);
        });
    }

    private void loadTable() {
    	
    	//DEBUG
    	System.out.println(">> loadTable() chamado");

        List<Order> orders = dao.findRecent();
        System.out.println(">> pedidos retornados: " + orders.size());
        
        List<Object[]> rows = new ArrayList<>();

        for (Order o : orders) {
            rows.add(new Object[] {
                o.getPedido(),
                o.getProjeto(),
                o.getLinhaMontagem(),
                o.getProgramacaoMes(),
                format(o.getDataCorte()),
                o.getTurnoCorte(),
                format(o.getDataMontagem()),
                o.getTurnoMontagem(),
                format(o.getDataSoldaPescoco()),
                o.getTurnoSoldaPescoco(),
                o.getObservacao()
            });
        }
        
        System.out.println(">> linhas preparadas para a tabela: " + rows.size());

        tableModel.setOrders(orders);
    }

    private String format(java.util.Date date) {
        return date == null ? "" : df.format(date);
    }
}
