package br.com.sgp.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UserView extends JInternalFrame {

    private JTable table;
    private DefaultTableModel model;

    private JButton btnNew;
    private JButton btnEdit;
    private JButton btnRefresh;
    private JButton btnClose;

    public UserView() {
        super("Usuários", true, true, true, true);

        setSize(600, 400);
        setLayout(new BorderLayout());
        setLocation(50, 50);

        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        btnEdit.setEnabled(false);
        table.getSelectionModel().addListSelectionListener(e ->
                btnEdit.setEnabled(table.getSelectedRow() != -1)
        );
    }

    private JScrollPane createTablePanel() {

        model = new DefaultTableModel(
                new Object[]{"ID", "Usuário", "Nome", "Perfil"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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

        panel.add(btnNew);
        panel.add(btnEdit);
        panel.add(btnRefresh);
        panel.add(btnClose);

        return panel;
    }

    public JTable getTable() { return table; }
    public DefaultTableModel getModel() { return model; }
    public JButton getBtnNew() { return btnNew; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnRefresh() { return btnRefresh; }
}
