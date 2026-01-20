package br.com.sgp.auth;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

public class LoginView extends JFrame {

    private JLabel lblBackground;
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;
    private JLabel lblStatus;
    private JLabel lblStatusUser;
    private JLabel lblConnectDB;

    public LoginView() {
        setupFrame();
        setupBackground();
        addUserIcon();
        addUserField();
        addPasswordField();
        addLoginButton();
        addStatus();
        addStatusUser();
        addConnectDB();
    }

    private void setupFrame() {
        setTitle("SGP - Login");
        setSize(480, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void setupBackground() {
        lblBackground = new JLabel(new ImageIcon(
                getClass().getResource("/images/background/backLogin_480x520.jpg")));
        setContentPane(lblBackground);
        lblBackground.setLayout(null);
    }

    private void addUserIcon() {
        JLabel lblUsuarioIcone = new JLabel(
                new ImageIcon(getClass().getResource("/images/icons/users_156x156.png")));
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
        txtPass.setHorizontalAlignment(SwingConstants.CENTER);
        txtPass.setBounds(140, 326, 200, 20);
        lblBackground.add(txtPass);
    }

    private void addLoginButton() {
        btnLogin = new JButton("Entrar");
        btnLogin.setBounds(190, 366, 100, 30);
        lblBackground.add(btnLogin);
    }

    private void addStatus() {
        lblStatus = new JLabel("Verificando conexão...");
        lblStatus.setHorizontalAlignment(SwingConstants.RIGHT);
        lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 10));
        lblStatus.setBounds(280, 450, 150, 20);
        //lblStatus.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        lblBackground.add(lblStatus);
    }
    
    private void addStatusUser() {
    	lblStatusUser = new JLabel("");
    	lblStatusUser.setHorizontalAlignment(SwingConstants.CENTER);
    	lblStatusUser.setFont(new Font("Tahoma", Font.PLAIN, 10));
    	lblStatusUser.setBounds(140, 406, 200, 20);
    	//lblStatusUser.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        lblBackground.add(lblStatusUser);
    }

    private void addConnectDB() {
        lblConnectDB = new JLabel(
                new ImageIcon(getClass().getResource("/images/icons/dbOff-40x40.png")));
        lblConnectDB.setBounds(410, 410, 40, 40);
        lblBackground.add(lblConnectDB);
    }

    // 🔹 Getters usados pelo Controller
    public JButton getBtnLogin() {
        return btnLogin;
    }

    public String getUser() {
        return txtUser.getText();
    }

    public char[] getPassword() {
        return txtPass.getPassword();
    }

    public void setStatus(String text, Color color, String iconPath) {
        lblStatus.setText(text);
        lblStatus.setForeground(color);
        lblConnectDB.setIcon(new ImageIcon(getClass().getResource(iconPath)));
    }
    
    public void setStatusUser(String text, Color color) {
        lblStatusUser.setText(text);
        lblStatusUser.setForeground(color);
    }
}
