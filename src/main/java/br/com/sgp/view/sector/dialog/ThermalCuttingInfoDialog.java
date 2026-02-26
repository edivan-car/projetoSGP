package br.com.sgp.view.sector.dialog;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.text.*;

import br.com.sgp.view.util.GridBagHelper;

public class ThermalCuttingInfoDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private JTextField txtLinhaMontagem;
    private JTextField txtDataProgramacao;
    private JTextField txtDataRecebimento;

    private boolean confirmed = false;

    public ThermalCuttingInfoDialog(Window owner) {
        super(owner, "Informações da Programação", ModalityType.APPLICATION_MODAL);
        initComponents();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {

        setLayout(new GridBagLayout());
        GridBagHelper g = new GridBagHelper();
        int y = 0;

        // Linha montagem
        add(new JLabel("Linha de montagem:"), g.c(0, y));

        txtLinhaMontagem = new JTextField(25);
        ((AbstractDocument) txtLinhaMontagem.getDocument())
                .setDocumentFilter(new UppercaseDocumentFilter());
        add(txtLinhaMontagem, g.c(1, y));

        // Data programação
        y++;
        add(new JLabel("Data programação:"), g.c(0, y));

        txtDataProgramacao = new JTextField(10);
        ((AbstractDocument) txtDataProgramacao.getDocument())
                .setDocumentFilter(new DateDocumentFilter());
        add(txtDataProgramacao, g.c(1, y));

        // Data recebimento
        y++;
        add(new JLabel("Data recebimento:"), g.c(0, y));

        txtDataRecebimento = new JTextField(10);
        txtDataRecebimento.setText(LocalDate.now().format(DATE_FORMAT));
        ((AbstractDocument) txtDataRecebimento.getDocument())
                .setDocumentFilter(new DateDocumentFilter());
        add(txtDataRecebimento, g.c(1, y));

        // Botões
        y++;
        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Cancelar");

        btnOk.addActionListener(e -> {
            if (getDataProgramacao() == null) {
                JOptionPane.showMessageDialog(this,
                        "Data de programação inválida.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            confirmed = true;
            dispose();
        });

        btnCancel.addActionListener(e -> dispose());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnCancel);
        buttons.add(btnOk);

        GridBagConstraints gbcButtons = g.c(0, y);
        g.span(gbcButtons, 2);
        add(buttons, gbcButtons);
    }

    // =======================
    // DOCUMENT FILTERS
    // =======================

    private static class UppercaseDocumentFilter extends DocumentFilter {

        @Override
        public void replace(FilterBypass fb, int offset, int length,
                            String text, AttributeSet attrs)
                throws BadLocationException {

            if (text != null) {
                text = text.toUpperCase();
            }
            super.replace(fb, offset, length, text, attrs);
        }
    }

    private static class DateDocumentFilter extends DocumentFilter {

        @Override
        public void replace(FilterBypass fb, int offset, int length,
                            String text, AttributeSet attrs)
                throws BadLocationException {

            if (text == null) return;

            text = text.replaceAll("[^0-9]", "");

            String current = fb.getDocument()
                    .getText(0, fb.getDocument().getLength());

            StringBuilder sb = new StringBuilder(current);
            sb.replace(offset, offset + length, text);

            String digits = sb.toString().replaceAll("[^0-9]", "");

            if (digits.length() > 8) {
                digits = digits.substring(0, 8);
            }

            StringBuilder formatted = new StringBuilder();

            for (int i = 0; i < digits.length(); i++) {
                formatted.append(digits.charAt(i));
                if ((i == 1 || i == 3) && i < digits.length() - 1) {
                    formatted.append("/");
                }
            }

            fb.replace(0, fb.getDocument().getLength(),
                    formatted.toString(), attrs);
        }
    }

    // =======================
    // GETTERS
    // =======================

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getLinhaMontagem() {
        return txtLinhaMontagem.getText();
    }

    public String getDataProgramacao() {
    	return txtDataProgramacao.getText();
    }

    public LocalDate getDataRecebimento() {
        try {
            return LocalDate.parse(
                    txtDataRecebimento.getText(),
                    DATE_FORMAT
            );
        } catch (Exception e) {
            return null;
        }
    }
}