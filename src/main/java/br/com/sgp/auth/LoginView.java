package br.com.sgp.auth;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

public class LoginView extends JFrame {
	private static final long serialVersionUID = 1L;
	
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
        JLabel lblUserIcon = new JLabel(
                new ImageIcon(getClass().getResource("/images/icons/users_156x156.png")));
        lblUserIcon.setBounds(162, 40, 156, 156);
        lblBackground.add(lblUserIcon);
    }

    private void addUserField() {
        JLabel lblUser = new JLabel("Usuário");
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblUser.setHorizontalAlignment(SwingConstants.CENTER);
        lblUser.setBounds(140, 226, 200, 20);
        lblBackground.add(lblUser);

        txtUser = new JTextField("admin");
        txtUser.setHorizontalAlignment(SwingConstants.CENTER);
        txtUser.setBounds(140, 256, 200, 20);
        lblBackground.add(txtUser);
    }

    private void addPasswordField() {
        JLabel lblPass = new JLabel("Senha");
        lblPass.setForeground(Color.WHITE);
        lblPass.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPass.setHorizontalAlignment(SwingConstants.CENTER);
        lblPass.setBounds(140, 296, 200, 20);
        lblBackground.add(lblPass);

        txtPass = new JPasswordField("admin@2486");
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
