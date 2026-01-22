package br.com.sgp.session;

import br.com.sgp.model.User;

public class UserSession {

    private static UserSession instance;

    private User loggedUser;

    private UserSession() {
        // construtor privado
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // ======================
    // CONTROLE DE SESSÃO
    // ======================
    public void login(User user) {
        this.loggedUser = user;
    }

    public void logout() {
        this.loggedUser = null;
    }

    public boolean isLoggedIn() {
        return loggedUser != null;
    }

    // ======================
    // GETTERS DE CONVENIÊNCIA
    // ======================
    public User getUser() {
        return loggedUser;
    }

    public String getUsername() {
        return loggedUser != null ? loggedUser.getUsername() : "";
    }

    public String getName() {
        return loggedUser != null ? loggedUser.getName() : "";
    }

    public String getProfile() {
        return loggedUser != null ? loggedUser.getProfile() : "";
    }

    public String getSector() {
        return loggedUser != null ? loggedUser.getSector() : "";
    }
}
