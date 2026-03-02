package br.com.sgp.view.sector.form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.sgp.view.sector.SectorForm;
import br.com.sgp.view.util.GridBagHelper;

public class ThermalCuttingForm extends JPanel implements SectorForm{

	private static final long serialVersionUID = 1L;

	private JTextField txtLinhaMontagem;
	private JTextField txtDataPlano;
	private JTextField txtDuplicada;
	private JTextField txtDataRecebimento;
	private JTextField txtProgCorte;
	private JTextArea txtObservation;
	private JCheckBox chkDuplicada;
	private JButton btnInfo;
	private JButton btnReg;
	private JButton btnClean;

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
		add(new JLabel("Linha de Montagem:"), g.c(0, y));

		txtLinhaMontagem = new JTextField();
		GridBagConstraints gbcLinha = g.c(1, y);
		g.span(gbcLinha, 3);
		g.weight(gbcLinha, 1, 0);
		g.fill(gbcLinha, GridBagConstraints.HORIZONTAL);
		add(txtLinhaMontagem, gbcLinha);

		// Data / Plano + Duplicata
		y++;
		add(new JLabel("Data de programação:"), g.c(0, y));

		txtDataPlano = new JTextField();
		GridBagConstraints gbcDataPlano = g.c(1, y);
		g.weight(gbcDataPlano, 0.5, 0);
		g.fill(gbcDataPlano, GridBagConstraints.HORIZONTAL);
		add(txtDataPlano, gbcDataPlano);

		add(new JLabel("Duplicada:"), g.c(2, y));

		txtDuplicada = new JTextField();
		GridBagConstraints gbcDuplicata = g.c(3, y);
		g.weight(gbcDuplicata, 0.5, 0);
		g.fill(gbcDuplicata, GridBagConstraints.HORIZONTAL);
		add(txtDuplicada, gbcDuplicata);

		// Data Receb. + Prog. Corte
		y++;
		add(new JLabel("Data de recebimento:"), g.c(0, y));

		txtDataRecebimento = new JTextField();
		GridBagConstraints gbcDataRec = g.c(1, y);
		g.weight(gbcDataRec, 0.5, 0);
		g.fill(gbcDataRec, GridBagConstraints.HORIZONTAL);
		add(txtDataRecebimento, gbcDataRec);

		add(new JLabel("Ref. de corte:"), g.c(2, y));

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

		// Checkbox + botão INFO
		y++;
		chkDuplicada = new JCheckBox("Duplicada");
		add(chkDuplicada, g.c(0, y));

		btnInfo = new JButton("INFO");
		//btnInfo.setPreferredSize(new Dimension(20, 25));
		btnInfo.setFocusPainted(false);
		
		GridBagConstraints gbcInfo = g.c(3, y);
		gbcInfo.anchor = GridBagConstraints.WEST;
		gbcInfo.insets = new Insets(5, 10, 5, 10);
		gbcInfo.fill = GridBagConstraints.NONE;
		add(btnInfo, gbcInfo);
		
		
		btnReg = new JButton("REG");
		btnReg.setFocusable(false);
		
		GridBagConstraints gbcReg = g.c(3, y);
		gbcReg.anchor = GridBagConstraints.CENTER;
		gbcReg.insets = new Insets(5, 10, 5, 10);
		gbcReg.fill = GridBagConstraints.NONE;
		add(btnReg, gbcReg);
		
		btnClean = new JButton("CLEAN");
		btnClean.setFocusable(false);
		
		GridBagConstraints gbcClean = g.c(3, y);
		gbcClean.anchor = GridBagConstraints.EAST;
		gbcClean.insets = new Insets(5, 10, 5, 10);
		gbcClean.fill = GridBagConstraints.NONE;
		add(btnClean, gbcClean);

	}

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public JTextField getTxtLinhaMontagem() {
		return txtLinhaMontagem;
	}

	public JTextField getTxtDataPlano() {
		return txtDataPlano;
	}

	public JTextField getTxtDuplicada() {
		return txtDuplicada;
	}

	public JTextField getTxtDataRecebimento() {
		return txtDataRecebimento;
	}

	public JTextField getTxtProgCorte() {
		return txtProgCorte;
	}

	public JTextArea getTxtObservation() {
		return txtObservation;
	}

	public JCheckBox getChkDuplicada() {
		return chkDuplicada;
	}

	public void setLinhaMontagem(String value) {
		txtLinhaMontagem.setText(value);
	}

	public void setTextDataPlano(LocalDate value) {
		if (value != null) {
			txtDataPlano.setText(value.format(formatter));
		} else {
			txtDataPlano.setText("");
		}
	}

	public void setTextDataRecebimento(LocalDate value) {
		if (value != null) {
			txtDataRecebimento.setText(value.format(formatter));
		} else {
			txtDataRecebimento.setText("");
		}
	}

	public void setProgCorte(String value) {
		txtProgCorte.setText(value);
	}

	public JButton getBtnInfo() {
		return btnInfo;
	}
	
	public JButton getBtnReg() {
		return btnReg;
	}
	
	public JButton getBtnClean() {
		return btnClean;
	}

	@Override
	public void clearForm() {
		txtLinhaMontagem.setText("");
		txtDataPlano.setText("");
		txtDuplicada.setText("");
		txtDataRecebimento.setText("");
		txtProgCorte.setText("");
		txtObservation.setText("");
		chkDuplicada.setSelected(false);;
	}

}
