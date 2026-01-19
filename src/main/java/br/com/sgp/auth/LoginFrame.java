package br.com.sgp.auth;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import br.com.sgp.util.AccessConnection;
import br.com.sgp.view.MainFrame;

public class LoginFrame extends JFrame {

	private JLabel lblBackground;
	private JTextField txtUser;
	private JPasswordField txtPass;
	private JLabel lblConnectDB;
	private JLabel lblStatus;

	public LoginFrame() {
		setupFrame();
		setupBackground();
		addUserIcon();
		addUserField();
		addPasswordField();
		addLoginButton();
		addConnectDB();

		testDatabaseConnection();
	}

	private void testDatabaseConnection() {

		new SwingWorker<Boolean, Void>() {

			@Override
			protected Boolean doInBackground() {
				return AccessConnection.testConnection();
			}

			@Override
			protected void done() {
				try {
					boolean connected = get();

					if (connected) {
						lblConnectDB.setIcon(new ImageIcon(getClass().getResource("/images/icons/dbOn-40x40.png")));
						lblStatus.setText("Conectado");
						lblStatus.setForeground(Color.BLUE);
					} else {
						lblConnectDB.setIcon(new ImageIcon(getClass().getResource("/images/icons/dbOff-40x40.png")));
						lblStatus.setText("Erro de conexão");
						lblStatus.setForeground(Color.RED);
					}

				} catch (Exception e) {
					lblStatus.setText("Erro de conexão");
					lblStatus.setForeground(Color.RED);
				}
			}
		}.execute();
	}

	private void setupFrame() {
		setTitle("SGP - Login");
		setSize(480, 520);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void setupBackground() {
		lblBackground = new JLabel(new ImageIcon(getClass().getResource("/images/background/backLogin_480x520.jpg")));

		// configurar JLabel como background
		setContentPane(lblBackground);

		// Layout null para posicionamento manual
		lblBackground.setLayout(null);
	}

	private void addUserIcon() {
		JLabel lblUsuarioIcone = new JLabel(new ImageIcon(getClass().getResource("/images/icons/users_156x156.png")));

		// Centralizar manualmente
		lblUsuarioIcone.setBounds(162, 40, 156, 156);

		lblBackground.add(lblUsuarioIcone);
	}

	private void addUserField() {

		JLabel lblUsuario = new JLabel("Usuário");
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setBounds(140, 226, 200, 20);
		lblBackground.add(lblUsuario);

		txtUser = new JTextField();
		txtUser.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtUser.setHorizontalAlignment(SwingConstants.CENTER);
		txtUser.setBounds(140, 256, 200, 20);
		lblBackground.add(txtUser);
	}

	private void addPasswordField() {

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setForeground(Color.WHITE);
		lblSenha.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSenha.setHorizontalAlignment(SwingConstants.CENTER);
		lblSenha.setBounds(140, 296, 200, 20);
		lblBackground.add(lblSenha);

		txtPass = new JPasswordField();
		txtPass.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtPass.setHorizontalAlignment(SwingConstants.CENTER);
		txtPass.setBounds(140, 326, 200, 20);
		lblBackground.add(txtPass);
	}

	private void addConnectDB() {
		lblConnectDB = new JLabel(new ImageIcon(getClass().getResource("/images/icons/dbOff-40x40.png")));
		lblConnectDB.setBounds(410, 410, 40, 40);
		lblBackground.add(lblConnectDB);

		lblStatus = new JLabel("Verificando...");
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblStatus.setForeground(Color.GRAY);
		lblStatus.setBounds(350, 450, 120, 20);
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblBackground.add(lblStatus);
	}

	private void addLoginButton() {

		JButton btnLogin = new JButton("Entrar");
	    btnLogin.setFont(new Font("Tahoma", Font.BOLD, 12));
	    btnLogin.setBounds(190, 366, 100, 40);
	    lblBackground.add(btnLogin);

	    btnLogin.addActionListener(e -> {

	        // 1️ - Verifica conexão com o banco
	        if (!AccessConnection.testConnection()) {
	            JOptionPane.showMessageDialog(
	                this,
	                "Não foi possível conectar ao banco de dados.",
	                "Erro",
	                JOptionPane.ERROR_MESSAGE
	            );
	            return;
	        }

	        // 2️ - Recupera dados
	        String user = txtUser.getText();
	        char[] pass = txtPass.getPassword();

	        // 3️ - Validação simples (mock por enquanto)
	        if ("admin".equals(user) && String.valueOf(pass).equals("123")) {

	            // 4️ - Abre tela principal
	            SwingUtilities.invokeLater(() -> {
	                new MainFrame().setVisible(true);
	            });

	            // 5️ - Fecha login
	            dispose();

	        } else {
	            JOptionPane.showMessageDialog(
	                this,
	                "Usuário ou senha inválidos.",
	                "Login inválido",
	                JOptionPane.WARNING_MESSAGE
	            );
	        }

	        Arrays.fill(pass, '\0');
	    });
	}
}
