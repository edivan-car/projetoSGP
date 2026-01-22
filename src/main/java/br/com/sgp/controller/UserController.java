package br.com.sgp.controller;

import br.com.sgp.dao.UserDAO;
import br.com.sgp.model.User;
import br.com.sgp.view.UserView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class UserController {

    private UserView view;
    private UserDAO dao;

    public UserController(UserView view) {
        this.view = view;
        this.dao = new UserDAO();

        initController();
        loadUsers();
    }

    private void initController() {
        // Por enquanto só carregamento inicial
        // Depois entramos com Novo / Editar
    }

    private void loadUsers() {

        DefaultTableModel model = view.getModel();
        model.setRowCount(0);

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

    // ======================
    // MÉTODOS DE APOIO
    // ======================
    public User getSelectedUser() {

        JTable table = view.getTable();
        int row = table.getSelectedRow();

        if (row == -1) {
            return null;
        }

        return new User(
                (int) table.getValueAt(row, 0),
                (String) table.getValueAt(row, 1),
                (String) table.getValueAt(row, 2),
                (String) table.getValueAt(row, 3),
                null // setor ainda não exibido na tabela
        );
    }
}
