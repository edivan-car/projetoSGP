package br.com.sgp.view.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import br.com.sgp.session.UserSession;
import br.com.sgp.util.AccessControl;
import br.com.sgp.view.util.AppColors;

/**
 * Painel lateral fixo (EAST) do MainView.
 * Exibe logo, atalhos de navegação e informações da sessão.
 */
public class SidebarPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int SIDEBAR_WIDTH = 260;

	private JLabel lblDateTime;

	private JButton btnCorteTermico;
	private JButton btnCorteDobra;
	private JButton btnFabricacaoVigas;
	private JButton btnUsuarios;
	private JButton btnRelatorios;

	public SidebarPanel() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(SIDEBAR_WIDTH, 0));
		setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, AppColors.ACCENT));

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
				getClass().getResource("/images/logos/logo_sgp.png"));
		Image scaled = icon.getImage().getScaledInstance(120, -1, Image.SCALE_SMOOTH);

		panel.add(new JLabel(new ImageIcon(scaled)));
		return panel;
	}

	// ================= ATALHOS =================
	private JPanel createShortcutsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(10, 14, 10, 14));

		btnCorteTermico = criarBotaoAtalho("Corte Térmico");
		btnCorteDobra = criarBotaoAtalho("Corte e Dobra");
		btnFabricacaoVigas = criarBotaoAtalho("Fabricação de Vigas");
		btnUsuarios        = criarBotaoAtalho("Usuários");
		btnRelatorios      = criarBotaoAtalho("Relatórios");

		panel.add(btnCorteTermico);
		panel.add(Box.createVerticalStrut(8));
		panel.add(btnCorteDobra);
		panel.add(Box.createVerticalStrut(8));
		panel.add(btnFabricacaoVigas);
		panel.add(Box.createVerticalStrut(8));
		panel.add(btnUsuarios);
		panel.add(Box.createVerticalStrut(8));
		panel.add(btnRelatorios);

		applyAccessControl();

		return panel;
	}

	private JButton criarBotaoAtalho(String texto) {
		JButton button = new JButton(texto);
		AppColors.style(button, AppColors.ACCENT);
		button.setAlignmentX(LEFT_ALIGNMENT);
		button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
		return button;
	}

	private void applyAccessControl() {
		btnUsuarios.setVisible(AccessControl.isAdmin());
		btnCorteTermico.setVisible(AccessControl.hasSectorAccess(AccessControl.FABRICACAO_PECAS));
		btnCorteDobra.setVisible(AccessControl.hasSectorAccess(AccessControl.FABRICACAO_PECAS));
		btnFabricacaoVigas.setVisible(AccessControl.hasSectorAccess(AccessControl.FABRICACAO_VIGAS));
		btnRelatorios.setVisible(AccessControl.isGestor());
	}

	// ================= INFO DO USUÁRIO =================
	private JPanel createInfoCard() {
		JPanel card = new JPanel();
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
		card.setBorder(new EmptyBorder(10, 12, 14, 12));

		UserSession session = UserSession.getInstance();

		card.add(sectionLabel("Usuário"));
		card.add(valueLabel(session.getName()));
		card.add(Box.createVerticalStrut(8));

		card.add(sectionLabel("Setor"));
		card.add(valueLabel(String.valueOf(session.getSector())));
		card.add(Box.createVerticalStrut(8));

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
		lbl.setForeground(AppColors.ACCENT);
		return lbl;
	}

	// ================= RELÓGIO =================
	private void startClock() {
		Timer timer = new Timer(1000, e -> lblDateTime.setText(
				"<html><div style='width:210px'>" + formatarDataHora() + "</div></html>"));
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

	// ================= GETTERS (usados pelo MainView) =================
	public JButton getBtnCorteTermico() {
		return btnCorteTermico;
	}
	
	public JButton getBtnCorteDobra() {
		return btnCorteDobra;
	}

	public JButton getBtnFabricacaoVigas() {
		return btnFabricacaoVigas;
	}

	public JButton getBtnUsuarios() {
		return btnUsuarios;
	}

	public JButton getBtnRelatorios() {
		return btnRelatorios;
	}
}