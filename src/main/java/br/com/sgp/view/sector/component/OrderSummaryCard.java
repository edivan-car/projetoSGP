package br.com.sgp.view.sector.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.sgp.model.Order;

public class OrderSummaryCard extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final Color READ_ONLY_BACKGROUND = new Color(245, 245, 245);

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private JTextField txtProject;
    private JTextField txtAssemblyLine;
    private JTextField txtSchedule;
    private JTextField txtDateCut;
    private JTextField txtDateAssembly;
    private JTextField txtDateWeld;
    private JTextField txtShiftCut;
    private JTextField txtShiftAssembly;
    private JTextField txtShiftWeld;
    private JTextArea txtObservation;

    public OrderSummaryCard() {
        setLayout(new BorderLayout(0, 2));
        setPreferredSize(new Dimension(860, 150));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 10, 0, 10),
                BorderFactory.createTitledBorder("Resumo do pedido")));

        add(createContent(), BorderLayout.CENTER);
    }

    private JPanel createContent() {
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        txtProject = readOnlyField(22);
        txtAssemblyLine = readOnlyField(10);
        txtSchedule = readOnlyField(10);
        row1.add(new JLabel("Projeto:"));
        row1.add(txtProject);
        row1.add(new JLabel("Linha:"));
        row1.add(txtAssemblyLine);
        row1.add(new JLabel("Programação:"));
        row1.add(txtSchedule);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        row2.add(fixedLabel("", 80));
        row2.add(fixedLabel("Corte", 105));
        row2.add(fixedLabel("Montagem", 105));
        row2.add(fixedLabel("SoldaPesc", 105));

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        txtDateCut = readOnlyField(10);
        txtDateAssembly = readOnlyField(10);
        txtDateWeld = readOnlyField(10);
        row3.add(fixedLabel("Data:", 80));
        row3.add(txtDateCut);
        row3.add(txtDateAssembly);
        row3.add(txtDateWeld);

        JPanel row4 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        txtShiftCut = readOnlyField(10);
        txtShiftAssembly = readOnlyField(10);
        txtShiftWeld = readOnlyField(10);
        row4.add(fixedLabel("Turno:", 80));
        row4.add(txtShiftCut);
        row4.add(txtShiftAssembly);
        row4.add(txtShiftWeld);

        JPanel row5 = new JPanel(new BorderLayout(5, 0));
        row5.setBorder(BorderFactory.createEmptyBorder(0, 8, 4, 8));
        txtObservation = new JTextArea(1, 40);
        txtObservation.setRows(1);
        txtObservation.setPreferredSize(new Dimension(100, 34));
        txtObservation.setEditable(false);
        txtObservation.setLineWrap(true);
        txtObservation.setWrapStyleWord(true);
        txtObservation.setBackground(READ_ONLY_BACKGROUND);
        row5.add(new JLabel("Obs:"), BorderLayout.WEST);
        row5.add(new JScrollPane(txtObservation), BorderLayout.CENTER);

        JPanel rows = new JPanel(new BorderLayout());
        JPanel topRows = new JPanel(new java.awt.GridLayout(4, 1, 0, 0));
        topRows.add(row1);
        topRows.add(row2);
        topRows.add(row3);
        topRows.add(row4);
        rows.add(topRows, BorderLayout.NORTH);
        rows.add(row5, BorderLayout.CENTER);

        return rows;
    }

    private JTextField readOnlyField(int columns) {
        JTextField field = new JTextField(columns);
        field.setEditable(false);
        field.setBackground(READ_ONLY_BACKGROUND);
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 22));
        return field;
    }

    private JLabel fixedLabel(String text, int width) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(width, 20));
        return label;
    }

    public void setOrder(Order order) {
        txtProject.setText(order.getProjeto());
        txtAssemblyLine.setText(order.getLinhaMontagem());
        txtSchedule.setText(order.getProgramacaoMes());
        txtDateCut.setText(formatDate(order.getDataCorte()));
        txtDateAssembly.setText(formatDate(order.getDataMontagem()));
        txtDateWeld.setText(formatDate(order.getDataSoldaPescoco()));
        txtShiftCut.setText(valueOrEmpty(order.getTurnoCorte()));
        txtShiftAssembly.setText(valueOrEmpty(order.getTurnoMontagem()));
        txtShiftWeld.setText(valueOrEmpty(order.getTurnoSoldaPescoco()));
        txtObservation.setText(valueOrEmpty(order.getObservacao()));
    }

    public void clear() {
        txtProject.setText("");
        txtAssemblyLine.setText("");
        txtSchedule.setText("");
        txtDateCut.setText("");
        txtDateAssembly.setText("");
        txtDateWeld.setText("");
        txtShiftCut.setText("");
        txtShiftAssembly.setText("");
        txtShiftWeld.setText("");
        txtObservation.setText("");
    }

    private String formatDate(java.util.Date date) {
        return date == null ? "" : dateFormat.format(date);
    }

    private String valueOrEmpty(String value) {
        return value != null ? value : "";
    }
}
