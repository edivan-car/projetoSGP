package br.com.sgp.view.sector;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.sgp.view.util.GridBagHelper;

public class DefaultSectorView extends JInternalFrame {
	private static final long serialVersionUID = 1L;

	private JLabel lblPedido;
	private JTextField txtSearch;
	private JButton btnSearch;
	private JTextField txtSearchProject;
	private JTextField txtSearchAssemblyLine;
	private JTextField txtSearchMonthlySchedule;
	private JTextField txtSearchDateThermalCutting;
	private JTextField txtSearchThermalCuttingShift;
	private JTextField txtSearchDateBeamAssembly;
	private JTextField txtSearchBeamAssemblyShift;
	private JTextField txtSearchDateNeckWelding;
	private JTextField txtSearchNeckWeldingShift;
	private JTextArea txtSearchObservation;

	private JPanel searchContainer;
	private JPanel formContainer;

	private JPanel currentForm;

	private JButton btnCreate;
	private JButton btnEdit;
	private JButton btnClear;

	public DefaultSectorView(String title) {
		super(title, false, true, false, false); // super(title, resizable, closable, maximizable, iconifiable);

	    setLayout(new BorderLayout());

	    add(createSearchPanel(), BorderLayout.NORTH);
	    add(createCenterPanel(), BorderLayout.CENTER);
	    add(createButtonPanel(), BorderLayout.SOUTH);

	    setSize(900, 600);

	    initComponents();
	}

	private void initComponents() {
		
		searchContainer.setLayout(new GridBagLayout());

		GridBagHelper g = new GridBagHelper();
		int y = 0;

		// PROJETO ================================
		searchContainer.add(new JLabel("Projeto:"), g.c(0, y));

		txtSearchProject = new JTextField();
		GridBagConstraints gbcLinha = g.c(1, y);
		g.span(gbcLinha, 3);
		g.weight(gbcLinha, 1, 0);
		g.fill(gbcLinha, GridBagConstraints.HORIZONTAL);
		searchContainer.add(txtSearchProject, gbcLinha);
		
		// LINHA MONTAGEM =========================
		y++;
		searchContainer.add(new JLabel("Linha de Montagem:"), g.c(0, y));

		txtSearchAssemblyLine = new JTextField();
		gbcLinha = g.c(1, y);
		g.weight(gbcLinha, 1, 0);
		g.fill(gbcLinha, GridBagConstraints.HORIZONTAL);
		searchContainer.add(txtSearchAssemblyLine, gbcLinha);
		
		// PROGRAMAÇÃO DO MÊS =====================
		searchContainer.add(new JLabel("Programação:"), g.c(2, y));

		txtSearchMonthlySchedule = new JTextField();
		gbcLinha = g.c(3, y);
		g.weight(gbcLinha, 1, 0);
		g.fill(gbcLinha, GridBagConstraints.HORIZONTAL);
		searchContainer.add(txtSearchMonthlySchedule, gbcLinha);
		
		// PROCESSOS ==============================
		y++;
		searchContainer.add(new JLabel("Corte"), g.c(1, y));
		searchContainer.add(new JLabel("Montagem"), g.c(2, y));
		searchContainer.add(new JLabel("SoldaPesc"), g.c(3, y));
		
		// DATA PROCESSO (CORTE, MONTAGEM, SOLDA PESCOÇO)
		y++;
		searchContainer.add(new JLabel("Data de Processo:"), g.c(0, y));

		txtSearchDateThermalCutting = new JTextField();
		gbcLinha = g.c(1, y);
		g.weight(gbcLinha, 0.5, 0);
		g.fill(gbcLinha, GridBagConstraints.HORIZONTAL);
		searchContainer.add(txtSearchDateThermalCutting, gbcLinha);
		
		txtSearchDateBeamAssembly = new JTextField();
		gbcLinha = g.c(2, y);
		g.weight(gbcLinha, 0.5, 0);
		g.fill(gbcLinha, GridBagConstraints.HORIZONTAL);
		searchContainer.add(txtSearchDateBeamAssembly, gbcLinha);
		
		txtSearchDateNeckWelding = new JTextField();
		gbcLinha = g.c(3, y);
		g.weight(gbcLinha, 0.5, 0);
		g.fill(gbcLinha, GridBagConstraints.HORIZONTAL);
		searchContainer.add(txtSearchDateNeckWelding, gbcLinha);
		
		// TURNO PROCESSO (CORTE, MONTAGEM, SOLDA PESCOÇO)
		y++;
		searchContainer.add(new JLabel("Turno de Processo:"), g.c(0, y));
		
		txtSearchThermalCuttingShift = new JTextField();
		GridBagConstraints gbcDuplicata = g.c(1, y);
		g.weight(gbcDuplicata, 0.5, 0);
		g.fill(gbcDuplicata, GridBagConstraints.HORIZONTAL);
		searchContainer.add(txtSearchThermalCuttingShift, gbcDuplicata);

		txtSearchBeamAssemblyShift = new JTextField();
		gbcLinha = g.c(2, y);
		g.weight(gbcLinha, 0.5, 0);
		g.fill(gbcLinha, GridBagConstraints.HORIZONTAL);
		searchContainer.add(txtSearchBeamAssemblyShift, gbcLinha);
		
		txtSearchNeckWeldingShift = new JTextField();
		gbcLinha = g.c(3, y);
		g.weight(gbcLinha, 0.5, 0);
		g.fill(gbcLinha, GridBagConstraints.HORIZONTAL);
		searchContainer.add(txtSearchNeckWeldingShift, gbcLinha);
		

		// OBSERVAÇÕES =================================
		y++;
		searchContainer.add(new JLabel("Observação:"), g.c(0, y));

		txtSearchObservation = new JTextArea(3, 20);
		txtSearchObservation.setLineWrap(true);
		txtSearchObservation.setWrapStyleWord(true);

		JScrollPane scrollObs = new JScrollPane(txtSearchObservation);

		GridBagConstraints gbcObs = g.c(1, y);
		g.span(gbcObs, 3);
		g.weight(gbcObs, 1, 1);
		g.fill(gbcObs, GridBagConstraints.BOTH);
		searchContainer.add(scrollObs, gbcObs);

	}

	// =========================
	// PAINEL DE PESQUISA
	// =========================
	private JPanel createSearchPanel() {

		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

		lblPedido = new JLabel("Pedido:");
		txtSearch = new JTextField();
		btnSearch = new JButton("🔍");

		panel.add(lblPedido, BorderLayout.WEST);
		panel.add(txtSearch, BorderLayout.CENTER);
		panel.add(btnSearch, BorderLayout.EAST);

		return panel;
	}

	// =========================
	// PAINEL CENTRAL
	// =========================
	private JPanel createCenterPanel() {

		JPanel center = new JPanel(new BorderLayout(10, 10));
		center.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

		// Container da pesquisa
		center.add(createSearchContainer(), BorderLayout.CENTER);

		// Container fixo do formulário
		center.add(createFormContainer(), BorderLayout.SOUTH);

		return center;
	}

	// =========================
	// CONTAINER DA PESQUISA
	// =========================
	private JPanel createSearchContainer() {

		searchContainer = new JPanel(new BorderLayout());
		searchContainer.setBorder(BorderFactory.createTitledBorder("Pesquisa"));

		searchContainer.setPreferredSize(new Dimension(700, 220));
		searchContainer.setMinimumSize(new Dimension(700, 220));

		return searchContainer;
	}

	// =========================
	// CONTAINER DO FORMULÁRIO
	// =========================
	private JPanel createFormContainer() {

		formContainer = new JPanel(new BorderLayout());
		formContainer.setBorder(BorderFactory.createTitledBorder("Dados do Apontamento"));

		formContainer.setPreferredSize(new Dimension(700, 220));
		formContainer.setMinimumSize(new Dimension(700, 220));

		return formContainer;
	}

	// =========================
	// FORMULÁRIO DINÂMICO
	// =========================

	public JPanel getCurrentForm() {
		return currentForm;
	}

	public void setForm(JPanel form) {

		this.currentForm = form;

		formContainer.removeAll();

		JPanel centerWrapper = new JPanel(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.CENTER;

		centerWrapper.add(form, gbc);

		formContainer.add(centerWrapper, BorderLayout.CENTER);

		formContainer.revalidate();
		formContainer.repaint();
	}

	// =========================
	// BOTÕES
	// =========================
	private JPanel createButtonPanel() {

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));

		btnCreate = new JButton("Cadastro");
		btnEdit = new JButton("Editar");
		btnClear = new JButton("Limpar");

		panel.add(btnCreate);
		panel.add(btnEdit);
		panel.add(btnClear);

		return panel;
	}

}
