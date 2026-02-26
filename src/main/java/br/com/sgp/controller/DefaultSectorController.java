package br.com.sgp.controller;

import java.text.SimpleDateFormat;

import br.com.sgp.dao.OrderDAO;
import br.com.sgp.model.Order;
import br.com.sgp.view.sector.DefaultSectorView;

public class DefaultSectorController {

    private final DefaultSectorView view;
    private final OrderDAO dao;
    
    private Order selectedOrder;

    private final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public DefaultSectorController(DefaultSectorView view) {
    	
    	this.view = view;
		this.dao = new OrderDAO();
		// DEBUG
    	System.out.println(">> DefaultSectorController criado");
    	
        initController();
    }
    
    private void initController() {
    	view.addSearchListener(e -> search());
    }
    
    private void search() {

        String pedido = view.getPedido();

        if (pedido.isEmpty()) {
            view.showMessage("Informe o número do pedido.");
            return;
        }

        System.out.println(">> Buscando pedido: " + pedido);

        Order order = dao.findByPedido(pedido);

        if (order == null) {
            view.showMessage("Pedido não encontrado.");
            view.clearFields();
            selectedOrder = null;
            return;
        }

        selectedOrder = order;

        view.setOrder(order);

        System.out.println(">> Pedido carregado com sucesso.");
    }
        
    private String format(java.util.Date date) {
        return date == null ? "" : df.format(date);
    }
}
