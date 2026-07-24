package br.com.sgp.view.resource;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import br.com.sgp.model.Resource;
import br.com.sgp.view.util.AppColors;

public class ResourceFormView extends JDialog {

    private static final long serialVersionUID = 1L;

    private final Resource resource;

    private JTextField txtCode;
    private JTextField txtLegacyCode;
    private JTextField txtDescription;
    private JTextField txtCategory;
    private JComboBox<String> cbResourceType;
    private JTextField txtUnitCode;
    private JCheckBox chkActive;

    private JButton btnSave;
    private JButton btnCancel;

    public ResourceFormView(
            Window parent,
            Resource resource) {

        super(
                parent,
                resource == null
                        ? "Novo equipamento"
                        : "Editar equipamento",
                ModalityType.APPLICATION_MODAL);

        this.resource = resource;

        setSize(520, 390);
        setResizable(false);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        add(createFormPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        if (resource != null) {
            fillForm();
        }

        configureFocusFlow();

        SwingUtilities.invokeLater(
                () -> txtCode.requestFocusInWindow());
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        10,
                        15));

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        addLabel(panel, gbc, row, "Código:");
        txtCode = new JTextField(20);
        addField(panel, gbc, row++, txtCode);

        addLabel(
                panel,
                gbc,
                row,
                "Código anterior:");
        txtLegacyCode = new JTextField();
        addField(panel, gbc, row++, txtLegacyCode);

        addLabel(panel, gbc, row, "Descrição:");
        txtDescription = new JTextField();
        addField(panel, gbc, row++, txtDescription);

        addLabel(panel, gbc, row, "Categoria:");
        txtCategory = new JTextField();
        addField(panel, gbc, row++, txtCategory);

        addLabel(panel, gbc, row, "Tipo:");
        cbResourceType =
                new JComboBox<String>(
                        new String[] {
                                "EQUIPAMENTO"
                        });
        addField(panel, gbc, row++, cbResourceType);

        addLabel(panel, gbc, row, "Unidade:");
        txtUnitCode = new JTextField();
        addField(panel, gbc, row++, txtUnitCode);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 1.0;

        chkActive =
                new JCheckBox("Recurso ativo");
        chkActive.setSelected(true);

        panel.add(chkActive, gbc);

        return panel;
    }

    private void addLabel(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String text) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;

        panel.add(new JLabel(text), gbc);
    }

    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            java.awt.Component component) {

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 1.0;

        panel.add(component, gbc);
    }

    private JPanel createButtonPanel() {
        JPanel panel =
                new JPanel(new FlowLayout(
                        FlowLayout.RIGHT,
                        8,
                        6));

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        8,
                        8,
                        8));

        btnSave = new JButton("Salvar");
        AppColors.style(
                btnSave,
                AppColors.SUCCESS);

        btnCancel = new JButton("Cancelar");
        AppColors.style(
                btnCancel,
                AppColors.NEUTRAL);

        btnCancel.addActionListener(
                event -> dispose());

        panel.add(btnSave);
        panel.add(btnCancel);

        getRootPane().setDefaultButton(btnSave);

        return panel;
    }

    private void configureFocusFlow() {
        txtCode.addActionListener(
                event -> txtLegacyCode
                        .requestFocusInWindow());

        txtLegacyCode.addActionListener(
                event -> txtDescription
                        .requestFocusInWindow());

        txtDescription.addActionListener(
                event -> txtCategory
                        .requestFocusInWindow());

        txtCategory.addActionListener(
                event -> cbResourceType
                        .requestFocusInWindow());
        
        cbResourceType.addActionListener(
                event -> txtUnitCode
                        .requestFocusInWindow());

        txtUnitCode.addActionListener(
                event -> chkActive
                        .requestFocusInWindow());
        
        chkActive.addActionListener(
                event -> btnSave
                        .requestFocusInWindow());
    }

    private void fillForm() {
        txtCode.setText(resource.getCode());
        txtLegacyCode.setText(
                resource.getLegacyCode());
        txtDescription.setText(
                resource.getDescription());
        txtCategory.setText(
                resource.getCategory());
        cbResourceType.setSelectedItem(
                resource.getResourceType());
        txtUnitCode.setText(
                resource.getUnitCode());
        chkActive.setSelected(
                resource.isActive());
    }

    private String normalizedText(
            JTextField field) {

        return field.getText()
                .trim()
                .toUpperCase(Locale.ROOT);
    }

    public Resource getResource() {
        return resource;
    }

    public String getCode() {
        return normalizedText(txtCode);
    }

    public String getLegacyCode() {
        return normalizedText(txtLegacyCode);
    }

    public String getDescription() {
        return normalizedText(txtDescription);
    }

    public String getCategory() {
        return normalizedText(txtCategory);
    }

    public String getResourceType() {
        return (String)
                cbResourceType.getSelectedItem();
    }

    public String getUnitCode() {
        return normalizedText(txtUnitCode);
    }

    public boolean isResourceActive() {
        return chkActive.isSelected();
    }

    public JButton getBtnSave() {
        return btnSave;
    }
}