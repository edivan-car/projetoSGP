package br.com.sgp.auth;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import br.com.sgp.dao.UserDAO;
import br.com.sgp.model.User;
import br.com.sgp.session.UserSession;
import br.com.sgp.util.ConnectionFactory;
import br.com.sgp.util.PasswordUtil;
import br.com.sgp.view.MainView;

public class LoginController {

	private LoginView view;
	private UserDAO userDAO;

	public LoginController(LoginView view, UserDAO userDAO) {
		this.view = view;
		this.userDAO = userDAO;
		initController();
		checkDatabase();
	}

	private void initController() {
		view.getBtnLogin().addActionListener(e -> login());
	}

	private void checkDatabase() {
	    view.setStatus("Verificando conexão...", Color.GRAY, "/images/icons/dbOff-40x40.png");

	    new Thread(() -> {
	        try {
	            Thread.sleep(800);
	        } catch (InterruptedException ex) {
	            Thread.currentThread().interrupt();
	        }

	        boolean connected = ConnectionFactory.testConnection();

	        javax.swing.SwingUtilities.invokeLater(() -> {
	            if (connected) {
	                view.setStatus("Conectado", Color.BLUE, "/images/icons/dbOn-40x40.png");
	            } else {
	                view.setStatus("Banco não encontrado", Color.RED, "/images/icons/dbOff-40x40.png");
	                JOptionPane.showMessageDialog(
	                    view,
	                    "<html><b>Não foi possível conectar ao banco de dados.</b><br><br>"
	                    + "Verifique se o arquivo abaixo está na mesma pasta do programa:<br>"
	                    + "<tt>" + ConnectionFactory.getDbPath() + "</tt><br><br>"
	                    + "O sistema funcionará em modo limitado até que a conexão seja restabelecida.</html>",
	                    "Erro de conexão",
	                    JOptionPane.ERROR_MESSAGE
	                );
	            }
	        });
	    }).start();
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
			view.clearCredentials();
			return;
		}
		
		// verifica primeiro acesso
		String hashAtual = userDAO.getPasswordHash(loggedUser.getId());
		if (PasswordUtil.isPrimeiroAcesso(hashAtual)) {
		    boolean trocou = solicitarTrocaDeSenha(loggedUser);
		    if (!trocou) {
		        return; // usuário cancelou, bloqueia acesso
		    }
		}  

		// 🔐 cria sessão
		UserSession.getInstance().login(loggedUser);

		// Login OK
		view.dispose();
		new MainView().setVisible(true);
	}
	
	private boolean solicitarTrocaDeSenha(User user) {

	    JPanel panel = new JPanel(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(5, 5, 5, 5);
	    gbc.fill = GridBagConstraints.HORIZONTAL;

	    gbc.gridx = 0; gbc.gridy = 0;
	    panel.add(new JLabel("Nova senha (mínimo 6 caracteres):"), gbc);

	    gbc.gridy = 1;
	    JPasswordField txtNova = new JPasswordField(20);
	    panel.add(txtNova, gbc);

	    gbc.gridy = 2;
	    panel.add(new JLabel("Confirmar nova senha:"), gbc);

	    gbc.gridy = 3;
	    JPasswordField txtConfirma = new JPasswordField(20);
	    panel.add(txtConfirma, gbc);

	    while (true) {
	        int result = JOptionPane.showConfirmDialog(
	            view,
	            panel,
	            "Primeiro acesso — troque sua senha",
	            JOptionPane.OK_CANCEL_OPTION,
	            JOptionPane.WARNING_MESSAGE
	        );

	        if (result != JOptionPane.OK_OPTION) {
	            JOptionPane.showMessageDialog(view,
	                "É necessário trocar a senha no primeiro acesso.");
	            return false;
	        }

	        String nova     = new String(txtNova.getPassword()).trim();
	        String confirma = new String(txtConfirma.getPassword()).trim();

	        if (nova.length() < 6) {
	            JOptionPane.showMessageDialog(view, "A senha deve ter no mínimo 6 caracteres.");
	            continue;
	        }

	        if (!nova.equals(confirma)) {
	            JOptionPane.showMessageDialog(view, "As senhas não coincidem.");
	            continue;
	        }

	        boolean updated = userDAO.updatePassword(user.getId(), nova);

	        if (!updated) {
	            JOptionPane.showMessageDialog(
	                view,
	                "Não foi possível alterar a senha.",
	                "Erro",
	                JOptionPane.ERROR_MESSAGE
	            );
	            return false;
	        }

	        JOptionPane.showMessageDialog(
	            view,
	            "Senha alterada com sucesso!"
	        );
	        return true;
	    }
	}
}
