package br.com.sgp.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;

import br.com.sgp.controller.UserController;
import br.com.sgp.session.UserSession;

public class MainView extends JFrame {

    private JDesktopPane desktopPane;
    private JLabel lblUser;
    private JLabel lblSector;
    private JLabel lblDateTime;

    public MainView() {
        setTitle("SGP - Sistema de Gestão da Produção");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🔹 Tamanho base padronizado
        setSize(1200, 800);
        setMinimumSize(new Dimension(1024, 700));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setJMenuBar(createMenuBar());
        add(createDesktopPane(), BorderLayout.CENTER);
        add(createStatusBar(), BorderLayout.SOUTH);
    }

    // ================= MENU =================
    private JMenuBar createMenuBar() {

        JMenuBar menuBar = new JMenuBar();

        JMenu menuStart = new JMenu("Início");
        JMenu menuSector = new JMenu("Setor / Área");
        JMenu menuReports = new JMenu("Relatórios");
        JMenu menuHelp = new JMenu("Ajuda");
        
        // =====================
        // MENU SETOR / ÁREA
        // =====================
        JMenuItem itemUsers = new JMenuItem("Usuários");

        itemUsers.addActionListener(e -> {
            UserView view = new UserView();
            new UserController(view);
            desktopPane.add(view);
            view.setVisible(true);
        });

        menuSector.add(itemUsers);

        // =====================
        // MENU AJUDA
        // =====================
        JMenuItem itemAbout = new JMenuItem("Sobre");
        itemAbout.addActionListener(e ->
                new AboutDialog(this).setVisible(true)
        );

        menuHelp.add(itemAbout);

        // =====================
        // ADD MENUS NA BARRA
        // =====================
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
                            .format(java.time.format.DateTimeFormatter.ofPattern(
                                    "dd/MM/yyyy HH:mm:ss"
                            ))
            );
        });
        timer.start();
    }

    // ================= GETTERS =================
    public JDesktopPane getDesktopPane() {
        return desktopPane;
    }
}
