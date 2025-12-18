package br.com.sgp.auth;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginFrame extends JFrame {

	private JLabel lblBackground;

	public LoginFrame() {
		configurarJanela();
		configurarBackground();
		adicionarIconeUsuario();
		adicionarCampoUsuario();
		adicionarCampoSenha();
		adicionarBotaoLogin();
	}

	private void configurarJanela() {
		setTitle("SGP - Login");
		setSize(480, 520);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void configurarBackground() {
		lblBackground = new JLabel(new ImageIcon(getClass().getResource("/images/background/backLogin_480x520.jpg")));

		// configurar JLabel como background
		setContentPane(lblBackground);

		// Layout null para posicionamento manual
		lblBackground.setLayout(null);
	}

	private void adicionarIconeUsuario() {
		JLabel lblUsuarioIcone = new JLabel(new ImageIcon(getClass().getResource("/images/icons/users_156x156.png")));

		// Centralizar manualmente
		lblUsuarioIcone.setBounds(162, 40, 156, 156);

		lblBackground.add(lblUsuarioIcone);
	}

	private void adicionarCampoUsuario() {

		JLabel lblUsuario = new JLabel("Usuário");
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setBounds(140, 226, 200, 20);
		lblBackground.add(lblUsuario);

		JTextField txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		txtUsuario.setBounds(140, 256, 200, 20);
		lblBackground.add(txtUsuario);
	}

	private void adicionarCampoSenha() {

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setForeground(Color.WHITE);
		lblSenha.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSenha.setHorizontalAlignment(SwingConstants.CENTER);
		lblSenha.setBounds(140, 296, 200, 20);
		lblBackground.add(lblSenha);

		JPasswordField txtSenha = new JPasswordField();
		txtSenha.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtSenha.setHorizontalAlignment(SwingConstants.CENTER);
		txtSenha.setBounds(140, 326, 200, 20);
		lblBackground.add(txtSenha);
	}

	private void adicionarBotaoLogin() {

		JButton btnLogin = new JButton("Entrar");
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnLogin.setHorizontalAlignment(SwingConstants.CENTER);
		btnLogin.setBounds(190, 366, 100, 40);
		lblBackground.add(btnLogin);

		btnLogin.addActionListener(e -> {
			System.out.println("Botão Login clicado");
		});
	}
}
