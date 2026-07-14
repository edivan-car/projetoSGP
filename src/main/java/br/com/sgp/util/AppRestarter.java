package br.com.sgp.util;

import java.io.File;

import javax.swing.SwingUtilities;

import br.com.sgp.dao.UserDAO;
import br.com.sgp.auth.LoginController;
import br.com.sgp.auth.LoginView;

import javax.swing.JFrame;

public final class AppRestarter {

    private AppRestarter() {}

    public static void restart(JFrame mainFrame) {
        try {
            String jarPath = new File(
                AppRestarter.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()
            ).getAbsolutePath();

            // se termina em .jar, está rodando o executável — faz restart real
            if (jarPath.endsWith(".jar")) {
                String java = System.getProperty("java.home")
                              + File.separator + "bin"
                              + File.separator + "java";

                new ProcessBuilder(java, "-jar", jarPath)
                    .inheritIO()
                    .start();

                System.exit(0);

            } else {
                // rodando pelo Eclipse — fallback: abre nova tela de login
                SwingUtilities.invokeLater(() -> {
                	mainFrame.dispose();
                    LoginView view = new LoginView();
                    new LoginController(view, new UserDAO());
                    view.setVisible(true);
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}