package br.com.sgp.controller;

import java.util.List;

import javax.swing.JOptionPane;

import br.com.sgp.dao.ResourceDAO;
import br.com.sgp.model.Resource;
import br.com.sgp.view.production.ProductionRecordView;

public class ProductionRecordController {

    private final ProductionRecordView view;
    private final ResourceDAO resourceDAO;

    public ProductionRecordController(
            ProductionRecordView view,
            ResourceDAO resourceDAO) {

        this.view = view;
        this.resourceDAO = resourceDAO;

        loadResources();
    }

    private void loadResources() {
        try {
            List<Resource> resources =
                    resourceDAO.findAllActive();

            view.setResources(resources);

            if (resources.isEmpty()) {
                JOptionPane.showMessageDialog(
                        view,
                        "Não há recursos ativos cadastrados.",
                        "Recursos",
                        JOptionPane.WARNING_MESSAGE);
            }

        } catch (IllegalStateException exception) {
            JOptionPane.showMessageDialog(
                    view,
                    exception.getMessage(),
                    "Erro ao carregar recursos",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}