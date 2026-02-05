package br.com.sgp.view.sector.forms;

import br.com.sgp.view.util.GridBagHelper;

import javax.swing.*;
import java.awt.*;

public class ThermalCuttingForm extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField txtLinhaMontagem;
	private JTextField txtDataPlano;
	private JTextField txtDuplicata;
	private JTextField txtDataRecebimento;
	private JTextField txtProgCorte;
	private JTextArea txtObservation;
	private JCheckBox chkDuplicada;

	public ThermalCuttingForm() {
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(760, 210));
		setMinimumSize(new Dimension(760, 210));
		setMaximumSize(new Dimension(760, 210));

		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		initComponents();
	}

	private void initComponents() {

		GridBagHelper g = new GridBagHelper();
	    int y = 0;

	    // Linha Montagem
	    add(new JLabel("Linha Mont.:"), g.c(0, y));

	    txtLinhaMontagem = new JTextField();
	    GridBagConstraints gbcLinha = g.c(1, y);
	    g.span(gbcLinha, 3);
	    g.weight(gbcLinha, 1, 0);
	    g.fill(gbcLinha, GridBagConstraints.HORIZONTAL);
	    add(txtLinhaMontagem, gbcLinha);

	    // Data / Plano + Duplicata
	    y++;
	    add(new JLabel("Data / Plano:"), g.c(0, y));

	    txtDataPlano = new JTextField();
	    GridBagConstraints gbcDataPlano = g.c(1, y);
	    g.weight(gbcDataPlano, 0.5, 0);
	    g.fill(gbcDataPlano, GridBagConstraints.HORIZONTAL);
	    add(txtDataPlano, gbcDataPlano);

	    add(new JLabel("Duplicata:"), g.c(2, y));

	    txtDuplicata = new JTextField();
	    GridBagConstraints gbcDuplicata = g.c(3, y);
	    g.weight(gbcDuplicata, 0.5, 0);
	    g.fill(gbcDuplicata, GridBagConstraints.HORIZONTAL);
	    add(txtDuplicata, gbcDuplicata);

	    // Data Receb. + Prog. Corte
	    y++;
	    add(new JLabel("Data / Receb.:"), g.c(0, y));

	    txtDataRecebimento = new JTextField();
	    GridBagConstraints gbcDataRec = g.c(1, y);
	    g.weight(gbcDataRec, 0.5, 0);
	    g.fill(gbcDataRec, GridBagConstraints.HORIZONTAL);
	    add(txtDataRecebimento, gbcDataRec);

	    add(new JLabel("Prog. Corte:"), g.c(2, y));

	    txtProgCorte = new JTextField();
	    GridBagConstraints gbcProgCorte = g.c(3, y);
	    g.weight(gbcProgCorte, 0.5, 0);
	    g.fill(gbcProgCorte, GridBagConstraints.HORIZONTAL);
	    add(txtProgCorte, gbcProgCorte);

	    // Observações
	    y++;
	    add(new JLabel("Observação:"), g.c(0, y));

	    txtObservation = new JTextArea(3, 20);
	    txtObservation.setLineWrap(true);
	    txtObservation.setWrapStyleWord(true);

	    JScrollPane scrollObs = new JScrollPane(txtObservation);

	    GridBagConstraints gbcObs = g.c(1, y);
	    g.span(gbcObs, 3);
	    g.weight(gbcObs, 1, 1);
	    g.fill(gbcObs, GridBagConstraints.BOTH);
	    add(scrollObs, gbcObs);

	    // Checkbox
	    y++;
	    chkDuplicada = new JCheckBox("Duplicada");
	    add(chkDuplicada, g.c(0, y));
	}
	
	public void setLinhaMontagem(String value) {
	    txtLinhaMontagem.setText(value);
	}

	public void setProgCorte(String value) {
	    txtProgCorte.setText(value);
	}

}
