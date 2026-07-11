package br.com.sgp.view;

import java.awt.BorderLayout;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import br.com.sgp.view.component.SidebarPanel;
import br.com.sgp.controller.FabricacaoPecasController;
import br.com.sgp.controller.UserController;
import br.com.sgp.session.UserSession;
import br.com.sgp.util.AccessControl;
import br.com.sgp.util.AppRestarter;
import br.com.sgp.view.sector.FabricacaoPecasView;

public class MainView extends JFrame {

	private static final long serialVersionUID = 1L;

	private JDesktopPane desktopPane;

	public MainView() {
		setTitle("SGP - Sistema de Gestão da Produção");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		addWindowStateListener(e -> {
			if ((e.getNewState() & JFrame.MAXIMIZED_BOTH) != JFrame.MAXIMIZED_BOTH
					&& (e.getNewState() & JFrame.ICONIFIED) == 0) {
				setExtendedState(JFrame.MAXIMIZED_BOTH);
			}
		});

		setIconImage(new javax.swing.ImageIcon(
				getClass().getResource("/images/icons/sgp_icon.png")).getImage());
		
		setJMenuBar(createMenuBar());
		
		add(createDesktopPane(), BorderLayout.CENTER);

		setJMenuBar(createMenuBar());
		add(createDesktopPane(), BorderLayout.CENTER);

		SidebarPanel sidebar = new SidebarPanel();
		sidebar.getBtnFabricacaoPecas().addActionListener(e -> abrirFabricacaoPecas());
		sidebar.getBtnUsuarios().addActionListener(e -> abrirUsuarios());
		sidebar.getBtnFabricacaoVigas().addActionListener(e ->
				JOptionPane.showMessageDialog(this, "Módulo Fabricação de Vigas"));
		sidebar.getBtnRelatorios().addActionListener(e ->
				JOptionPane.showMessageDialog(this, "Módulo Relatórios"));
		add(sidebar, BorderLayout.EAST);
	}

	// ================= MENU =================
	private JMenuBar createMenuBar() {

		JMenuBar menuBar = new JMenuBar();

		JMenu menuStart = new JMenu("Início");

		JMenu menuSector = new JMenu("Setor / Área");
		JMenuItem itemUsers = new JMenuItem("Usuários");

		// ===== Fabricação de Peças
		JMenu menuFabricacaoPecas = new JMenu("Fabricação de Peças");
		JMenuItem itemFabricacaoPecas = new JMenuItem("Fabricação de Peças");
		JMenuItem itemCorteDobra = new JMenuItem("Corte e Dobra");

		// ===== Fabricação de Vigas
		JMenu menuFabricacaoVigas = new JMenu("Fabricação de Vigas");
		JMenuItem itemMontagemVigas = new JMenuItem("Montagem de Vigas");
		JMenuItem itemSoldaVigas = new JMenuItem("Solda de Vigas");
		JMenuItem itemSoldaPescoco = new JMenuItem("Solda Pescoço");

		JMenu menuReports = new JMenu("Relatórios");
		JMenu menuHelp = new JMenu("Ajuda");

		// =====================
		// AÇÕES
		// =====================

		itemUsers.addActionListener(e -> abrirUsuarios());

		itemFabricacaoPecas.addActionListener(e -> abrirFabricacaoPecas());

		itemCorteDobra.addActionListener(e -> JOptionPane.showMessageDialog(this, "Módulo Corte e Dobra"));

		itemMontagemVigas.addActionListener(e -> JOptionPane.showMessageDialog(this, "Módulo Montagem de Vigas"));

		itemSoldaVigas.addActionListener(e -> JOptionPane.showMessageDialog(this, "Módulo Solda de Vigas"));

		itemSoldaPescoco.addActionListener(e -> JOptionPane.showMessageDialog(this, "Módulo Solda Pescoço"));

		// =====================
		// MONTAGEM DO MENU
		// =====================
		menuSector.add(itemUsers);
		menuSector.addSeparator();

		menuSector.add(menuFabricacaoPecas);
		menuFabricacaoPecas.add(itemFabricacaoPecas);
		menuFabricacaoPecas.add(itemCorteDobra);

		menuSector.add(menuFabricacaoVigas);
		menuFabricacaoVigas.add(itemMontagemVigas);
		menuFabricacaoVigas.add(itemSoldaVigas);
		menuFabricacaoVigas.add(itemSoldaPescoco);

		JMenuItem itemAbout = new JMenuItem("Sobre");
		itemAbout.addActionListener(e -> new AboutDialog(this).setVisible(true));

		JMenuItem itemLogout = new JMenuItem("Sair");
		itemLogout.addActionListener(e -> logout());

		menuHelp.add(itemAbout);
		menuHelp.addSeparator();
		menuHelp.add(itemLogout);
		menuBar.add(menuStart);
		menuBar.add(menuSector);
		menuBar.add(menuReports);
		menuBar.add(menuHelp);

		applyAccessControl(itemUsers, menuFabricacaoPecas, menuFabricacaoVigas, menuReports);

		return menuBar;
	}

	private void applyAccessControl(JMenuItem itemUsers, JMenu menuFabricacaoPecas, JMenu menuFabricacaoVigas,
			JMenu menuReports) {

		// Usuários — apenas ADMIN
		itemUsers.setVisible(AccessControl.isAdmin());

		// Fabricação de Peças — ADMIN ou setor FABRICACAO_PECAS
		menuFabricacaoPecas.setVisible(AccessControl.hasSectorAccess(AccessControl.FABRICACAO_PECAS));

		// Fabricação de Vigas — ADMIN ou setor FABRICACAO_VIGAS
		menuFabricacaoVigas.setVisible(AccessControl.hasSectorAccess(AccessControl.FABRICACAO_VIGAS));

		// Relatórios — ADMIN ou GESTOR
		menuReports.setVisible(AccessControl.isGestor());
	}

	// ================= ÁREA CENTRAL =================
	private JDesktopPane createDesktopPane() {
		desktopPane = new JDesktopPane();
		desktopPane.setBackground(new java.awt.Color(245, 247, 250));
		return desktopPane;
	}

	// ================= GETTER =================
	public JDesktopPane getDesktopPane() {
		return desktopPane;
	}

	private void centralizar(JInternalFrame frame) {
		int x = (desktopPane.getWidth() - frame.getWidth()) / 2;
		int y = (desktopPane.getHeight() - frame.getHeight()) / 2;
		frame.setLocation(x, y);
	}
	
	private void abrirUsuarios() {
		UserView view = new UserView();
		new UserController(view);
		desktopPane.add(view);
		view.setVisible(true);
	}

	private void abrirFabricacaoPecas() {
		FabricacaoPecasView view = new FabricacaoPecasView();
		new FabricacaoPecasController(view);
		desktopPane.add(view);
		view.setVisible(true);
		centralizar(view);
		try {
			view.setSelected(true);
		} catch (java.beans.PropertyVetoException ex) {
			ex.printStackTrace();
		}
	}

	private void logout() {
		int confirm = JOptionPane.showConfirmDialog(this, "Deseja sair e voltar à tela de login?", "Confirmar saída",
				JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			UserSession.getInstance().logout();
			AppRestarter.restart(this);
			;
		}
	}
}
