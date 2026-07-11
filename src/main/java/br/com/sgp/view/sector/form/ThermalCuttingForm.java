package br.com.sgp.view.sector.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import br.com.sgp.view.sector.SectorForm;
import br.com.sgp.view.util.AppColors;
import br.com.sgp.view.util.GridBagHelper;

public class ThermalCuttingForm extends JPanel implements SectorForm {

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
	private JButton btnCleanMemory;

	public ThermalCuttingForm() {
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(600, 250));
		setMinimumSize(new Dimension(600, 250));

		setBorder(BorderFactory.createEmptyBorder(8, 4, 4, 4));

		initComponents();
	}

	private void initComponents() {

		GridBagHelper g = new GridBagHelper();
		int y = 0;

		// Linha de montagem | Ref. de corte (lado a lado)
		txtLinhaMontagem = new JTextField();
		GridBagConstraints gbcLinha = g.c(0, y);
		g.weight(gbcLinha, 0.5, 0);
		add(campo("Linha de montagem:", txtLinhaMontagem), gbcLinha);

		txtProgCorte = new JTextField();
		GridBagConstraints gbcProgCorte = g.c(1, y);
		g.weight(gbcProgCorte, 0.5, 0);
		add(campo("Ref. de corte:", txtProgCorte), gbcProgCorte);

		// Data programação | Data recebimento (lado a lado)
		y++;
		txtDataPlano = new JTextField();
		GridBagConstraints gbcDataPlano = g.c(0, y);
		g.weight(gbcDataPlano, 0.5, 0);
		add(campo("Data de programação:", txtDataPlano), gbcDataPlano);

		txtDataRecebimento = new JTextField();
		GridBagConstraints gbcDataRec = g.c(1, y);
		g.weight(gbcDataRec, 0.5, 0);
		add(campo("Data de recebimento:", txtDataRecebimento), gbcDataRec);

		// Observação (linha inteira, expande verticalmente)
		y++;
		txtObservation = new JTextArea(2, 20);
		txtObservation.setLineWrap(true);
		txtObservation.setWrapStyleWord(true);
		JScrollPane scrollObs = new JScrollPane(txtObservation);

		GridBagConstraints gbcObs = g.c(0, y);
		g.span(gbcObs, 2);
		g.weight(gbcObs, 1, 1);
		g.fill(gbcObs, GridBagConstraints.BOTH);
		add(campo("Observação:", scrollObs), gbcObs);

		// Checkbox + botões
		y++;
		chkDuplicada = new JCheckBox("Duplicada");

		btnInfo        = new JButton("Info");
		btnCleanMemory = new JButton("Limpar Info");
		btnReg         = new JButton("Registrar");
		btnClean       = new JButton("Limpar");

		AppColors.style(btnInfo, AppColors.ACCENT);
		AppColors.style(btnCleanMemory, AppColors.NEUTRAL);
		AppColors.style(btnReg, AppColors.SUCCESS);
		AppColors.style(btnClean, AppColors.WARNING);

		btnReg.setFocusable(true);
		bindEnterKey(btnReg);

		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(chkDuplicada, BorderLayout.WEST);

		JPanel btnPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 0));
		btnPanel.add(btnInfo);
		btnPanel.add(btnCleanMemory);
		btnPanel.add(btnReg);
		btnPanel.add(btnClean);
		bottomPanel.add(btnPanel, BorderLayout.CENTER);

		GridBagConstraints gbcBtns = g.c(0, y);
		g.span(gbcBtns, 2);
		g.weight(gbcBtns, 1, 0);
		add(bottomPanel, gbcBtns);
	}

	/** Monta um bloco com o rótulo em negrito acima do campo. */
	private JPanel campo(String rotulo, JComponent field) {
		JPanel panel = new JPanel(new BorderLayout(0, 3));

		JLabel lbl = new JLabel(rotulo);
		lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 11f));
		lbl.setForeground(new Color(90, 90, 90));

		panel.add(lbl, BorderLayout.NORTH);
		panel.add(field, BorderLayout.CENTER);
		return panel;
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
		txtDataPlano.setText(value != null ? value.format(formatter) : "");
	}

	public void setTextDataRecebimento(LocalDate value) {
		txtDataRecebimento.setText(value != null ? value.format(formatter) : "");
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

	public JButton getBtnCleanMemory() {
		return btnCleanMemory;
	}

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

	private void bindEnterKey(JButton button) {
		button.getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, 0), "press");
		button.getActionMap().put("press", new javax.swing.AbstractAction() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				button.doClick();
			}
		});
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