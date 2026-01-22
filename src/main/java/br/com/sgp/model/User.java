package br.com.sgp.model;

public class User {

    private int id;
    private String username;
    private String name;
    private String profile;
    private String sector;

    public User(int id, String username, String name, String profile, String sector) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.profile = profile;
        this.sector = sector;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getProfile() { return profile; }
    public String getSector() { return sector; }
}
