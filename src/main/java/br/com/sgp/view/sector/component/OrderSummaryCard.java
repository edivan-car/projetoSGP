package br.com.sgp.view.sector.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import br.com.sgp.model.Order;

public class OrderSummaryCard extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final Color READ_ONLY_BACKGROUND = new Color(245, 245, 245);

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private static final String[] COLUMNS = {
        "", "Projeto", "Linha", "Programação", "Dt. Corte", "Dt. Montagem", "Dt. SoldaPesc"
    };

    private static final int ROW_DATES  = 0;
    private static final int ROW_SHIFTS = 1;

    private DefaultTableModel tableModel;
    private JTable table;
    private JTextArea txtObservation;

    public OrderSummaryCard() {
        setLayout(new BorderLayout(0, 4));
        setPreferredSize(new Dimension(860, 130));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 10, 0, 10),
                BorderFactory.createTitledBorder("Resumo do pedido")));

        add(createTable(), BorderLayout.CENTER);
        add(createObsPanel(), BorderLayout.SOUTH);
    }

    private JScrollPane createTable() {
        tableModel = new DefaultTableModel(COLUMNS, 2) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableModel.setValueAt("Data:",  ROW_DATES,  0);
        tableModel.setValueAt("Turno:", ROW_SHIFTS, 0);

        table = new JTable(tableModel);
        table.setBackground(READ_ONLY_BACKGROUND);
        table.setRowHeight(22);
        table.setFocusable(false);
        table.setRowSelectionAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);

        // larguras fixas por coluna
        table.getColumnModel().getColumn(0).setPreferredWidth(45);  // Rótulo
        table.getColumnModel().getColumn(1).setPreferredWidth(160); // Projeto
        table.getColumnModel().getColumn(2).setPreferredWidth(100); // Linha
        table.getColumnModel().getColumn(3).setPreferredWidth(80);  // Programação
        table.getColumnModel().getColumn(4).setPreferredWidth(90);  // Dt. Corte
        table.getColumnModel().getColumn(5).setPreferredWidth(90);  // Dt. Montagem
        table.getColumnModel().getColumn(6).setPreferredWidth(90);  // Dt. SoldaPesc
        
        // altura fixa para 2 linhas + cabeçalho, sem scrollbar  // <- incluir
        table.setPreferredScrollableViewportSize(new Dimension(860, 44));

        return new JScrollPane(table);
    }

    private JPanel createObsPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));

        txtObservation = new JTextArea(2, 40);
        txtObservation.setEditable(false);
        txtObservation.setLineWrap(true);
        txtObservation.setWrapStyleWord(true);
        txtObservation.setBackground(READ_ONLY_BACKGROUND);

        panel.add(new JLabel("Obs.:"), BorderLayout.WEST);
        panel.add(new JScrollPane(txtObservation), BorderLayout.CENTER);

        return panel;
    }

    public void setOrder(Order order) {
        tableModel.setValueAt(order.getProjeto(),                         ROW_DATES, 1);
        tableModel.setValueAt(order.getLinhaMontagem(),                   ROW_DATES, 2);
        tableModel.setValueAt(order.getProgramacaoMes(),                  ROW_DATES, 3);
        tableModel.setValueAt(formatDate(order.getDataCorte()),           ROW_DATES, 4);
        tableModel.setValueAt(formatDate(order.getDataMontagem()),        ROW_DATES, 5);
        tableModel.setValueAt(formatDate(order.getDataSoldaPescoco()),    ROW_DATES, 6);

        tableModel.setValueAt(valueOrEmpty(order.getTurnoCorte()),        ROW_SHIFTS, 4);
        tableModel.setValueAt(valueOrEmpty(order.getTurnoMontagem()),     ROW_SHIFTS, 5);
        tableModel.setValueAt(valueOrEmpty(order.getTurnoSoldaPescoco()), ROW_SHIFTS, 6);

        txtObservation.setText(valueOrEmpty(order.getObservacao()));
    }

    public void clear() {
        for (int col = 1; col < COLUMNS.length; col++) {
            tableModel.setValueAt("", ROW_DATES,  col);
            tableModel.setValueAt("", ROW_SHIFTS, col);
        }
        txtObservation.setText("");
    }

    private String formatDate(java.util.Date date) {
        return date == null ? "" : dateFormat.format(date);
    }

    private String valueOrEmpty(String value) {
        return value != null ? value : "";
    }
}