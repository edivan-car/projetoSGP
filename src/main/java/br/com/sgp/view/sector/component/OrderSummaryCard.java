package br.com.sgp.view.sector.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import br.com.sgp.model.Order;
import br.com.sgp.view.util.AppColors;

public class OrderSummaryCard extends JPanel {

    private static final long serialVersionUID = 1L;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private JLabel lblLinha1; // Projeto / Linha / Programação
    private JLabel lblLinha2; // Corte térmico / Montagem / Solda-Pescoço
    private JTextArea txtObservation;

    public OrderSummaryCard() {
        setLayout(new BorderLayout(0, 4));
        setPreferredSize(new Dimension(860, 90));

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 10, 4, 10),
                BorderFactory.createMatteBorder(0, 3, 0, 0, AppColors.ACCENT)));

        add(createTextPanel(), BorderLayout.CENTER);
        add(createObsPanel(), BorderLayout.SOUTH);
    }

    private JPanel createTextPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 0));

        Font font = panel.getFont().deriveFont(12f);

        lblLinha1 = new JLabel(" ");
        lblLinha1.setFont(font);

        lblLinha2 = new JLabel(" ");
        lblLinha2.setFont(font);

        panel.add(lblLinha1);
        panel.add(javax.swing.Box.createVerticalStrut(4));
        panel.add(lblLinha2);

        return panel;
    }

    private JPanel createObsPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 2, 0));

        txtObservation = new JTextArea(1, 40);
        txtObservation.setEditable(false);
        txtObservation.setLineWrap(true);
        txtObservation.setWrapStyleWord(true);
        txtObservation.setBackground(AppColors.FIELD_BG);

        panel.add(new JLabel("Obs.:"), BorderLayout.WEST);
        panel.add(new JScrollPane(txtObservation), BorderLayout.CENTER);

        return panel;
    }

    public void setOrder(Order order) {
        lblLinha1.setText(String.format("Projeto: %s      Linha: %s      Programação: %s",
                valueOrEmpty(order.getProjeto()),
                valueOrEmpty(order.getLinhaMontagem()),
                valueOrEmpty(order.getProgramacaoMes())));

        lblLinha2.setText(String.format("Corte térmico: %s (Turno %s)      Montagem: %s (Turno %s)",
                formatDate(order.getDataCorte()), valueOrEmpty(order.getTurnoCorte()),
                formatDate(order.getDataMontagem()), valueOrEmpty(order.getTurnoMontagem())));

        txtObservation.setText(valueOrEmpty(order.getObservacao()));
    }

    public void clear() {
        lblLinha1.setText(" ");
        lblLinha2.setText(" ");
        txtObservation.setText("");
    }

    private String formatDate(java.util.Date date) {
        return date == null ? "—" : dateFormat.format(date);
    }

    private String valueOrEmpty(String value) {
        return (value != null && !value.isEmpty()) ? value : "—";
    }
}