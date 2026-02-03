package br.com.sgp.controller;

import br.com.sgp.dao.OrderDAO;
import br.com.sgp.model.Order;
import br.com.sgp.view.sector.SectorBaseView;

import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class SectorBaseController {

    private SectorBaseView view;
    private OrderDAO dao;

    private final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public SectorBaseController(SectorBaseView view) {
        this.view = view;
        this.dao = new OrderDAO();

        initController();
        loadTable();
    }

    private void initController() {

        //view.getBtnClear().addActionListener(e -> view.clearFields());

        // Botões Cadastro / Editar entram depois
    }

    private void loadTable() {

        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        List<Order> orders = dao.findRecent();

        for (Order o : orders) {
            model.addRow(new Object[]{
                    o.getPedido(),
                    o.getProjeto(),
                    o.getLinhaMontagem(),
                    o.getProgramacaoMes(),
                    format(o.getDataCorte()),
                    o.getTurnoCorte(),
                    format(o.getDataMontagem()),
                    o.getTurnoMontagem(),
                    format(o.getDataSoldaPescoco()),
                    o.getTurnoSoldaPescoco()
            });
        }
    }

    private String format(java.util.Date date) {
        return date == null ? "" : df.format(date);
    }
}
