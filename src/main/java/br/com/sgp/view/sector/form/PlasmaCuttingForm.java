package br.com.sgp.view.sector.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

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
import javax.swing.UIManager;

import br.com.sgp.view.sector.SectorForm;
import br.com.sgp.view.util.AppColors;

public class PlasmaCuttingForm extends JPanel implements SectorForm {
	private static final long serialVersionUID = 1L;
	private static final Color RACK_SELECTED_BG = new Color(220, 231, 253); // tom claro do AppColors.ACCENT
	private static final Color RACK_SELECTED_FG = AppColors.ACCENT;
	private static final Color RACK_DEFAULT_BG = UIManager.getColor("Button.background");
	private static final Color RACK_DEFAULT_FG = UIManager.getColor("Button.foreground");

	private JTextField txtDate;
	private JTextField txtShift;
	private JTextField txtRack;
	private JTextArea txtObservation;
	private JCheckBox chkDuplicada = new JCheckBox("Duplicada");

	private JButton btnGenerate = new JButton("Gerar");
	private JButton btnRegister = new JButton("Cadastrar");
	private JButton btnCleanRack = new JButton("Limpar");
	private JCheckBox chkPreviousDay = new JCheckBox("Dia anterior");

	private JButton[] rackButtons = new JButton[12];
	private JButton btnFixedSupport = new JButton("Suporte Fixo");
	private JButton btnMobileSupport = new JButton("Suporte Móvel");

	public PlasmaCuttingForm() {
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(600, 250));
		setMinimumSize(new Dimension(600, 250));

		initComponents();
	}

	private void initComponents() {

		JPanel leftPanel = criarPainelEsquerdo();
		JPanel rightPanel = criarPainelRacks();

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(3, 5, 3, 5);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(leftPanel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.VERTICAL;
		add(rightPanel, gbc);
	}

	private JPanel criarPainelEsquerdo() {

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(3, 5, 3, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		txtDate = new JTextField();
		txtShift = new JTextField();
		txtRack = new JTextField();
		txtRack.setEditable(false);
		txtRack.setBackground(AppColors.FIELD_BG);

		// Data do Corte | Turno | Rack (lado a lado)
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.4;
		panel.add(campo("Data do Corte:", txtDate), gbc);

		gbc.gridx = 1;
		gbc.weightx = 0.3;
		panel.add(campo("Turno:", txtShift), gbc);

		gbc.gridx = 2;
		gbc.weightx = 0.3;
		panel.add(campo("Rack:", txtRack), gbc);

		// Observação (linha inteira, expande verticalmente)
		txtObservation = new JTextArea(2, 10);
		txtObservation.setLineWrap(true);
		txtObservation.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(txtObservation);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		panel.add(campo("Observação:", scroll), gbc);

		// Checkboxes + botões
		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.gridwidth = 3;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JPanel bottomPanel = new JPanel(new BorderLayout());

		JPanel checkboxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		checkboxPanel.add(chkDuplicada);
		checkboxPanel.add(chkPreviousDay);
		bottomPanel.add(checkboxPanel, BorderLayout.WEST);

		AppColors.style(btnGenerate, AppColors.ACCENT);
		AppColors.style(btnCleanRack, AppColors.WARNING);
		AppColors.style(btnRegister, AppColors.SUCCESS);
		bindEnterKey(btnRegister);

		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
		buttonsPanel.add(btnGenerate);
		buttonsPanel.add(btnCleanRack);
		buttonsPanel.add(btnRegister);
		bottomPanel.add(buttonsPanel, BorderLayout.CENTER);

		panel.add(bottomPanel, gbc);

		return panel;
	}

	/** Monta um bloco com o rótulo em negrito acima do campo (mesmo padrão do Corte Térmico). */
	private JPanel campo(String rotulo, JComponent field) {
		JPanel panel = new JPanel(new BorderLayout(0, 3));

		JLabel lbl = new JLabel(rotulo);
		lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 11f));
		lbl.setForeground(new Color(90, 90, 90));

		panel.add(lbl, BorderLayout.NORTH);
		panel.add(field, BorderLayout.CENTER);
		return panel;
	}

	private JPanel criarPainelRacks() {

		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Unidade de Transporte"),
				BorderFactory.createEmptyBorder(3, 5, 3, 5)));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;

		int linha = 0;
		int coluna = 0;

		for (int i = 0; i < rackButtons.length; i++) {

			rackButtons[i] = new JButton(String.format("Rack %02d", i + 1));

			rackButtons[i].setMargin(new Insets(2, 4, 2, 4));
			rackButtons[i].setFont(new Font("Arial", Font.BOLD, 11));
			rackButtons[i].setPreferredSize(new Dimension(78, 23));
			configureRackButton(rackButtons[i]);

			gbc.gridx = coluna;
			gbc.gridy = linha;
			panel.add(rackButtons[i], gbc);

			coluna++;
			if (coluna == 3) {
				coluna = 0;
				linha++;
			}
		}

		linha++;
		gbc.gridx = 0;
		gbc.gridy = linha;
		gbc.gridwidth = 3;
		panel.add(btnFixedSupport, gbc);

		linha++;
		gbc.gridy = linha;
		panel.add(btnMobileSupport, gbc);

		return panel;
	}

	private void configureRackButton(JButton button) {
		button.setFocusPainted(false);
		button.setOpaque(true);
		button.setBackground(RACK_DEFAULT_BG);
		button.setForeground(RACK_DEFAULT_FG);
	}

	public void selectRackButton(JButton selectedButton) {
		for (JButton button : rackButtons) {
			if (button != null) {
				setRackButtonSelected(button, button == selectedButton);
			}
		}
		setRackButtonSelected(btnFixedSupport, btnFixedSupport == selectedButton);
		setRackButtonSelected(btnMobileSupport, btnMobileSupport == selectedButton);
	}

	private void setRackButtonSelected(JButton button, boolean selected) {
		button.setBackground(selected ? RACK_SELECTED_BG : RACK_DEFAULT_BG);
		button.setForeground(selected ? RACK_SELECTED_FG : RACK_DEFAULT_FG);
		button.setFont(button.getFont().deriveFont(selected ? Font.BOLD : Font.PLAIN));
	}

	// ======================
	// GETTERS NECESSÁRIOS
	// ======================

	public JTextField getTxtRack() {
		return txtRack;
	}

	public JButton[] getRackButtons() {
		return rackButtons;
	}

	public JButton getBtnFixedSupport() {
		return btnFixedSupport;
	}

	public JButton getBtnMobileSupport() {
		return btnMobileSupport;
	}

	public JButton getBtnGenerate() {
		return btnGenerate;
	}

	public JTextField getTxtDate() {
		return txtDate;
	}

	public JTextArea getTxtObservation() {
		return txtObservation;
	}

	public JCheckBox getChkPreviousDay() {
		return chkPreviousDay;
	}

	public JCheckBox getChkDuplicada() {
		return chkDuplicada;
	}

	public JButton getBtnRegister() {
		return btnRegister;
	}

	public JButton getBtnCleanRack() {
		return btnCleanRack;
	}

	public void clearRackField() {
		txtDate.setText("");
		txtShift.setText("");
		txtRack.setText("");
		txtObservation.setText("");
		chkDuplicada.setSelected(false);
		chkPreviousDay.setSelected(false);
		clearRackSelection();
	}

	public void addCleanRackListener(java.awt.event.ActionListener listener) {
		btnCleanRack.addActionListener(listener);
	}

	@Override
	public void clearForm() {
		txtDate.setText("");
		txtShift.setText("");
		txtRack.setText("");
		txtObservation.setText("");
		chkDuplicada.setSelected(false);
		chkPreviousDay.setSelected(false);
		clearRackSelection();
	}

	public JTextField getTxtShift() {
		return txtShift;
	}

	private void clearRackSelection() {
		for (JButton button : rackButtons) {
			if (button != null) {
				setRackButtonSelected(button, false);
			}
		}
		setRackButtonSelected(btnFixedSupport, false);
		setRackButtonSelected(btnMobileSupport, false);
	}

	public void setFieldsEditable(boolean editable) {
		txtDate.setEditable(editable);
		txtShift.setEditable(editable);
		txtObservation.setEditable(editable);
		chkPreviousDay.setEnabled(editable);
		btnGenerate.setEnabled(editable);
		for (JButton btn : getRackButtons()) btn.setEnabled(editable);
		btnFixedSupport.setEnabled(editable);
		btnMobileSupport.setEnabled(editable);
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
}