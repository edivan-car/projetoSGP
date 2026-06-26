package br.com.sgp.view.sector;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import br.com.sgp.model.Order;
import br.com.sgp.view.sector.component.OrderSummaryCard;
import br.com.sgp.view.sector.form.PlasmaCuttingForm;
import br.com.sgp.view.sector.form.ThermalCuttingForm;

public class FabricacaoPecasView extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    // ── Busca ──────────────────────────────────────────
    private JTextField txtSearch;
    private JButton    btnSearch;

    // ── Card "Resumo do pedido" ────────────────────────
    private OrderSummaryCard orderSummaryCard;

    // ── Abas ──────────────────────────────────────────
    private JTabbedPane tabbedPane;
    private ThermalCuttingForm  thermalForm;
    private PlasmaCuttingForm   plasmaForm;

    public FabricacaoPecasView() {
        super("Fabricação de Peças", false, true, false, false);
        setLayout(new BorderLayout(0, 4));

        add(createSearchPanel(),  BorderLayout.NORTH);
        orderSummaryCard = new OrderSummaryCard();

        add(createContentPanel(), BorderLayout.CENTER);

        setSize(920, 540);
        setResizable(false);
        setMaximizable(false);
    }

    // ═══════════════════════════════════════════════════
    // PAINEL DE BUSCA
    // ═══════════════════════════════════════════════════
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 10, 4, 10));

        txtSearch = new JTextField();
        btnSearch = new JButton("...");
        btnSearch.setPreferredSize(new Dimension(55, 28));

        JPanel right = new JPanel(new BorderLayout(5, 0));
        right.add(txtSearch, BorderLayout.CENTER);
        right.add(btnSearch, BorderLayout.EAST);

        panel.add(new JLabel("Pedido:"), BorderLayout.WEST);
        panel.add(right,                 BorderLayout.CENTER);

        return panel;
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 4));
        panel.add(orderSummaryCard, BorderLayout.NORTH);
        panel.add(createTabbedPanel(), BorderLayout.CENTER);
        return panel;
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
        orderSummaryCard.setOrder(order);
    }

    public void clearCard() {
        orderSummaryCard.clear();
        txtSearch.requestFocusInWindow();
    }

    public void clearAllFields() {
        clearCard();
        thermalForm.clearForm();
        plasmaForm.clearForm();
    }

    public void showMessage(String msg) {
        javax.swing.JOptionPane.showMessageDialog(this, msg);
    }
    
    public void setFormsEditable(boolean editable) {
        thermalForm.setFieldsEditable(editable);
        plasmaForm.setFieldsEditable(editable);
    }

    public void setRegisterEnabled(boolean enabled) {
        thermalForm.getBtnReg().setEnabled(enabled);
        plasmaForm.getBtnRegister().setEnabled(enabled);
    }

    public ThermalCuttingForm getThermalForm()  { return thermalForm; }
    public PlasmaCuttingForm  getPlasmaForm()   { return plasmaForm;  }
    public JTabbedPane        getTabbedPane()   { return tabbedPane;  }
    public int                getSelectedTab()  { return tabbedPane.getSelectedIndex(); }
}
