package br.com.sgp.view;

import br.com.sgp.model.User;

import javax.swing.*;
import java.awt.*;

public class UserFormView extends JDialog {

    private JTextField txtUsername;
    private JTextField txtName;
    private JPasswordField txtPassword;
    private JComboBox<String> cbProfile;
    private JTextField txtSector;
    private JCheckBox chkActive;

    private JButton btnSave;
    private JButton btnCancel;

    private User user; // null = novo | != null = edição

    public UserFormView(Window parent, User user) {
        super(parent, "Usuário", ModalityType.APPLICATION_MODAL);
        this.user = user;

        setSize(400, 350);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BorderLayout());

        add(createFormPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        if (user != null) {
            fillForm();
        }
    }

    // ================= FORM =================
    private JPanel createFormPanel() {

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        // Username
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("Usuário:"), gbc);

        gbc.gridx = 1;
        txtUsername = new JTextField(20);
        panel.add(txtUsername, gbc);

        // Nome
        gbc.gridx = 0; gbc.gridy = ++y;
        panel.add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        txtName = new JTextField();
        panel.add(txtName, gbc);

        // Senha
        gbc.gridx = 0; gbc.gridy = ++y;
        panel.add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1;
        txtPassword = new JPasswordField();
        panel.add(txtPassword, gbc);

        // Perfil
        gbc.gridx = 0; gbc.gridy = ++y;
        panel.add(new JLabel("Perfil:"), gbc);

        gbc.gridx = 1;
        cbProfile = new JComboBox<>(new String[]{"admin", "manager", "user"});
        panel.add(cbProfile, gbc);

        // Setor
        gbc.gridx = 0; gbc.gridy = ++y;
        panel.add(new JLabel("Setor / Área:"), gbc);

        gbc.gridx = 1;
        txtSector = new JTextField();
        panel.add(txtSector, gbc);

        // Ativo
        gbc.gridx = 1; gbc.gridy = ++y;
        chkActive = new JCheckBox("Usuário ativo");
        chkActive.setSelected(true);
        panel.add(chkActive, gbc);

        return panel;
    }

    // ================= BUTTONS =================
    private JPanel createButtonPanel() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnSave = new JButton("Salvar");
        btnCancel = new JButton("Cancelar");

        btnCancel.addActionListener(e -> dispose());

        panel.add(btnSave);
        panel.add(btnCancel);

        return panel;
    }

    // ================= LOAD =================
    private void fillForm() {
        txtUsername.setText(user.getUsername());
        txtName.setText(user.getName());
        cbProfile.setSelectedItem(user.getProfile());
        txtSector.setText(user.getSector());
        chkActive.setSelected(user.isActive());
    }

    // ================= GETTERS =================
    public JButton getBtnSave() {
        return btnSave;
    }

    public String getUsername() {
        return txtUsername.getText().trim();
    }

    public String getFullName() {
        return txtName.getText().trim();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public String getProfile() {
        return (String) cbProfile.getSelectedItem();
    }

    public String getSector() {
        return txtSector.getText().trim();
    }

    public boolean isActive() {
        return chkActive.isSelected();
    }

    public User getUser() {
        return user;
    }
}
