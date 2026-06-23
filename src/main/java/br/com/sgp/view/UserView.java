package br.com.sgp.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UserView extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	
	private JTable table;
    private DefaultTableModel model;

    private JButton btnNew;
    private JButton btnEdit;
    private JButton btnRefresh;
    private JButton btnClose;
    private JButton btnResetPassword;

    public UserView() {
        super("Usuários", true, true, true, true);

        setSize(680, 400);
        setLayout(new BorderLayout());
        setLocation(50, 50);

        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        btnEdit.setEnabled(false);
        
        table.getSelectionModel().addListSelectionListener(e -> {
        	boolean selected = table.getSelectedRow() != -1;
            btnEdit.setEnabled(selected);
            btnResetPassword.setEnabled(selected);
        });
    }

    private JScrollPane createTablePanel() {

        model = new DefaultTableModel(
                new Object[]{"ID", "Usuário", "Nome", "Perfil", "Setor"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // ID
        table.getColumnModel().getColumn(0).setMinWidth(50);
        table.getColumnModel().getColumn(0).setMaxWidth(70);

        // Usuário
        table.getColumnModel().getColumn(1).setPreferredWidth(120);

        // Nome
        table.getColumnModel().getColumn(2).setPreferredWidth(250);

        // Perfil
        table.getColumnModel().getColumn(3).setPreferredWidth(100);

        // Setor
        table.getColumnModel().getColumn(4).setPreferredWidth(150);

        return new JScrollPane(table);
    }

    private JPanel createButtonPanel() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnNew = new JButton("Novo");
        btnEdit = new JButton("Editar");
        btnRefresh = new JButton("Atualizar");
        btnClose = new JButton("Fechar");

        btnClose.addActionListener(e -> {
            int opt = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente fechar?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION
            );
            if (opt == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        btnResetPassword = new JButton("Resetar Senha");
        btnResetPassword.setEnabled(false);
        
        panel.add(btnNew);
        panel.add(btnEdit);
        panel.add(btnResetPassword);
        panel.add(btnRefresh);
        panel.add(btnClose);

        return panel;
    }

    public JTable getTable() { return table; }
    public DefaultTableModel getModel() { return model; }
    public JButton getBtnNew() { return btnNew; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnRefresh() { return btnRefresh; }
    public JButton getBtnResetPassword() { return btnResetPassword; }
}
