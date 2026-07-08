package br.com.sgp.view.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import br.com.sgp.session.UserSession;

/**
 * Painel lateral fixo (EAST) do MainView.
 * Exibe logo, área reservada para atalhos futuros e informações da sessão.
 */
public class SidebarPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int SIDEBAR_WIDTH = 240;

	private JLabel lblDateTime;

	public SidebarPanel() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(SIDEBAR_WIDTH, 0));
		setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY));

		add(createLogoPanel(), BorderLayout.NORTH);
		add(createShortcutsPanel(), BorderLayout.CENTER);
		add(createInfoCard(), BorderLayout.SOUTH);

		startClock();
	}

	// ================= LOGO =================
	private JPanel createLogoPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(16, 10, 16, 10));

		ImageIcon icon = new ImageIcon(
				getClass().getResource("/images/logos/lea3_logo-218x98.png"));
		Image scaled = icon.getImage().getScaledInstance(120, -1, Image.SCALE_SMOOTH);

		panel.add(new JLabel(new ImageIcon(scaled)));
		return panel;
	}

	// ================= ATALHOS (reservado) =================
	private JPanel createShortcutsPanel() {
		JPanel panel = new JPanel();
		// TODO: futuros botões de atalho serão adicionados aqui
		return panel;
	}

	// ================= INFO DO USUÁRIO =================
	private JPanel createInfoCard() {
		JPanel card = new JPanel();
		card.setLayout(new javax.swing.BoxLayout(card, javax.swing.BoxLayout.Y_AXIS));
		card.setBorder(new EmptyBorder(10, 12, 14, 12));

		UserSession session = UserSession.getInstance();

		card.add(sectionLabel("Usuário"));
		card.add(valueLabel(session.getName()));
		card.add(javax.swing.Box.createVerticalStrut(8));

		card.add(sectionLabel("Setor"));
		card.add(valueLabel(String.valueOf(session.getSector())));
		card.add(javax.swing.Box.createVerticalStrut(8));

		card.add(sectionLabel("Data"));
		lblDateTime = valueLabel(formatarDataHora());
		card.add(lblDateTime);

		return card;
	}

	private JLabel sectionLabel(String text) {
		JLabel lbl = new JLabel(text);
		lbl.setForeground(Color.GRAY);
		lbl.setFont(lbl.getFont().deriveFont(11f));
		lbl.setHorizontalAlignment(SwingConstants.LEFT);
		return lbl;
	}

	private JLabel valueLabel(String text) {
		JLabel lbl = new JLabel(text);
		lbl.setFont(lbl.getFont().deriveFont(java.awt.Font.BOLD, 12f));
		return lbl;
	}

	// ================= RELÓGIO =================
	private void startClock() {
		Timer timer = new Timer(1000, e -> lblDateTime.setText(
				"<html><div style='width:140px'>" + formatarDataHora() + "</div></html>"));
		timer.start();
	}
	
	private String formatarDataHora() {
		java.time.LocalDateTime now = java.time.LocalDateTime.now();

		java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter
				.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy - HH:mm", new java.util.Locale("pt", "BR"));

		String formatted = now.format(formatter);
		return capitalize(formatted);
	}

	private String capitalize(String text) {
		if (text == null || text.isEmpty()) {
			return text;
		}
		return Character.toUpperCase(text.charAt(0)) + text.substring(1);
	}
}