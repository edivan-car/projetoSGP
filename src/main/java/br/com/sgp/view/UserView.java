package br.com.sgp.view;

import br.com.sgp.dao.UserDAO;
import br.com.sgp.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

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

        loadUsers();
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

        btnRefresh.addActionListener(e -> loadUsers());
        btnClose.addActionListener(e -> dispose());

        panel.add(btnNew);
        panel.add(btnEdit);
        panel.add(btnRefresh);
        panel.add(btnClose);

        return panel;
    }

    private void loadUsers() {

        model.setRowCount(0);

        UserDAO dao = new UserDAO();
        List<User> users = dao.findAll();

        for (User u : users) {
            model.addRow(new Object[]{
                u.getId(),
                u.getUsername(),
                u.getName(),
                u.getProfile()
            });
        }
    }
}
