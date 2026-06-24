package br.com.sgp.view.sector;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.sgp.model.Order;
import br.com.sgp.view.sector.form.PlasmaCuttingForm;
import br.com.sgp.view.sector.form.ThermalCuttingForm;

public class FabricacaoPecasView extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private static final java.text.SimpleDateFormat df =
            new java.text.SimpleDateFormat("dd/MM/yyyy");

    // ── Busca ──────────────────────────────────────────
    private JTextField txtSearch;
    private JButton    btnSearch;

    // ── Card "Resumo do pedido" ────────────────────────
    private JTextField txtCardProject;
    private JTextField txtCardAssemblyLine;
    private JTextField txtCardSchedule;
    private JTextField txtCardDateCut;
    private JTextField txtCardDateAssembly;
    private JTextField txtCardDateWeld;
    private JTextField txtCardShiftCut;
    private JTextField txtCardShiftAssembly;
    private JTextField txtCardShiftWeld;
    private JTextArea  txtCardObservation;

    // ── Abas ──────────────────────────────────────────
    private JTabbedPane tabbedPane;
    private ThermalCuttingForm  thermalForm;
    private PlasmaCuttingForm   plasmaForm;

    public FabricacaoPecasView() {
        super("Fabricação de Peças", false, true, false, false);
        setLayout(new BorderLayout(0, 4));

        add(createSearchPanel(),  BorderLayout.NORTH);
        add(createCardPanel(),    BorderLayout.CENTER);
        add(createTabbedPanel(),  BorderLayout.SOUTH);

        setSize(920, 680);
        setResizable(false);
        setMaximizable(false);
    }

    // ═══════════════════════════════════════════════════
    // PAINEL DE BUSCA
    // ═══════════════════════════════════════════════════
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 10, 4, 10));

        txtSearch = new JTextField();
        btnSearch = new JButton("🔍");
        btnSearch.setPreferredSize(new Dimension(40, 28));

        panel.add(new JLabel("Pedido:"), BorderLayout.WEST);
        panel.add(txtSearch,             BorderLayout.CENTER);
        panel.add(btnSearch,             BorderLayout.EAST);

        return panel;
    }

    // ═══════════════════════════════════════════════════
    // CARD "RESUMO DO PEDIDO"
    // ═══════════════════════════════════════════════════
    private JPanel createCardPanel() {

        JPanel card = new JPanel(new BorderLayout(0, 4));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(0, 10, 0, 10),
            BorderFactory.createTitledBorder("Resumo do pedido")
        ));

        // linha 1 — Projeto / Linha / Programação
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        txtCardProject      = readOnlyField(20);
        txtCardAssemblyLine = readOnlyField(10);
        txtCardSchedule     = readOnlyField(10);
        row1.add(new JLabel("Projeto:"));        row1.add(txtCardProject);
        row1.add(new JLabel("Linha:"));          row1.add(txtCardAssemblyLine);
        row1.add(new JLabel("Programação:"));    row1.add(txtCardSchedule);

        // linha 2 — cabeçalho colunas
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        row2.add(fixedLabel("", 80));
        row2.add(fixedLabel("Corte",    110));
        row2.add(fixedLabel("Montagem", 110));
        row2.add(fixedLabel("SoldaPesc",110));

        // linha 3 — datas
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        txtCardDateCut      = readOnlyField(10);
        txtCardDateAssembly = readOnlyField(10);
        txtCardDateWeld     = readOnlyField(10);
        row3.add(fixedLabel("Data:",    80));
        row3.add(txtCardDateCut);
        row3.add(txtCardDateAssembly);
        row3.add(txtCardDateWeld);

        // linha 4 — turnos
        JPanel row4 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        txtCardShiftCut      = readOnlyField(10);
        txtCardShiftAssembly = readOnlyField(10);
        txtCardShiftWeld     = readOnlyField(10);
        row4.add(fixedLabel("Turno:",   80));
        row4.add(txtCardShiftCut);
        row4.add(txtCardShiftAssembly);
        row4.add(txtCardShiftWeld);

        // linha 5 — observação
        JPanel row5 = new JPanel(new BorderLayout(5, 0));
        row5.setBorder(BorderFactory.createEmptyBorder(2, 8, 4, 8));
        txtCardObservation = new JTextArea(2, 40);
        txtCardObservation.setEditable(false);
        txtCardObservation.setLineWrap(true);
        txtCardObservation.setWrapStyleWord(true);
        txtCardObservation.setBackground(new java.awt.Color(245, 245, 245));
        row5.add(new JLabel("Obs:"), BorderLayout.WEST);
        row5.add(new JScrollPane(txtCardObservation), BorderLayout.CENTER);

        JPanel rows = new JPanel(new BorderLayout());
        JPanel topRows = new JPanel(new java.awt.GridLayout(4, 1, 0, 0));
        topRows.add(row1);
        topRows.add(row2);
        topRows.add(row3);
        topRows.add(row4);
        rows.add(topRows,  BorderLayout.NORTH);
        rows.add(row5,     BorderLayout.CENTER);

        card.add(rows, BorderLayout.CENTER);

        return card;
    }

    // ═══════════════════════════════════════════════════
    // ABAS
    // ═══════════════════════════════════════════════════
    private JPanel createTabbedPanel() {

        tabbedPane = new JTabbedPane();

        thermalForm = new ThermalCuttingForm();
        plasmaForm  = new PlasmaCuttingForm();

        tabbedPane.addTab("Corte Térmico",  thermalForm);
        tabbedPane.addTab("Corte a Plasma", plasmaForm);
        tabbedPane.addTab("Corte e Dobra",  new JPanel()); // <- aba desabilitada
        tabbedPane.setEnabledAt(2, false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));
        panel.add(tabbedPane, BorderLayout.CENTER);

        return panel;
    }

    // ═══════════════════════════════════════════════════
    // HELPERS
    // ═══════════════════════════════════════════════════
    private JTextField readOnlyField(int cols) {
        JTextField f = new JTextField(cols);
        f.setEditable(false);
        f.setBackground(new java.awt.Color(245, 245, 245));
        return f;
    }

    private JLabel fixedLabel(String text, int width) {
        JLabel l = new JLabel(text);
        l.setPreferredSize(new Dimension(width, 20));
        return l;
    }

    private String fmt(java.util.Date d) {
        return d == null ? "" : df.format(d);
    }

    // ═══════════════════════════════════════════════════
    // API PÚBLICA
    // ═══════════════════════════════════════════════════

    public void addSearchListener(ActionListener l) {
        btnSearch.addActionListener(l);
        txtSearch.addActionListener(l);
    }

    public String getOrderNumber() {
        return txtSearch.getText().trim().toUpperCase();
    }

    public void setOrder(Order order) {
        txtCardProject.setText(order.getProjeto());
        txtCardAssemblyLine.setText(order.getLinhaMontagem());
        txtCardSchedule.setText(order.getProgramacaoMes());
        txtCardDateCut.setText(fmt(order.getDataCorte()));
        txtCardDateAssembly.setText(fmt(order.getDataMontagem()));
        txtCardDateWeld.setText(fmt(order.getDataSoldaPescoco()));
        txtCardShiftCut.setText(order.getTurnoCorte() != null ? order.getTurnoCorte() : "");
        txtCardShiftAssembly.setText(order.getTurnoMontagem() != null ? order.getTurnoMontagem() : "");
        txtCardShiftWeld.setText(order.getTurnoSoldaPescoco() != null ? order.getTurnoSoldaPescoco() : "");
        txtCardObservation.setText(order.getObservacao() != null ? order.getObservacao() : "");
    }

    public void clearCard() {
        txtCardProject.setText("");
        txtCardAssemblyLine.setText("");
        txtCardSchedule.setText("");
        txtCardDateCut.setText("");
        txtCardDateAssembly.setText("");
        txtCardDateWeld.setText("");
        txtCardShiftCut.setText("");
        txtCardShiftAssembly.setText("");
        txtCardShiftWeld.setText("");
        txtCardObservation.setText("");
        txtSearch.setText("");
        txtSearch.requestFocusInWindow();
    }

    public void showMessage(String msg) {
        javax.swing.JOptionPane.showMessageDialog(this, msg);
    }

    public ThermalCuttingForm getThermalForm()  { return thermalForm; }
    public PlasmaCuttingForm  getPlasmaForm()   { return plasmaForm;  }
    public JTabbedPane        getTabbedPane()   { return tabbedPane;  }
    public int                getSelectedTab()  { return tabbedPane.getSelectedIndex(); }
}