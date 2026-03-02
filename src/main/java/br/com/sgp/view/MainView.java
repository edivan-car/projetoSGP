package br.com.sgp.view;

import java.awt.BorderLayout;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import br.com.sgp.controller.DefaultSectorController;
import br.com.sgp.controller.PlasmaCuttingController;
import br.com.sgp.controller.ThermalCuttingController;
import br.com.sgp.controller.UserController;
import br.com.sgp.session.UserSession;
import br.com.sgp.view.sector.DefaultSectorView;
import br.com.sgp.view.sector.form.PlasmaCuttingForm;
import br.com.sgp.view.sector.form.ThermalCuttingForm;

public class MainView extends JFrame {

    private static final long serialVersionUID = 1L;

    private JDesktopPane desktopPane;
    private JLabel lblUser;
    private JLabel lblSector;
    private JLabel lblDateTime;

    public MainView() {
        setTitle("SGP - Sistema de Gestão da Produção");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setJMenuBar(createMenuBar());
        add(createDesktopPane(), BorderLayout.CENTER);
        add(createStatusBar(), BorderLayout.SOUTH);
    }

    // ================= MENU =================
    private JMenuBar createMenuBar() {

        JMenuBar menuBar = new JMenuBar();

        JMenu menuStart = new JMenu("Início");

        JMenu menuSector = new JMenu("Setor / Área");
        JMenuItem itemUsers = new JMenuItem("Usuários");

        // ===== Fabricação de Peças
        JMenu menuFabricacaoPecas = new JMenu("Fabricação de Peças");
        JMenuItem itemCorteTermico = new JMenuItem("Corte Térmico");
        JMenuItem itemCortePlasma = new JMenuItem("Corte a Plasma");
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

        itemUsers.addActionListener(e -> {
            UserView view = new UserView();
            new UserController(view);
            desktopPane.add(view);
            view.setVisible(true);
        });

        itemCorteTermico.addActionListener(e -> {

            DefaultSectorView view = new DefaultSectorView("Corte Térmico", "THERMAL_CUTTING");
            
            desktopPane.add(view);
            view.setVisible(true);
            centralizar(view);
            
            ThermalCuttingForm form = new ThermalCuttingForm();
            view.setForm(form);
            
            // Controller genérico do setor (tabela, seleção, etc.)
            new DefaultSectorController(view);
            
            // ✅ Controller específico do Corte Térmico
            new ThermalCuttingController(form);

            try {
				view.setSelected(true);
			} catch (java.beans.PropertyVetoException ex) {
				ex.printStackTrace();
			}
        });
        
        itemCortePlasma.addActionListener(e -> {

            DefaultSectorView view = new DefaultSectorView("Corte a Plasma", "PLASMA_CUTTING");
            
            desktopPane.add(view);
            view.setVisible(true);
            centralizar(view);
            
            PlasmaCuttingForm form = new PlasmaCuttingForm();
            view.setForm(form);
            
            // Controller genérico do setor (tabela, seleção, etc.)
            new DefaultSectorController(view);
            
            // ✅ Controller específico do Corte a Plasma
            new PlasmaCuttingController(form);

            try {
				view.setSelected(true);
			} catch (java.beans.PropertyVetoException ex) {
				ex.printStackTrace();
			}
        });

        itemCorteDobra.addActionListener(
                e -> JOptionPane.showMessageDialog(this, "Módulo Corte e Dobra")
        );

        itemMontagemVigas.addActionListener(
                e -> JOptionPane.showMessageDialog(this, "Módulo Montagem de Vigas")
        );

        itemSoldaVigas.addActionListener(
                e -> JOptionPane.showMessageDialog(this, "Módulo Solda de Vigas")
        );

        itemSoldaPescoco.addActionListener(
                e -> JOptionPane.showMessageDialog(this, "Módulo Solda Pescoço")
        );

        // =====================
        // MONTAGEM DO MENU
        // =====================
        menuSector.add(itemUsers);
        menuSector.addSeparator();

        menuSector.add(menuFabricacaoPecas);
        menuFabricacaoPecas.add(itemCorteTermico);
        menuFabricacaoPecas.add(itemCortePlasma);
        menuFabricacaoPecas.add(itemCorteDobra);

        menuSector.add(menuFabricacaoVigas);
        menuFabricacaoVigas.add(itemMontagemVigas);
        menuFabricacaoVigas.add(itemSoldaVigas);
        menuFabricacaoVigas.add(itemSoldaPescoco);

        JMenuItem itemAbout = new JMenuItem("Sobre");
        itemAbout.addActionListener(e -> new AboutDialog(this).setVisible(true));
        menuHelp.add(itemAbout);

        menuBar.add(menuStart);
        menuBar.add(menuSector);
        menuBar.add(menuReports);
        menuBar.add(menuHelp);

        return menuBar;
    }

    // ================= ÁREA CENTRAL =================
    private JDesktopPane createDesktopPane() {
        desktopPane = new JDesktopPane();
        return desktopPane;
    }

    // ================= STATUS BAR =================
    private JPanel createStatusBar() {

        JPanel statusBar = new JPanel(new BorderLayout());

        UserSession session = UserSession.getInstance();

        lblUser = new JLabel("Usuário: " + session.getName());
        lblSector = new JLabel("Setor: " + session.getSector());
        lblDateTime = new JLabel();

        JPanel left = new JPanel();
        left.add(lblUser);
        left.add(new JLabel("|"));
        left.add(lblSector);

        JPanel right = new JPanel();
        right.add(lblDateTime);

        statusBar.add(left, BorderLayout.WEST);
        statusBar.add(right, BorderLayout.EAST);

        startClock();

        return statusBar;
    }

    // ================= RELÓGIO =================
    private void startClock() {
        Timer timer = new Timer(1000, e -> {
            lblDateTime.setText(
                java.time.LocalDateTime.now()
                    .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
            );
        });
        timer.start();
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
}
