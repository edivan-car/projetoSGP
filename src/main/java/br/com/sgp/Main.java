package br.com.sgp;

import br.com.sgp.view.MainFrame;

public class Main {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
		});
	}
}
