package br.com.sgp.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame {

    public MainFrame() {
    	setTitle("SGP - Sistema de Gestão da Produção");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Bem-vindo ao SGP");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        add(label);
    }
}
