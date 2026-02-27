package br.com.sgp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.sgp.dao.OrderDAO;
import br.com.sgp.model.Order;
import br.com.sgp.view.sector.DefaultSectorView;
import br.com.sgp.view.sector.form.ThermalCuttingForm;

public class DefaultSectorController {

	private final DefaultSectorView view;
	private final OrderDAO dao;

	private Order selectedOrder;

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private String formatDate(Date date) {
		if (date == null) {
			return "";
		}
		return sdf.format(date);
	}
	
	public DefaultSectorController(DefaultSectorView view) {

		this.view = view;
		this.dao = new OrderDAO();
		// DEBUG
		System.out.println(">> DefaultSectorController criado");

		initController();
	}

	private void initController() {
		view.addSearchListener(e -> search());
		view.addClearListener(e -> view.clearFields());
	}

	private void search() {

		String orderNumber = view.getOrderNumber();

		if (orderNumber.isEmpty()) {
			view.showMessage("Informe o número do pedido.");
			return;
		}

		System.out.println(">> Buscando pedido: " + orderNumber);

		Order order = dao.findByOrder(orderNumber);

		if (order == null) {
			view.showMessage("Pedido não encontrado.");
			view.clearFields();
			selectedOrder = null;
			return;
		}

		selectedOrder = order;

		view.setOrder(order);

		selectDinamicForm(order);

		System.out.println(">> Pedido carregado com sucesso.");
	}

	private void selectThermalCutting(Order order) {

		if (!(view.getCurrentForm() instanceof ThermalCuttingForm)) {
			return;
		}

		ThermalCuttingForm form = (ThermalCuttingForm) view.getCurrentForm();
		
		System.out.println(view.getCurrentForm());

		form.getTxtLinhaMontagem().setText(order.getLinhaMontagem());
		form.getTxtDataPlano().setText(formatDate(order.getDataPlano()));
		form.getTxtDuplicada().setText(order.getDuplicada());
		form.getTxtDataRecebimento().setText(formatDate(order.getDataEntrega()));
		form.getTxtProgCorte().setText(order.getProgCorte());
		form.getTxtObservation().setText(order.getObservacao());
		form.getChkDuplicada().setSelected(order.getDuplicada() != null && !order.getDuplicada().trim().isEmpty());

	}

	private void selectDinamicForm(Order order) {

		String sector = view.getSectorName();

		switch (sector) {

		case "THERMAL_CUTTING":
			selectThermalCutting(order);
			break;

//		case "BEAM_ASSEMBLY":
//			selectBeamAssembly(order);
//			break;
//
//		case "NECK_WELDING":
//			selectNeckWelding(order);
//			break;
//		}
		}
		
		

//    private String format(java.util.Date date) {
//        return date == null ? "" : sdf.format(date);
//    }
	}
}
