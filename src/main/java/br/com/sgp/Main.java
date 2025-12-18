package br.com.sgp;

import javax.swing.SwingUtilities;

import br.com.sgp.auth.LoginFrame;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
		});
	}
}
