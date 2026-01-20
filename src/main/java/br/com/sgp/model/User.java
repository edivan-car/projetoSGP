package br.com.sgp.model;

public class User {

    private int id;
    private String username;
    private String nome;
    private String perfil;

    public User(int id, String username, String nome, String perfil) {
        this.id = id;
        this.username = username;
        this.nome = nome;
        this.perfil = perfil;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNome() {
        return nome;
    }

    public String getPerfil() {
        return perfil;
    }
}
