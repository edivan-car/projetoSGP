package br.com.sgp;

import javax.swing.SwingUtilities;

import br.com.sgp.auth.LoginController;
import br.com.sgp.auth.LoginView;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView view = new LoginView();
            new LoginController(view);
            view.setVisible(true);
        });
    }
}
