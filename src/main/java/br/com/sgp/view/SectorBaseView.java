package br.com.sgp.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.*;

public class SectorBaseView extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	
	// Pesquisa
    private JTextField txtSearch;
    private JButton btnSearch;

    // Tabela
    private JTable table;
    private DefaultTableModel tableModel;

    // Formulário
    private JTextField txtOrder;
    private JTextField txtDatePlano;
    private JTextField txtShift;
    private JTextArea txtObservation;

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

        // =====================
        // TABELA (cresce)
        // =====================
        JScrollPane tableScroll = createTablePanel();
        center.add(tableScroll, BorderLayout.CENTER);

        // =====================
        // FORMULÁRIO (fixo)
        // =====================
        JPanel formWrapper = new JPanel(new GridBagLayout());

        JPanel formPanel = createFormPanel();
        formPanel.setPreferredSize(new Dimension(650, 180));
        formPanel.setMinimumSize(new Dimension(650, 180));
        formPanel.setMaximumSize(new Dimension(650, 180));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        formWrapper.add(formPanel, gbc);

        center.add(formWrapper, BorderLayout.SOUTH);

        return center;
    }

    // =========================
    // TABELA
    // =========================
    private JScrollPane createTablePanel() {

        tableModel = new DefaultTableModel(
                new Object[]{
                        "Pedido",
                        "Projeto",
                        "Linha Montagem",
                        "Prog/Mês",
                        "Corte",
                        "Turno",
                        "Montagem",
                        "Turno",
                        "Solda Pesc.",
                        "Turno",
                        "Observações"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(22);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        
        configureColumnWidths();

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Registros"));

        return scroll;
    }
    
    private void configureColumnWidths() {

        TableColumnModel columnModel = table.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(60);   // Pedido
        columnModel.getColumn(1).setPreferredWidth(120);  // Projeto
        columnModel.getColumn(2).setPreferredWidth(120);  // Linha Montagem
        columnModel.getColumn(3).setPreferredWidth(80);   // Prog/Mês

        columnModel.getColumn(4).setPreferredWidth(110);   // Corte
        columnModel.getColumn(5).setPreferredWidth(40);   // Corte Turno

        columnModel.getColumn(6).setPreferredWidth(110);  // Montagem
        columnModel.getColumn(7).setPreferredWidth(40);   // Montagem Turno

        columnModel.getColumn(8).setPreferredWidth(110);  // Solda Pesc.
        columnModel.getColumn(9).setPreferredWidth(40);   // Solda Pesc. Turno

        columnModel.getColumn(10).setPreferredWidth(200); // Observações
    }

    // =========================
    // FORMULÁRIO
    // =========================
    private JPanel createFormPanel() {

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        // ======================
        // Linha Montagem
        // ======================
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel("Linha Mont.:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        txtOrder = new JTextField();
        panel.add(txtOrder, gbc);

        gbc.gridwidth = 1;

        // ======================
        // Data / Plano
        // ======================
        gbc.gridx = 0;
        gbc.gridy = ++y;
        panel.add(new JLabel("Data / Plano:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtDatePlano = new JTextField();
        panel.add(txtDatePlano, gbc);

        // ======================
        // Duplicata
        // ======================        
        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Duplicata:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField txtDuplicata = new JTextField();
        panel.add(txtDuplicata, gbc);

        // reset
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        
        // ======================
        // Prog. Corte
        // ======================
        gbc.gridx = 0;
        gbc.gridy = ++y;
        panel.add(new JLabel("Data / Receb.:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField txtDataReceb = new JTextField();
        panel.add(txtDataReceb, gbc);

        // ======================
        // Prog. Corte
        // ======================
        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Prog. Corte:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField txtProgCorte = new JTextField();
        panel.add(txtProgCorte, gbc);

        // reset
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;

        
        txtObservation = new JTextArea(3, 20);
        txtObservation.setLineWrap(true);
        txtObservation.setWrapStyleWord(true);

        JScrollPane scrollObs = new JScrollPane(txtObservation);
        
	     // ======================
	     // Observações
	     // ======================
	     gbc.gridx = 0;
	     gbc.gridy = ++y;
	     panel.add(new JLabel("Observação:"), gbc);
	
	     gbc.gridx = 1;
	     gbc.gridwidth = 3;
	     gbc.fill = GridBagConstraints.BOTH;
	     gbc.weighty = 1;
	
	     panel.add(scrollObs, gbc);
	
	     // reset
	     gbc.gridwidth = 1;
	     gbc.weighty = 0;
	     gbc.fill = GridBagConstraints.HORIZONTAL;

        // ======================
        // Checkbox Duplicada
        // ======================
        gbc.gridx = 0;
        gbc.gridy = ++y;
        JCheckBox chkDuplicada = new JCheckBox("Duplicada");
        panel.add(chkDuplicada, gbc);

        return panel;
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

    public DefaultTableModel getTableModel() {
        return tableModel;
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

    public void clearFields() {
        txtOrder.setText("");
        txtDatePlano.setText("");
        txtShift.setText("");
        txtObservation.setText("");
    }
}
