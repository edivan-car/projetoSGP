package br.com.sgp.view.sector.form;

import java.awt.*;
import javax.swing.*;

import br.com.sgp.view.sector.SectorForm;

public class PlasmaCuttingForm extends JPanel implements SectorForm {
	private static final long serialVersionUID = 1L;
	private static final Color RACK_SELECTED_BG = new Color(230, 241, 251);
	private static final Color RACK_SELECTED_FG = new Color(24, 95, 165);
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

	// 🔥 Agora usando ARRAY
	private JButton[] rackButtons = new JButton[12];
	private JButton btnFixedSupport = new JButton("Suporte Fixo");
	private JButton btnMobileSupport = new JButton("Suporte Móvel");

	public PlasmaCuttingForm() {
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(760, 190));
//		setPreferredSize(new Dimension(760, 210));
//		setMinimumSize(new Dimension(760, 210));
//		setMaximumSize(new Dimension(760, 210));
//
//		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)),
//				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		initComponents();
	}

	private void initComponents() {

		JPanel leftPanel = criarPainelEsquerdo();
		JPanel rightPanel = criarPainelRacks();

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(3, 5, 3, 5);

		// LEFT
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(leftPanel, gbc);

		// RIGHT
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

		txtDate = new JTextField(10);
		txtShift = new JTextField(5);
		txtRack = new JTextField(10);
		txtRack.setEditable(false);
		txtRack.setBackground(new Color(245, 245, 245));
		txtObservation = new JTextArea(2, 10);
		txtObservation.setFocusTraversalKeysEnabled(true);

		JScrollPane scroll = new JScrollPane(txtObservation);

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(new JLabel("Data do Corte:"), gbc);

		gbc.gridx = 1;
		panel.add(txtDate, gbc);

		gbc.gridx = 2;
		panel.add(new JLabel("Turno:"), gbc);

		gbc.gridx = 3;
		panel.add(txtShift, gbc);

		gbc.gridx = 4;
		panel.add(new JLabel("Rack:"), gbc);

		gbc.gridx = 5;
		panel.add(txtRack, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.NORTH;
		panel.add(new JLabel("Observação:"), gbc);

		gbc.gridx = 1;
		gbc.gridwidth = 5;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;
		panel.add(scroll, gbc);

		// ===============================
		// LINHA INFERIOR (CHECK + BOTÕES)
		// ===============================

		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.gridwidth = 6;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;

		JPanel bottomPanel = new JPanel(new BorderLayout());

		// 🔹 Checkbox
		bottomPanel.add(chkDuplicada, BorderLayout.WEST);

		// 🔹 Painel de botões centralizados
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

		buttonsPanel.add(btnGenerate);
		buttonsPanel.add(chkPreviousDay);
		buttonsPanel.add(btnCleanRack);
		buttonsPanel.add(btnRegister);

		bottomPanel.add(buttonsPanel, BorderLayout.CENTER);

		panel.add(bottomPanel, gbc);

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

		// 🔹 SUPORTE FIXO
		linha++;
		gbc.gridx = 0;
		gbc.gridy = linha;
		gbc.gridwidth = 3;
		panel.add(btnFixedSupport, gbc);

		// 🔹 SUPORTE MÓVEL
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
}
