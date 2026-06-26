package br.com.sgp.controller;

import br.com.sgp.dao.OrderDAO;
import br.com.sgp.model.Order;
import br.com.sgp.view.sector.FabricacaoPecasView;

public class FabricacaoPecasController {

    private final FabricacaoPecasView view;
    private final OrderDAO dao = new OrderDAO();
    private final java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");

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
        new ThermalCuttingController(view.getThermalForm(), view);

        // Corte a Plasma — passa a view para acessar getOrderNumber()
        new PlasmaCuttingController(view.getPlasmaForm(), view);
    }

    private void search() { // <- modificar
        String orderNumber = view.getOrderNumber();

        if (orderNumber.isEmpty()) {
            view.showMessage("Informe o número do pedido.");
            return;
        }

        Order order = dao.findByOrder(orderNumber);

        if (order == null) {
            view.clearCard();          // <- limpa tabela mas NÃO limpa o campo pedido
            view.setFormsEditable(true);  // <- pedido novo: formulários liberados
            view.setRegisterEnabled(true); // <- botão registrar habilitado
            return;
        }

        view.setOrder(order);
        view.setFormsEditable(false);  // <- pedido encontrado: bloqueia edição
        view.setRegisterEnabled(false); // <- botão registrar desabilitado
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
        view.getThermalForm().getTxtLinhaMontagem().setText(
            order.getLinhaMontagem() != null ? order.getLinhaMontagem() : "");
        view.getThermalForm().getTxtDataPlano().setText(formatDate(order.getDataPlano()));
        view.getThermalForm().getTxtDataRecebimento().setText(formatDate(order.getDataEntrega()));
        view.getThermalForm().getTxtProgCorte().setText(
            order.getProgCorte() != null ? order.getProgCorte() : "");
        view.getThermalForm().getTxtObservation().setText(
            order.getObservacao() != null ? order.getObservacao() : "");
        view.getThermalForm().getChkDuplicada().setSelected(
            order.getDuplicada() != null && !order.getDuplicada().trim().isEmpty());
    }

    private void populatePlasma(Order order) {
        view.getPlasmaForm().getTxtDate().setText(
            formatDate(order.getDataCorte()));
        view.getPlasmaForm().getTxtShift().setText(
            order.getTurnoCorte() != null ? order.getTurnoCorte() : "");
        view.getPlasmaForm().getTxtRack().setText(
            order.getRack() != null ? order.getRack() : "");
        view.getPlasmaForm().getTxtObservation().setText(
            order.getObservacao() != null ? order.getObservacao() : "");
    }

    private String formatDate(java.util.Date date) {
        return date != null ? dateFormat.format(date) : "";
    }
}
