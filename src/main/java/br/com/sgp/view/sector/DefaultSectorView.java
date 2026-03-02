package br.com.sgp.view.sector;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.sgp.model.Order;
import br.com.sgp.view.util.GridBagHelper;

public class DefaultSectorView extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	
	private final java.text.SimpleDateFormat df =
	        new java.text.SimpleDateFormat("dd/MM/yyyy");

	private JLabel lblOrder;
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

	private SectorForm currentForm;

	private JButton btnCreate;
	private JButton btnEdit;
	private JButton btnClear;
	
	private String sectorName;

	public DefaultSectorView(String title, String sectorName) {
		super(title, false, true, false, false); // super(title, resizable, closable, maximizable, iconifiable);

		this.sectorName = sectorName;
		
	    setLayout(new BorderLayout());

	    add(createSearchPanel(), BorderLayout.NORTH);
	    add(createCenterPanel(), BorderLayout.CENTER);
	    add(createButtonPanel(), BorderLayout.SOUTH);

	    setSize(900, 600);
	    setResizable(false);
	    setMaximizable(false);

	    initComponents();
	}

	private void initComponents() {
		
		searchContainer.setLayout(new GridBagLayout());

		GridBagHelper g = new GridBagHelper();
		int y = 0;

		// PROJETO ================================
		searchContainer.add(new JLabel("Projeto:"), g.c(0, y));

		txtSearchProject = new JTextField(20);
		txtSearchProject.setPreferredSize(new Dimension(200, 22));
		txtSearchProject.setMinimumSize(new Dimension(200, 22));
		txtSearchProject.setMaximumSize(new Dimension(200, 22));
		GridBagConstraints gbcLinha = g.c(1, y);
		g.span(gbcLinha, 3);
		g.weight(gbcLinha, 0, 0);
		g.fill(gbcLinha, GridBagConstraints.HORIZONTAL);
		searchContainer.add(txtSearchProject, gbcLinha);
		
		// LINHA MONTAGEM =========================
		y++;
		searchContainer.add(new JLabel("Linha de Montagem:"), g.c(0, y));

		txtSearchAssemblyLine = new JTextField(20);
		txtSearchAssemblyLine.setPreferredSize(new Dimension(60, 22));
		txtSearchAssemblyLine.setMinimumSize(new Dimension(60, 22));
		txtSearchAssemblyLine.setMaximumSize(new Dimension(60, 22));
		gbcLinha = g.c(1, y);
		g.weight(gbcLinha, 0, 0);
		g.fill(gbcLinha, GridBagConstraints.HORIZONTAL);
		searchContainer.add(txtSearchAssemblyLine, gbcLinha);
		
		// PROGRAMAÇÃO DO MÊS =====================
		searchContainer.add(new JLabel("Programação:"), g.c(2, y));

		txtSearchMonthlySchedule = new JTextField(20);
		gbcLinha = g.c(3, y);
		g.weight(gbcLinha, 0, 0);
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

		txtSearchDateThermalCutting = new JTextField(20);
		gbcLinha = g.c(1, y);
		g.weight(gbcLinha, 0, 0);
		g.fill(gbcLinha, GridBagConstraints.HORIZONTAL);
		searchContainer.add(txtSearchDateThermalCutting, gbcLinha);
		
		txtSearchDateBeamAssembly = new JTextField(20);
		gbcLinha = g.c(2, y);
		g.weight(gbcLinha, 0, 0);
		g.fill(gbcLinha, GridBagConstraints.HORIZONTAL);
		searchContainer.add(txtSearchDateBeamAssembly, gbcLinha);
		
		txtSearchDateNeckWelding = new JTextField(20);
		gbcLinha = g.c(3, y);
		g.weight(gbcLinha, 0, 0);
		g.fill(gbcLinha, GridBagConstraints.HORIZONTAL);
		searchContainer.add(txtSearchDateNeckWelding, gbcLinha);
		
		// TURNO PROCESSO (CORTE, MONTAGEM, SOLDA PESCOÇO)
		y++;
		searchContainer.add(new JLabel("Turno de Processo:"), g.c(0, y));
		
		txtSearchThermalCuttingShift = new JTextField(20);
		gbcLinha = g.c(1, y);
		g.weight(gbcLinha, 0, 0);
		g.fill(gbcLinha, GridBagConstraints.HORIZONTAL);
		searchContainer.add(txtSearchThermalCuttingShift, gbcLinha);

		txtSearchBeamAssemblyShift = new JTextField(20);
		gbcLinha = g.c(2, y);
		g.weight(gbcLinha, 0, 0);
		g.fill(gbcLinha, GridBagConstraints.HORIZONTAL);
		searchContainer.add(txtSearchBeamAssemblyShift, gbcLinha);
		
		txtSearchNeckWeldingShift = new JTextField(20);
		gbcLinha = g.c(3, y);
		g.weight(gbcLinha, 0, 0);
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

		lblOrder = new JLabel("Pedido:");
		txtSearch = new JTextField();
		btnSearch = new JButton("🔍");

		panel.add(lblOrder, BorderLayout.WEST);
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

	public SectorForm getCurrentForm() {
		return currentForm;
	}

	public void setForm(JPanel form) {
		
		if (!(form instanceof SectorForm)) {
	        throw new IllegalArgumentException("Form must implement SectorForm");
	    }

		this.currentForm = (SectorForm) form;

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
	
	public String getSectorName() {
	    return sectorName;
	}
	
	public void addSearchListener(ActionListener listener) {
	    btnSearch.addActionListener(listener);
	    txtSearch.addActionListener(listener);
	}
	
	public String getOrderNumber() {
	    return txtSearch.getText().trim().toUpperCase();
	}
	
	private String formatDate(java.util.Date date) {
	    return date == null ? "" : df.format(date);
	}
	
	public void setOrder(Order order) {
	    txtSearchProject.setText(order.getProjeto());
	    txtSearchAssemblyLine.setText(order.getLinhaMontagem());
	    txtSearchMonthlySchedule.setText(order.getProgramacaoMes());

	    txtSearchDateThermalCutting.setText(
	    		formatDate(order.getDataCorte())
	    );

	    txtSearchThermalCuttingShift.setText(order.getTurnoCorte()!= null ? order.getTurnoCorte() : "");

	    txtSearchDateBeamAssembly.setText(
	    		formatDate(order.getDataMontagem())
	    );

	    txtSearchBeamAssemblyShift.setText(order.getTurnoMontagem()!= null ? order.getTurnoMontagem() : "");

	    txtSearchDateNeckWelding.setText(
	    		formatDate(order.getDataSoldaPescoco())
	    );

	    txtSearchNeckWeldingShift.setText(order.getTurnoSoldaPescoco()!= null ? order.getTurnoSoldaPescoco() : "");

	    txtSearchObservation.setText(order.getObservacao()!= null ? order.getObservacao() : "");
	}
	
	public void showMessage(String message) {
	    javax.swing.JOptionPane.showMessageDialog(this, message);
	}
	
	public void addCreateListener(ActionListener listener) {
	    btnCreate.addActionListener(listener);
	}
	
	public void addClearListener(ActionListener listener) {
	    btnClear.addActionListener(listener);
	}
	
	public void clearFields() {
		txtSearch.setText("");
	    txtSearchProject.setText("");
	    txtSearchAssemblyLine.setText("");
	    txtSearchMonthlySchedule.setText("");
	    txtSearchDateThermalCutting.setText("");
	    txtSearchThermalCuttingShift.setText("");
	    txtSearchDateBeamAssembly.setText("");
	    txtSearchBeamAssemblyShift.setText("");
	    txtSearchDateNeckWelding.setText("");
	    txtSearchNeckWeldingShift.setText("");
	    txtSearchObservation.setText("");
	    
	    if (currentForm != null) {
	    	currentForm.clearForm();
	    }
	    
	    txtSearch.requestFocusInWindow();
	}

}
