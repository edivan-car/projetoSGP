package br.com.sgp.auth;

public class LoginController {

	public boolean autenticar(String usuario, char[] senha) {

		// validações básicas
		if (usuario == null || usuario.trim().isEmpty()) {
			return false;
		}

		// TODO: integrar banco ou serviço
		return "admin".equals(usuario) && String.valueOf(senha).equals("123");
	}

}
