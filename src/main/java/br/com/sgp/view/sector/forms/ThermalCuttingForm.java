package br.com.sgp.view.sector.forms;

import javax.swing.*;
import java.awt.*;

public class ThermalCuttingForm extends JPanel {
    private static final long serialVersionUID = 1L;

    // ======================
    // Campos
    // ======================
    private JTextField txtLinhaMontagem;
    private JTextField txtDataPlano;
    private JTextField txtDuplicata;
    private JTextField txtDataRecebimento;
    private JTextField txtProgCorte;
    private JTextArea txtObservation;
    private JCheckBox chkDuplicada;

    // ======================
    // Construtor
    // ======================
    public ThermalCuttingForm() {
        setLayout(new GridBagLayout());
        initComponents();
    }

    // ======================
    // Helpers de Layout
    // ======================
    private void addLabel(String text, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel(text), gbc);
    }

    private void addField(
            JComponent component,
            GridBagConstraints gbc,
            int x,
            int y,
            double weightX
    ) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = weightX;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(component, gbc);
    }

    // ======================
    // Inicialização
    // ======================
    private void initComponents() {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        int y = 0;

        // ======================
        // Linha Montagem
        // ======================
        addLabel("Linha Mont.:", gbc, 0, y);

        txtLinhaMontagem = new JTextField();
        addField(txtLinhaMontagem, gbc, 1, y, 1.0);

        // ======================
        // Data / Plano + Duplicata
        // ======================
        y++;

        addLabel("Data / Plano:", gbc, 0, y);
        txtDataPlano = new JTextField();
        addField(txtDataPlano, gbc, 1, y, 0.5);

        addLabel("Duplicata:", gbc, 2, y);
        txtDuplicata = new JTextField();
        addField(txtDuplicata, gbc, 3, y, 0.5);

        // ======================
        // Data / Recebimento + Prog. Corte
        // ======================
        y++;

        addLabel("Data / Receb.:", gbc, 0, y);
        txtDataRecebimento = new JTextField();
        addField(txtDataRecebimento, gbc, 1, y, 0.5);

        addLabel("Prog. Corte:", gbc, 2, y);
        txtProgCorte = new JTextField();
        addField(txtProgCorte, gbc, 3, y, 0.5);

        // ======================
        // Observações
        // ======================
        y++;

        addLabel("Observação:", gbc, 0, y);

        txtObservation = new JTextArea(3, 20);
        txtObservation.setLineWrap(true);
        txtObservation.setWrapStyleWord(true);

        JScrollPane scrollObs = new JScrollPane(txtObservation);

        gbc.gridx = 1;
        gbc.gridy = y;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollObs, gbc);

        // reset
        gbc.gridwidth = 1;
        gbc.weighty = 0;

        // ======================
        // Checkbox
        // ======================
        y++;

        chkDuplicada = new JCheckBox("Duplicada");
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.NONE;
        add(chkDuplicada, gbc);
    }
}
