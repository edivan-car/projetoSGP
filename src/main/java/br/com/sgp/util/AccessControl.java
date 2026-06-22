package br.com.sgp.util;

import br.com.sgp.session.UserSession;

public final class AccessControl {

    // Perfis
    public static final String ADMIN  = "ADMIN";
    public static final String GESTOR = "GESTOR";
    public static final String USER   = "USER";

    // Setores
    public static final String FABRICACAO_PECAS  = "FABRICACAO_PECAS";
    public static final String FABRICACAO_VIGAS  = "FABRICACAO_VIGAS";
    public static final String TI                = "TI";

    private AccessControl() {}

    public static boolean isAdmin() {
        return ADMIN.equals(UserSession.getInstance().getProfile());
    }

    public static boolean isGestor() {
        String profile = UserSession.getInstance().getProfile();
        return ADMIN.equals(profile) || GESTOR.equals(profile);
    }

    public static boolean hasSectorAccess(String sector) {
        if (isAdmin()) return true;
        return sector.equals(UserSession.getInstance().getSector());
    }
}