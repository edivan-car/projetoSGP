package br.com.sgp.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.time.Year;

import javax.swing.*;

public class AboutDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	public AboutDialog(JFrame parent) {
        super(parent, "Sobre", true);

        setSize(420, 260);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BorderLayout());

        add(createContent(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createContent() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel("SGP - Sistema de Gestão da Produção");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitle.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblVersion = new JLabel("Versão 0.1.0");
        lblVersion.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblAuthor = new JLabel("Desenvolvido por: Edivan Cardoso");
        lblAuthor.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblLicense = new JLabel("Licenciado sob a MIT License");
        lblLicense.setAlignmentX(CENTER_ALIGNMENT);
        
        JLabel lblYear = new JLabel("© " + Year.now().getValue());
        lblYear.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblYear.setAlignmentX(CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(15));
        panel.add(lblTitle);
        panel.add(Box.createVerticalStrut(8));
        panel.add(lblVersion);
        panel.add(Box.createVerticalStrut(15));
        panel.add(lblAuthor);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblLicense);
        panel.add(Box.createVerticalStrut(15));
        panel.add(lblYear);

        return panel;
    }

    private JPanel createButtonPanel() {
    	JPanel panel = new JPanel();

        // Espaçamento interno (top, left, bottom, right)
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));

        JButton btnClose = new JButton("Fechar");
        btnClose.addActionListener(e -> dispose());

        panel.add(btnClose);
        return panel;
    }
}
