package br.com.sgp.view.production;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;

import br.com.sgp.session.UserSession;
import br.com.sgp.view.util.AppColors;

public class ProductionRecordView extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private JComboBox<String> cmbResource;
    private JTextField txtProduct;
    private JComboBox<String> cmbShift;
    private JFormattedTextField txtDate;
    private JFormattedTextField txtStartTime;
    private JFormattedTextField txtEndTime;
    private JTextField txtOperator;
    private JLabel lblDuration;

    private JTextField txtOrderNumber;
    private JSpinner spnFirstQuality;
    private JSpinner spnSecondQuality;
    private JSpinner spnThirdQuality;
    private JSpinner spnScrap;
    private JButton btnAddOrder;
    private JButton btnRemoveOrder;
    private JTable tblOrders;
    private ProductionOrderTableModel orderTableModel;

    private JComboBox<String> cmbStopReason;
    private JFormattedTextField txtStopStart;
    private JFormattedTextField txtStopEnd;
    private JTextField txtStopObservation;
    private JButton btnAddStop;
    private JButton btnRemoveStop;
    private JTable tblStops;
    private StopTableModel stopTableModel;

    private JButton btnRegister;
    private JButton btnClear;

    public ProductionRecordView() {
        super("Registro de Produção — Corte e Dobra", false, true, false, false);
        setLayout(new BorderLayout());
        setContentPane(createContent());
        setSize(980, 680);
        setMinimumSize(new Dimension(900, 600));
        setResizable(true);
        setMaximizable(false);
        updateDuration();
    }

    private JComponent createContent() {
        JPanel content = new JPanel(new BorderLayout(0, 10));
        content.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        JPanel sections = new JPanel();
        sections.setLayout(new javax.swing.BoxLayout(sections, javax.swing.BoxLayout.Y_AXIS));
        sections.add(createHeaderPanel());
        sections.add(javax.swing.Box.createVerticalStrut(8));
        sections.add(createOrderPanel());
        sections.add(javax.swing.Box.createVerticalStrut(8));
        sections.add(createStopPanel());

        JScrollPane scroll = new JScrollPane(sections);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(14);

        content.add(scroll, BorderLayout.CENTER);
        content.add(createFooter(), BorderLayout.SOUTH);
        return content;
    }

    private JPanel createHeaderPanel() {
        JPanel wrapper = sectionPanel("Dados do apontamento");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = constraints();

        cmbResource = new JComboBox<String>(new String[] {
                "Selecione...", "Guilhotina 01", "Guilhotina 02",
                "Prensa Excêntrica 40T", "Prensa Hidráulica 80T", "Dobradeira CNC 01"
        });
        txtProduct = new JTextField();
        cmbShift = new JComboBox<String>(new String[] { "1º Turno", "2º Turno", "3º Turno" });

        addField(panel, gbc, 0, 0, 1, 0.36, "Recurso", cmbResource);
        addField(panel, gbc, 1, 0, 1, 0.36, "Código do produto", txtProduct);
        addField(panel, gbc, 2, 0, 1, 0.28, "Turno", cmbShift);

        txtDate = formattedField(LocalDate.now().format(DATE_FORMAT), 10);
        txtStartTime = timeField();
        txtEndTime = timeField();
        txtOperator = new JTextField(currentOperator());
        txtOperator.setEditable(false);
        txtOperator.setBackground(AppColors.FIELD_BG);

        addField(panel, gbc, 0, 1, 1, 0.25, "Data (dd/mm/aaaa)", txtDate);
        addField(panel, gbc, 1, 1, 1, 0.20, "Hora início (hh:mm)", txtStartTime);
        addField(panel, gbc, 2, 1, 1, 0.20, "Hora fim (hh:mm)", txtEndTime);
        addField(panel, gbc, 3, 1, 1, 0.35, "Operador", txtOperator);

        JPanel durationPanel = new JPanel(new BorderLayout());
        JLabel caption = new JLabel("TEMPO DO PROCESSO", SwingConstants.RIGHT);
        caption.setFont(caption.getFont().deriveFont(Font.BOLD, 10f));
        caption.setForeground(AppColors.NEUTRAL);
        lblDuration = new JLabel("—", SwingConstants.RIGHT);
        lblDuration.setFont(lblDuration.getFont().deriveFont(Font.BOLD, 21f));
        lblDuration.setForeground(AppColors.ACCENT);
        durationPanel.add(caption, BorderLayout.NORTH);
        durationPanel.add(lblDuration, BorderLayout.CENTER);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.20;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(durationPanel, gbc);

        installDurationListener(txtDate);
        installDurationListener(txtStartTime);
        installDurationListener(txtEndTime);

        wrapper.add(panel, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel createOrderPanel() {
        JPanel wrapper = sectionPanel("Ordens de Produção processadas");
        JPanel input = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = constraints();

        txtOrderNumber = new JTextField();
        spnFirstQuality = quantitySpinner();
        spnSecondQuality = quantitySpinner();
        spnThirdQuality = quantitySpinner();
        spnScrap = quantitySpinner();
        btnAddOrder = new JButton("Adicionar OP");
        AppColors.style(btnAddOrder, AppColors.ACCENT);

        addField(input, gbc, 0, 0, 1, 0.32, "Nº da OP", txtOrderNumber);
        addField(input, gbc, 1, 0, 1, 0.14, "1ª qualidade", spnFirstQuality);
        addField(input, gbc, 2, 0, 1, 0.14, "2ª qualidade", spnSecondQuality);
        addField(input, gbc, 3, 0, 1, 0.14, "3ª qualidade", spnThirdQuality);
        addField(input, gbc, 4, 0, 1, 0.14, "Refugo", spnScrap);
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.weightx = 0.12;
        gbc.anchor = GridBagConstraints.SOUTH;
        input.add(btnAddOrder, gbc);

        orderTableModel = new ProductionOrderTableModel();
        tblOrders = new JTable(orderTableModel);
        configureTable(tblOrders);
        tblOrders.setPreferredScrollableViewportSize(new Dimension(850, 60));

        btnRemoveOrder = new JButton("Remover OP selecionada");
        AppColors.style(btnRemoveOrder, AppColors.NEUTRAL);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 4));
        actions.add(btnRemoveOrder);

        JPanel center = new JPanel(new BorderLayout(0, 4));
        center.add(input, BorderLayout.NORTH);
        center.add(new JScrollPane(tblOrders), BorderLayout.CENTER);
        center.add(actions, BorderLayout.SOUTH);
        wrapper.add(center, BorderLayout.CENTER);

        btnAddOrder.addActionListener(e -> addOrderFromForm());
        btnRemoveOrder.addActionListener(e -> removeSelectedOrder());
        txtOrderNumber.addActionListener(e -> addOrderFromForm());
        return wrapper;
    }

    private JPanel createStopPanel() {
        JPanel wrapper = sectionPanel("Paradas ocorridas no período — opcional");
        JPanel input = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = constraints();

        cmbStopReason = new JComboBox<String>(new String[] {
                "Troca de ferramenta (prevista)", "Setup / ajuste de máquina (prevista)",
                "Falta de material (não prevista)", "Quebra / manutenção corretiva (não prevista)",
                "Falta de operador (não prevista)"
        });
        txtStopStart = timeField();
        txtStopEnd = timeField();
        txtStopObservation = new JTextField();
        btnAddStop = new JButton("Adicionar parada");
        AppColors.style(btnAddStop, AppColors.WARNING);

        addField(input, gbc, 0, 0, 1, 0.34, "Motivo", cmbStopReason);
        addField(input, gbc, 1, 0, 1, 0.13, "Início", txtStopStart);
        addField(input, gbc, 2, 0, 1, 0.13, "Fim", txtStopEnd);
        addField(input, gbc, 3, 0, 1, 0.28, "Observação", txtStopObservation);
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 0.12;
        gbc.anchor = GridBagConstraints.SOUTH;
        input.add(btnAddStop, gbc);

        stopTableModel = new StopTableModel();
        tblStops = new JTable(stopTableModel);
        configureTable(tblStops);
        tblStops.setPreferredScrollableViewportSize(new Dimension(850, 80));

        btnRemoveStop = new JButton("Remover parada selecionada");
        AppColors.style(btnRemoveStop, AppColors.NEUTRAL);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 4));
        actions.add(btnRemoveStop);

        JPanel center = new JPanel(new BorderLayout(0, 4));
        center.add(input, BorderLayout.NORTH);
        center.add(new JScrollPane(tblStops), BorderLayout.CENTER);
        center.add(actions, BorderLayout.SOUTH);
        wrapper.add(center, BorderLayout.CENTER);

        btnAddStop.addActionListener(e -> addStopFromForm());
        btnRemoveStop.addActionListener(e -> removeSelectedStop());
        return wrapper;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, AppColors.NEUTRAL));
        btnClear = new JButton("Limpar");
        btnRegister = new JButton("Registrar");
        AppColors.style(btnClear, AppColors.NEUTRAL);
        AppColors.style(btnRegister, AppColors.SUCCESS);
        footer.add(btnClear);
        footer.add(btnRegister);
        btnClear.addActionListener(e -> clearForm());
        return footer;
    }

    private JPanel sectionPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(title),
                BorderFactory.createEmptyBorder(5, 7, 7, 7)));
        return panel;
    }

    private GridBagConstraints constraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 4, 3, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int x, int y, int width,
            double weight, String label, JComponent component) {
        JPanel field = new JPanel(new BorderLayout(0, 3));
        JLabel lbl = new JLabel(label);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 11f));
        field.add(lbl, BorderLayout.NORTH);
        field.add(component, BorderLayout.CENTER);
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.weightx = weight;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(field, gbc);
    }

    private JFormattedTextField formattedField(String value, int columns) {
        JFormattedTextField field = new JFormattedTextField();
        field.setColumns(columns);
        field.setText(value);
        return field;
    }
    
    private JFormattedTextField timeField() {
        try {
            MaskFormatter mask = new MaskFormatter("##:##");
            mask.setPlaceholderCharacter('_');
            mask.setOverwriteMode(true);

            JFormattedTextField field =
                    new JFormattedTextField(mask);

            field.setColumns(5);
            field.setHorizontalAlignment(JTextField.CENTER);

            field.addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusGained(java.awt.event.FocusEvent e) {
                    javax.swing.SwingUtilities.invokeLater(
                            () -> field.setCaretPosition(0));
                }
            });

            return field;

        } catch (java.text.ParseException e) {
            throw new IllegalStateException(
                    "Não foi possível criar o campo de horário.", e);
        }
    }

    private JSpinner quantitySpinner() {
        JSpinner spinner = new JSpinner(
                new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));

        JSpinner.NumberEditor editor =
                (JSpinner.NumberEditor) spinner.getEditor();

        JTextField textField = editor.getTextField();

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                javax.swing.SwingUtilities.invokeLater(
                        () -> textField.selectAll());
            }
        });

        return spinner;
    }

    private void configureTable(JTable table) {
        table.setFillsViewportHeight(true);
        table.setRowHeight(23);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(SwingConstants.RIGHT);
        for (int column = 1; column < table.getColumnCount(); column++) {
            table.getColumnModel().getColumn(column).setCellRenderer(right);
        }
    }

    private void installDurationListener(JTextField field) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateDuration(); }
            public void removeUpdate(DocumentEvent e) { updateDuration(); }
            public void changedUpdate(DocumentEvent e) { updateDuration(); }
        });
    }

    private void updateDuration() {
        if (lblDuration == null) return;
        try {
            LocalTime start = LocalTime.parse(txtStartTime.getText().trim(), TIME_FORMAT);
            LocalTime end = LocalTime.parse(txtEndTime.getText().trim(), TIME_FORMAT);
            long minutes = Duration.between(start, end).toMinutes();
            if (minutes < 0) minutes += 24 * 60;
            lblDuration.setText(String.format("%02d:%02d", minutes / 60, minutes % 60));
        } catch (DateTimeParseException ex) {
            lblDuration.setText("—");
        }
    }

    private void addOrderFromForm() {
        String orderNumber = txtOrderNumber.getText().trim().toUpperCase();
        int q1 = spinnerValue(spnFirstQuality);
        int q2 = spinnerValue(spnSecondQuality);
        int q3 = spinnerValue(spnThirdQuality);
        int scrap = spinnerValue(spnScrap);
        if (orderNumber.isEmpty()) {
            showMessage("Informe o número da OP.");
            txtOrderNumber.requestFocusInWindow();
            return;
        }
        if (q1 + q2 + q3 + scrap == 0) {
            showMessage("Informe ao menos uma quantidade para a OP.");
            return;
        }
        orderTableModel.add(new ProductionOrderRow(orderNumber, q1, q2, q3, scrap));
        clearOrderInput();
    }

    private void addStopFromForm() {
        try {
            LocalTime start = LocalTime.parse(txtStopStart.getText().trim(), TIME_FORMAT);
            LocalTime end = LocalTime.parse(txtStopEnd.getText().trim(), TIME_FORMAT);
            stopTableModel.add(new StopRow(String.valueOf(cmbStopReason.getSelectedItem()), start, end,
                    txtStopObservation.getText().trim()));
            txtStopStart.setText("");
            txtStopEnd.setText("");
            txtStopObservation.setText("");
        } catch (DateTimeParseException ex) {
            showMessage("Informe início e fim da parada no formato HH:mm.");
        }
    }

    private void removeSelectedOrder() {
        int row = tblOrders.getSelectedRow();
        if (row >= 0) orderTableModel.remove(row);
    }

    private void removeSelectedStop() {
        int row = tblStops.getSelectedRow();
        if (row >= 0) stopTableModel.remove(row);
    }

    private int spinnerValue(JSpinner spinner) {
        return ((Number) spinner.getValue()).intValue();
    }

    private void clearOrderInput() {
        txtOrderNumber.setText("");
        spnFirstQuality.setValue(0);
        spnSecondQuality.setValue(0);
        spnThirdQuality.setValue(0);
        spnScrap.setValue(0);
        txtOrderNumber.requestFocusInWindow();
    }

    public boolean validateRecord() {
        if (cmbResource.getSelectedIndex() <= 0 || txtProduct.getText().trim().isEmpty()) {
            showMessage("Selecione o recurso e informe o código do produto.");
            return false;
        }
        try {
            LocalDate.parse(txtDate.getText().trim(), DATE_FORMAT);
            LocalTime.parse(txtStartTime.getText().trim(), TIME_FORMAT);
            LocalTime.parse(txtEndTime.getText().trim(), TIME_FORMAT);
        } catch (DateTimeParseException ex) {
            showMessage("Confira a data e os horários do apontamento.");
            return false;
        }
        if (orderTableModel.getRows().isEmpty()) {
            showMessage("Adicione ao menos uma OP ao apontamento.");
            return false;
        }
        return true;
    }

    public void clearForm() {
        cmbResource.setSelectedIndex(0);
        txtProduct.setText("");
        cmbShift.setSelectedIndex(0);
        txtDate.setText(LocalDate.now().format(DATE_FORMAT));
        txtStartTime.setText("");
        txtEndTime.setText("");
        clearOrderInput();
        orderTableModel.clear();
        stopTableModel.clear();
        txtStopStart.setText("");
        txtStopEnd.setText("");
        txtStopObservation.setText("");
        cmbResource.requestFocusInWindow();
    }

    private String currentOperator() {
        String name = UserSession.getInstance().getName();
        return name == null ? "" : name;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void addRegisterListener(ActionListener listener) { btnRegister.addActionListener(listener); }
    public JComboBox<String> getCmbResource() { return cmbResource; }
    public JTextField getTxtProduct() { return txtProduct; }
    public JComboBox<String> getCmbShift() { return cmbShift; }
    public JFormattedTextField getTxtDate() { return txtDate; }
    public JFormattedTextField getTxtStartTime() { return txtStartTime; }
    public JFormattedTextField getTxtEndTime() { return txtEndTime; }
    public JTextField getTxtOperator() { return txtOperator; }
    public JButton getBtnRegister() { return btnRegister; }
    public List<ProductionOrderRow> getOrders() { return orderTableModel.getRows(); }
    public List<StopRow> getStops() { return stopTableModel.getRows(); }

    public static final class ProductionOrderRow {
        private final String orderNumber;
        private final int firstQuality;
        private final int secondQuality;
        private final int thirdQuality;
        private final int scrap;

        public ProductionOrderRow(String orderNumber, int firstQuality, int secondQuality,
                int thirdQuality, int scrap) {
            this.orderNumber = orderNumber;
            this.firstQuality = firstQuality;
            this.secondQuality = secondQuality;
            this.thirdQuality = thirdQuality;
            this.scrap = scrap;
        }

        public String getOrderNumber() { return orderNumber; }
        public int getFirstQuality() { return firstQuality; }
        public int getSecondQuality() { return secondQuality; }
        public int getThirdQuality() { return thirdQuality; }
        public int getScrap() { return scrap; }
        public int getTotal() { return firstQuality + secondQuality + thirdQuality + scrap; }
    }

    public static final class StopRow {
        private final String reason;
        private final LocalTime start;
        private final LocalTime end;
        private final String observation;

        public StopRow(String reason, LocalTime start, LocalTime end, String observation) {
            this.reason = reason;
            this.start = start;
            this.end = end;
            this.observation = observation;
        }

        public String getReason() { return reason; }
        public LocalTime getStart() { return start; }
        public LocalTime getEnd() { return end; }
        public String getObservation() { return observation; }
    }

    private static final class ProductionOrderTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        private final String[] columns = { "Nº OP", "1ª", "2ª", "3ª", "Refugo", "Total" };
        private final List<ProductionOrderRow> rows = new ArrayList<ProductionOrderRow>();
        public int getRowCount() { return rows.size() + 1; }
        public int getColumnCount() { return columns.length; }
        public String getColumnName(int column) { return columns[column]; }
        public Object getValueAt(int row, int column) {
            if (row == rows.size()) return totalValue(column);
            ProductionOrderRow value = rows.get(row);
            switch (column) {
            case 0: return value.getOrderNumber();
            case 1: return value.getFirstQuality();
            case 2: return value.getSecondQuality();
            case 3: return value.getThirdQuality();
            case 4: return value.getScrap();
            default: return value.getTotal();
            }
        }
        private Object totalValue(int column) {
            if (column == 0) return "Total do apontamento";
            int total = 0;
            for (ProductionOrderRow row : rows) {
                if (column == 1) total += row.getFirstQuality();
                else if (column == 2) total += row.getSecondQuality();
                else if (column == 3) total += row.getThirdQuality();
                else if (column == 4) total += row.getScrap();
                else total += row.getTotal();
            }
            return total;
        }
        void add(ProductionOrderRow row) { rows.add(row); fireTableDataChanged(); }
        void remove(int row) { if (row < rows.size()) { rows.remove(row); fireTableDataChanged(); } }
        void clear() { rows.clear(); fireTableDataChanged(); }
        List<ProductionOrderRow> getRows() { return new ArrayList<ProductionOrderRow>(rows); }
    }

    private static final class StopTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        private final String[] columns = { "Motivo", "Início", "Fim", "Observação" };
        private final List<StopRow> rows = new ArrayList<StopRow>();
        public int getRowCount() { return rows.size(); }
        public int getColumnCount() { return columns.length; }
        public String getColumnName(int column) { return columns[column]; }
        public Object getValueAt(int row, int column) {
            StopRow value = rows.get(row);
            switch (column) {
            case 0: return value.getReason();
            case 1: return value.getStart().format(TIME_FORMAT);
            case 2: return value.getEnd().format(TIME_FORMAT);
            default: return value.getObservation();
            }
        }
        void add(StopRow row) { rows.add(row); fireTableDataChanged(); }
        void remove(int row) { rows.remove(row); fireTableDataChanged(); }
        void clear() { rows.clear(); fireTableDataChanged(); }
        List<StopRow> getRows() { return new ArrayList<StopRow>(rows); }
    }
}
