package br.com.sgp.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

import br.com.sgp.model.User;
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

        JMenuItem itemAbout = new JMenuItem("Sobre");
        itemAbout.addActionListener(e ->
                new AboutDialog(this).setVisible(true)
        );

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

        User user = UserSession.getInstance().getUser();

        String userName = user != null ? user.getName() : "Desconhecido";
        String sector = user != null ? user.getSector() : "-";

        lblUser = new JLabel("Usuário: " + userName);
        lblSector = new JLabel("Setor: " + sector);
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
