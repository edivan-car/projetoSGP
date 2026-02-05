package br.com.sgp.view.sector;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;

import br.com.sgp.view.sector.table.OrderTableModel;

public class SectorBaseView extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    // Pesquisa
    private JTextField txtSearch;
    private JButton btnSearch;

    // Tabela
    private JTable table;
    private JPanel currentForm;

    // Container do formulário (dinâmico)
    private JPanel formContainer;

    // Botões
    private JButton btnCreate;
    private JButton btnEdit;
    private JButton btnClear;

    public SectorBaseView(String title) {
        super(title, true, true, true, true);

        setSize(900, 600);
        setLayout(new BorderLayout(10, 10));
        setLocation(30, 30);

        add(createSearchPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    // =========================
    // PAINEL DE PESQUISA
    // =========================
    private JPanel createSearchPanel() {

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        txtSearch = new JTextField();
        btnSearch = new JButton("🔍");

        panel.add(txtSearch, BorderLayout.CENTER);
        panel.add(btnSearch, BorderLayout.EAST);

        return panel;
    }

    // =========================
    // PAINEL CENTRAL
    // =========================
    private JPanel createCenterPanel() {

        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        // Tabela
        center.add(createTablePanel(), BorderLayout.CENTER);

        // Container fixo do formulário
        center.add(createFormContainer(), BorderLayout.SOUTH);

        return center;
    }

    // =========================
    // TABELA
    // =========================
    private JScrollPane createTablePanel() {

        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(22);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Registros"));

        return scroll;
    }

    public void configureColumnWidths() {

        TableColumnModel columnModel = table.getColumnModel();

        if (columnModel.getColumnCount() < 11) {
            return;
        }

        columnModel.getColumn(0).setPreferredWidth(60);
        columnModel.getColumn(1).setPreferredWidth(120);
        columnModel.getColumn(2).setPreferredWidth(120);
        columnModel.getColumn(3).setPreferredWidth(80);

        columnModel.getColumn(4).setPreferredWidth(110);
        columnModel.getColumn(5).setPreferredWidth(40);

        columnModel.getColumn(6).setPreferredWidth(110);
        columnModel.getColumn(7).setPreferredWidth(40);

        columnModel.getColumn(8).setPreferredWidth(110);
        columnModel.getColumn(9).setPreferredWidth(40);

        columnModel.getColumn(10).setPreferredWidth(200);
    }


    // =========================
    // CONTAINER DO FORMULÁRIO
    // =========================
    private JPanel createFormContainer() {

        formContainer = new JPanel(new BorderLayout());
        formContainer.setBorder(
                BorderFactory.createTitledBorder("Dados do Apontamento")
        );

        formContainer.setPreferredSize(new Dimension(700, 220));
        formContainer.setMinimumSize(new Dimension(700, 220));

        return formContainer;
    }

    // =========================
    // FORMULÁRIO DINÂMICO
    // =========================
    
    public JPanel getCurrentForm() {
        return currentForm;
    }
    
    public void setForm(JPanel form) {

        this.currentForm = form;

        formContainer.removeAll();

        JPanel centerWrapper = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        centerWrapper.add(form, gbc);

        formContainer.add(centerWrapper, BorderLayout.CENTER);

        formContainer.revalidate();
        formContainer.repaint();
    }


    // =========================
    // BOTÕES
    // =========================
    private JPanel createButtonPanel() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));

        btnCreate = new JButton("Cadastro");
        btnEdit = new JButton("Editar");
        btnClear = new JButton("Limpar");

        panel.add(btnCreate);
        panel.add(btnEdit);
        panel.add(btnClear);

        return panel;
    }

    // =========================
    // GETTERS
    // =========================
    public JTable getTable() {
        return table;
    }

    public void setTableModel(OrderTableModel model) {
    	System.out.println(">> setTableModel chamado");
    	
        table.setModel(model);
        configureColumnWidths();
    }
    
    public JButton getBtnCreate() {
        return btnCreate;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public JButton getBtnClear() {
        return btnClear;
    }
}
