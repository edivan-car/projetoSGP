package br.com.sgp.auth;

import java.awt.Color;

import br.com.sgp.dao.UserDAO;
import br.com.sgp.model.User;
import br.com.sgp.util.AccessConnection;
import br.com.sgp.view.MainFrame;

public class LoginController {

	private LoginView view;
	private UserDAO userDAO = new UserDAO();

	public LoginController(LoginView view) {
		this.view = view;
		initController();
		checkDatabase();
	}

	private void initController() {
		view.getBtnLogin().addActionListener(e -> login());
	}

	private void checkDatabase() {
		if (AccessConnection.testConnection()) {
			view.setStatus("Conectado", Color.BLUE, "/images/icons/dbOn-40x40.png");
		} else {
			view.setStatus("Erro de conexão", Color.RED, "/images/icons/dbOff-40x40.png");
		}
	}

	private void login() {

		String user = view.getUser();
		String pass = String.valueOf(view.getPassword());

		if (user.isEmpty() || pass.isEmpty()) {
			view.setStatusUser("Informe usuário e senha", Color.RED);
			return;
		}

		User loggedUser = userDAO.login(user, pass);

		if (loggedUser == null) {
			view.setStatusUser("Usuário ou senha inválidos", Color.RED);
			return;
		}

		// Login OK
		view.dispose();
		new MainFrame().setVisible(true);
	}
}
