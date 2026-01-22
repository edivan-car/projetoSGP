package br.com.sgp.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class UserView extends JInternalFrame {

    private JTable table;
    private DefaultTableModel model;

    public UserView() {
        super("Usuários", true, true, true, true);

        setSize(600, 400);
        setLayout(new BorderLayout());
        setLocation(50, 50);

        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

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

        JButton btnNew = new JButton("Novo");
        JButton btnEdit = new JButton("Editar");
        JButton btnRefresh = new JButton("Atualizar");
        JButton btnClose = new JButton("Fechar");

        btnClose.addActionListener(e -> dispose());

        panel.add(btnNew);
        panel.add(btnEdit);
        panel.add(btnRefresh);
        panel.add(btnClose);

        return panel;
    }
    
    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getModel() {
        return model;
    }
}
