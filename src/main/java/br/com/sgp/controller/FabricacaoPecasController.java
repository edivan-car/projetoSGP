package br.com.sgp.controller;

import br.com.sgp.dao.OrderDAO;
import br.com.sgp.model.Order;
import br.com.sgp.view.sector.FabricacaoPecasView;

public class FabricacaoPecasController {

    private final FabricacaoPecasView view;
    private final OrderDAO dao = new OrderDAO();

    public FabricacaoPecasController(FabricacaoPecasView view) {
        this.view = view;
        initController();
        initSectorControllers();
    }

    private void initController() {
        view.addSearchListener(e -> search());
    }

    private void initSectorControllers() {
        // Corte Térmico
        new ThermalCuttingController(view.getThermalForm());

        // Corte a Plasma — passa a view para acessar getOrderNumber()
        new PlasmaCuttingController(view.getPlasmaForm(), view);
    }

    private void search() {
        String orderNumber = view.getOrderNumber();

        if (orderNumber.isEmpty()) {
            view.showMessage("Informe o número do pedido.");
            return;
        }

        Order order = dao.findByOrder(orderNumber);

        if (order == null) {
            view.showMessage("Pedido não encontrado: " + orderNumber);
            view.clearCard();
            return;
        }

        view.setOrder(order);
        populateActiveTab(order);
    }

    private void populateActiveTab(Order order) {
        switch (view.getSelectedTab()) {
            case 0: populateThermal(order);  break;
            case 1: populatePlasma(order);   break;
            default: break;
        }
    }

    private void populateThermal(Order order) {
        // campos já preenchidos pelo ThermalCuttingController via REG
        // aqui não populamos automaticamente — fluxo de INFO/REG se mantém
    }

    private void populatePlasma(Order order) {
        view.getPlasmaForm().getTxtDate().setText(
            order.getDataCorte() != null
                ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(order.getDataCorte()) : "");
        view.getPlasmaForm().getTxtShift().setText(
            order.getTurnoCorte() != null ? order.getTurnoCorte() : "");
        view.getPlasmaForm().getTxtRack().setText(
            order.getRack() != null ? order.getRack() : "");
        view.getPlasmaForm().getTxtObservation().setText(
            order.getObservacao() != null ? order.getObservacao() : "");
    }
}