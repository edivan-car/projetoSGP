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
		view.addSearchListener(e -> search());
		view.addCreateListener(e -> save());
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

	private void save() {
		String orderNumber = view.getOrderNumber();

		if (orderNumber.isEmpty()) {
			view.showMessage("Informe o número do pedido.");
			return;
		}

		String sector = view.getSectorName();

		switch (sector) {

		case "THERMAL_CUTTING":
			saveThermalCutting(orderNumber);
			break;

		default:
			view.showMessage("Setor não implementado.");
		}
	}

	private void saveThermalCutting(String orderNumber) {
		
		if (!(view.getCurrentForm() instanceof ThermalCuttingForm)) {
	        view.showMessage("Formulário inválido.");
	        return;
	    }

	    ThermalCuttingForm form =
	            (ThermalCuttingForm) view.getCurrentForm();

	    // =========================
	    // CAPTURA DOS CAMPOS
	    // =========================

	    String linha = form.getTxtLinhaMontagem().getText().trim();
	    String dataPlano = form.getTxtDataPlano().getText().trim();
	    String dataEntrega = form.getTxtDataRecebimento().getText().trim();
	    String progCorte = form.getTxtProgCorte().getText().trim();
	    String observacao = form.getTxtObservation().getText().trim();
	    boolean duplicadaCheck = form.getChkDuplicada().isSelected();

	    // =========================
	    // VALIDAÇÕES
	    // =========================

	    if (linha.isEmpty()) {
	        view.showMessage("Linha é obrigatória.");
	        return;
	    }

	    if (dataPlano.isEmpty()) {
	        view.showMessage("Data Plano é obrigatória.");
	        return;
	    }

	    if (dataEntrega.isEmpty()) {
	        view.showMessage("Data Entrega é obrigatória.");
	        return;
	    }

	    if (progCorte.isEmpty()) {
	        view.showMessage("Prog Corte é obrigatória.");
	        return;
	    }

	    String duplicada = duplicadaCheck ? "S" : "";

	    try {

	        java.text.SimpleDateFormat sdf =
	                new java.text.SimpleDateFormat("dd/MM/yyyy");

	        java.util.Date dtPlano = sdf.parse(dataPlano);
	        java.util.Date dtEntrega = sdf.parse(dataEntrega);

	        // =========================
	        // VERIFICAR SE EXISTE
	        // =========================

	        Order existing = dao.findByOrder(orderNumber);

	        if (existing == null) {

	            // NÃO EXISTE → INSERT
	            dao.insertThermalCutting(
	                    orderNumber,
	                    linha,
	                    dtPlano,
	                    dtEntrega,
	                    progCorte,
	                    observacao,
	                    duplicada
	            );

	            view.showMessage("Pedido cadastrado com sucesso.");

	        } else {

	            // EXISTE → VERIFICAR SE JÁ TEM DADOS

	            boolean jaCadastrado =
	                    existing.getLinhaMontagem() != null && !existing.getLinhaMontagem().trim().isEmpty()
	                    && existing.getDataPlano() != null
	                    && existing.getDataEntrega() != null
	                    && existing.getProgCorte() != null && !existing.getProgCorte().trim().isEmpty();

	            if (jaCadastrado) {

	                view.showMessage("Pedido já possui cadastro de Corte Térmico. Utilize o botão Edit.");
	                return;
	            }

	            // Se chegou aqui → existe mas está incompleto
	            dao.updateThermalCutting(
	                    orderNumber,
	                    linha,
	                    dtPlano,
	                    dtEntrega,
	                    progCorte,
	                    observacao,
	                    duplicada
	            );

	            view.showMessage("Pedido atualizado com sucesso.");
	        }
	        
            form.clearForm();
            view.clearFields(); // limpa os campos da parte superior

	    } catch (Exception e) {
	        e.printStackTrace();
	        view.showMessage("Erro ao salvar dados.");
	    }
		
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
