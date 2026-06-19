package br.com.sgp.auth;

import java.awt.Color;

import javax.swing.JOptionPane;

import br.com.sgp.dao.UserDAO;
import br.com.sgp.model.User;
import br.com.sgp.session.UserSession;
import br.com.sgp.util.ConnectionFactory;
import br.com.sgp.view.MainView;

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
		if (ConnectionFactory.testConnection()) {
			view.setStatus("Conectado", Color.BLUE, "/images/icons/dbOn-40x40.png");
		} else {
			view.setStatus("Banco não encontrado", Color.RED, "/images/icons/dbOff-40x40.png");

			JOptionPane.showMessageDialog(view,
					"<html><b>Não foi possível conectar ao banco de dados.</b><br><br>"
							+ "Verifique se o arquivo abaixo está na mesma pasta do programa:<br>" + "<tt>"
							+ ConnectionFactory.getDbPath() + "</tt><br><br>"
							+ "O sistema funcionará em modo limitado até que a conexão seja restabelecida.</html>",
					"Erro de conexão", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void login() {
		
		if (!ConnectionFactory.testConnection()) {
            JOptionPane.showMessageDialog(
                view,
                "<html>Sem conexão com o banco de dados.<br>"
                + "Verifique se o arquivo <b>db_production.accdb</b><br>"
                + "está na mesma pasta do programa.</html>",
                "Sem conexão",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

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

		// 🔐 cria sessão
		UserSession.getInstance().login(loggedUser);

		// Login OK
		view.dispose();
		new MainView().setVisible(true);
	}
}
