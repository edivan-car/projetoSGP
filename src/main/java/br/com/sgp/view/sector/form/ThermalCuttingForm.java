package br.com.sgp.view.sector.form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
	private JTextField txtDataRecebimento;
	private JTextField txtProgCorte;
	private JTextArea txtObservation;
	private JCheckBox chkDuplicada;
	private JButton btnInfo;
	private JButton btnReg;
	private JButton btnClean;
	private JButton btnCleanMemory = new JButton("Limpar Info");

	public ThermalCuttingForm() {
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(760, 175));
		setMinimumSize(new Dimension(760, 175));
		setMaximumSize(new Dimension(760, 175));

		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)),
				BorderFactory.createEmptyBorder(6, 10, 6, 10)));

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
		g.span(gbcDataPlano, 3);
		g.weight(gbcDataPlano, 1, 0);
		g.fill(gbcDataPlano, GridBagConstraints.HORIZONTAL);
		add(txtDataPlano, gbcDataPlano);

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

		txtObservation = new JTextArea(2, 20);
		txtObservation.setLineWrap(true);
		txtObservation.setWrapStyleWord(true);

		JScrollPane scrollObs = new JScrollPane(txtObservation);

		GridBagConstraints gbcObs = g.c(1, y);
		g.span(gbcObs, 3);
		g.weight(gbcObs, 1, 1);
		g.fill(gbcObs, GridBagConstraints.BOTH);
		add(scrollObs, gbcObs);

		// Checkbox + botões de apoio
		y++;
		chkDuplicada = new JCheckBox("Duplicada");
		add(chkDuplicada, g.c(0, y));

		btnInfo        = new JButton("Info");
		btnCleanMemory = new JButton("Limpar Info");
		btnReg         = new JButton("Registrar");
		btnClean       = new JButton("Limpar");

		btnInfo.setMargin(new Insets(2, 12, 2, 12));
		btnInfo.setFocusPainted(false);
		btnCleanMemory.setMargin(new Insets(2, 12, 2, 12));
		btnReg.setMargin(new Insets(2, 12, 2, 12));
		btnReg.setFocusable(true);   // <- modificar: precisa receber foco para ENTER funcionar
		btnClean.setMargin(new Insets(2, 12, 2, 12));

		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
		btnPanel.add(btnInfo);
		btnPanel.add(btnCleanMemory);
		btnPanel.add(btnReg);
		btnPanel.add(btnClean);

		GridBagConstraints gbcBtns = g.c(1, y);
		g.span(gbcBtns, 3);
		g.weight(gbcBtns, 1, 0);
		g.fill(gbcBtns, GridBagConstraints.HORIZONTAL);
		add(btnPanel, gbcBtns);

	}

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public JTextField getTxtLinhaMontagem() {
		return txtLinhaMontagem;
	}

	public JTextField getTxtDataPlano() {
		return txtDataPlano;
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
	
	public JButton getBtnCleanMemory() { return btnCleanMemory; }
	
	public void setFieldsEditable(boolean editable) {
	    txtLinhaMontagem.setEditable(editable);
	    txtDataPlano.setEditable(editable);
	    txtDataRecebimento.setEditable(editable);
	    txtProgCorte.setEditable(editable);
	    txtObservation.setEditable(editable);
	    chkDuplicada.setEnabled(editable);
	    btnInfo.setEnabled(editable);
	    btnCleanMemory.setEnabled(editable);
	}

	@Override
	public void clearForm() {
		txtLinhaMontagem.setText("");
		txtDataPlano.setText("");
		txtDataRecebimento.setText("");
		txtProgCorte.setText("");
		txtObservation.setText("");
		chkDuplicada.setSelected(false);
	}

}
