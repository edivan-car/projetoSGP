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
    	
    	this.view = null;
		this.dao = new OrderDAO();
		// DEBUG
    	System.out.println(">> DefaultSectorController criado");
    	
        initController();
    }
    
    private void initController() {

    }
        
    private String format(java.util.Date date) {
        return date == null ? "" : df.format(date);
    }
}
