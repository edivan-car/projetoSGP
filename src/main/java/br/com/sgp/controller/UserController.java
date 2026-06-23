package br.com.sgp.controller;

import br.com.sgp.dao.UserDAO;
import br.com.sgp.model.User;
import br.com.sgp.util.PasswordUtil;
import br.com.sgp.view.UserFormView;
import br.com.sgp.view.UserView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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

		view.getBtnNew().addActionListener(e -> newUser());
		view.getBtnEdit().addActionListener(e -> editUser());
		view.getBtnRefresh().addActionListener(e -> loadUsers());
		view.getBtnResetPassword().addActionListener(e -> resetPassword());
	}

	// ======================
	// AÇÕES
	// ======================

	private void newUser() {

		UserFormView form = new UserFormView(SwingUtilities.getWindowAncestor(view), null);

		form.getBtnSave().addActionListener(e -> saveUser(form));
		form.setVisible(true);
	}

	private void editUser() {

		User selected = getSelectedUser();

		if (selected == null) {
			JOptionPane.showMessageDialog(view, "Selecione um usuário para editar", "Atenção",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Window parent = SwingUtilities.getWindowAncestor(view);

		UserFormView form = new UserFormView(parent, selected);
		form.getBtnSave().addActionListener(e -> saveUser(form));
		form.setVisible(true);
	}

	private void saveUser(UserFormView form) {

	    if (form.getUsername().isEmpty() || form.getName().isEmpty()) {
	        JOptionPane.showMessageDialog(
	                form,
	                "Usuário e Nome são obrigatórios",
	                "Validação",
	                JOptionPane.WARNING_MESSAGE
	        );
	        return;
	    }

	    String password = form.getPassword();

	    if (form.getUser() != null && password.isEmpty()) {
	        password = form.getUser().getPassword();
	    } else if (form.getUser() == null && password.isEmpty()) {
	    	password = PasswordUtil.SENHA_GENERICA;
	    }

	    User user = new User(
	            form.getUser() == null ? 0 : form.getUser().getId(),
	            form.getUsername(),
	            password,
	            form.getFullName(),
	            form.getProfile(),
	            form.getSector(),
	            form.isActive()
	    );

	    try {
	        if (form.getUser() == null) {
	            dao.insert(user);
	        } else {
	            dao.update(user);
	        }

	        form.dispose();
	        loadUsers();

	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(form, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	    }
	}

	// ======================
	// CARREGAMENTO
	// ======================

	private void loadUsers() {

		DefaultTableModel model = view.getModel();
		model.setRowCount(0);

		List<User> users = dao.findAll();

		for (User u : users) {
			model.addRow(new Object[] { u.getId(), u.getUsername(), u.getName(), u.getProfile(), u.getSector() });
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

		return new User((int) table.getValueAt(row, 0), (String) table.getValueAt(row, 1),
				(String) table.getValueAt(row, 2), (String) table.getValueAt(row, 3), null // sector será carregado do
																							// banco depois
		);
	}
	
	private void resetPassword() {
	    User selected = getSelectedUser();

	    if (selected == null) {
	        JOptionPane.showMessageDialog(view, "Selecione um usuário.", "Atenção",
	                JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    int confirm = JOptionPane.showConfirmDialog(
	        view,
	        "Resetar a senha de \"" + selected.getName() + "\" para a senha genérica?",
	        "Confirmar Reset",
	        JOptionPane.YES_NO_OPTION
	    );

	    if (confirm == JOptionPane.YES_OPTION) {
	        boolean ok = dao.resetPassword(selected.getId());
	        if (ok) {
	            JOptionPane.showMessageDialog(view, "Senha resetada com sucesso.");
	        } else {
	            JOptionPane.showMessageDialog(view, "Erro ao resetar senha.", "Erro",
	                    JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}
}
