package br.com.sgp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingUtilities;

import br.com.sgp.dao.ResourceDAO;
import br.com.sgp.model.Resource;
import br.com.sgp.view.resource.ResourceView;
import br.com.sgp.view.resource.ResourceFormView;

public class ResourceController {

    private final ResourceView view;
    private final ResourceDAO dao;

    private List<Resource> resources =
            new ArrayList<Resource>();

    public ResourceController(
            ResourceView view,
            ResourceDAO dao) {

        this.view = view;
        this.dao = dao;

        configureActions();
        loadResources();
    }

    private void configureActions() {
    	view.getBtnNew().addActionListener(
    	        event -> openNewResourceForm());
    	
        view.getBtnRefresh().addActionListener(
                event -> refreshResources());

        view.getTxtSearch()
                .getDocument()
                .addDocumentListener(
                        new DocumentListener() {

            @Override
            public void insertUpdate(
                    DocumentEvent event) {
                filterResources();
            }

            @Override
            public void removeUpdate(
                    DocumentEvent event) {
                filterResources();
            }

            @Override
            public void changedUpdate(
                    DocumentEvent event) {
                filterResources();
            }
        });
    }
    
    private void openNewResourceForm() {
        ResourceFormView form =
                new ResourceFormView(
                        SwingUtilities.getWindowAncestor(view),
                        null);

        form.getBtnSave().addActionListener(
                event -> saveNewResource(form));

        form.setVisible(true);
    }
    
    private void saveNewResource(
            ResourceFormView form) {

        if (!validateForm(form, 0)) {
            return;
        }

        Resource resource = new Resource(
                0,
                form.getCode(),
                nullIfEmpty(form.getLegacyCode()),
                form.getDescription(),
                form.getCategory(),
                form.getResourceType(),
                nullIfEmpty(form.getUnitCode()),
                form.isResourceActive(),
                null,
                null);

        try {
            if (dao.insert(resource)) {
                JOptionPane.showMessageDialog(
                        form,
                        "Equipamento cadastrado com sucesso.",
                        "Cadastro",
                        JOptionPane.INFORMATION_MESSAGE);

                form.dispose();
                refreshResources();
            }

        } catch (IllegalStateException exception) {
            showError(exception.getMessage());
        }
    }

    private void loadResources() {
        try {
            resources = dao.findAll();
            filterResources();

        } catch (IllegalStateException exception) {
            showError(exception.getMessage());
        }
    }

    private void filterResources() {
        String search =
                view.getTxtSearch()
                        .getText()
                        .trim()
                        .toUpperCase(Locale.ROOT);

        List<Resource> filtered =
                new ArrayList<Resource>();

        for (Resource resource : resources) {
            if (matchesSearch(resource, search)) {
                filtered.add(resource);
            }
        }

        fillTable(filtered);
    }
    
    private void refreshResources() {
        view.getTxtSearch().setText("");
        view.getTable().clearSelection();

        loadResources();

        view.getTxtSearch()
                .requestFocusInWindow();
    }

    private boolean matchesSearch(
            Resource resource,
            String search) {

        if (search.isEmpty()) {
            return true;
        }

        String code = safeUpper(resource.getCode());
        String description =
                safeUpper(resource.getDescription());

        return code.contains(search)
                || description.contains(search);
    }

    private String safeUpper(String value) {
        return value == null
                ? ""
                : value.toUpperCase(Locale.ROOT);
    }

    private void fillTable(
            List<Resource> filteredResources) {

        DefaultTableModel model =
                view.getModel();

        model.setRowCount(0);

        for (Resource resource : filteredResources) {
            model.addRow(new Object[] {
                    resource.getId(),
                    resource.getCode(),
                    resource.getDescription(),
                    resource.getCategory(),
                    resource.getResourceType(),
                    resource.getUnitCode(),
                    resource.isActive()
                            ? "Ativo"
                            : "Inativo"
            });
        }
    }
    
    private boolean validateForm(
            ResourceFormView form,
            int excludedId) {

        if (form.getCode().isEmpty()) {
            return showValidation(
                    form,
                    "Informe o código do equipamento.");
        }

        if (form.getCode().length() > 20) {
            return showValidation(
                    form,
                    "O código deve possuir no máximo 20 caracteres.");
        }

        if (form.getDescription().isEmpty()) {
            return showValidation(
                    form,
                    "Informe a descrição do equipamento.");
        }

        if (form.getDescription().length() > 150) {
            return showValidation(
                    form,
                    "A descrição deve possuir no máximo 150 caracteres.");
        }

        if (form.getCategory().isEmpty()) {
            return showValidation(
                    form,
                    "Informe a categoria do equipamento.");
        }

        if (form.getCategory().length() > 30) {
            return showValidation(
                    form,
                    "A categoria deve possuir no máximo 30 caracteres.");
        }

        if (form.getLegacyCode().length() > 20) {
            return showValidation(
                    form,
                    "O código anterior deve possuir no máximo 20 caracteres.");
        }

        if (form.getUnitCode().length() > 10) {
            return showValidation(
                    form,
                    "A unidade deve possuir no máximo 10 caracteres.");
        }

        if (dao.existsByCode(
                form.getCode(),
                excludedId)) {

            return showValidation(
                    form,
                    "Já existe um equipamento com esse código.");
        }

        return true;
    }
    
    private String nullIfEmpty(String value) {
        return value.isEmpty()
                ? null
                : value;
    }

    private boolean showValidation(
            ResourceFormView form,
            String message) {

        JOptionPane.showMessageDialog(
                form,
                message,
                "Atenção",
                JOptionPane.WARNING_MESSAGE);

        return false;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                view,
                message,
                "Erro",
                JOptionPane.ERROR_MESSAGE);
    }
}