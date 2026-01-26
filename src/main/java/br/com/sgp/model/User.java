package br.com.sgp.model;

public class User {

    private int id;
    private String username;
    private String password;
    private String name;
    private String profile;
    private String sector;
    private boolean active;

    public User(int id, String username, String password, String name,
                String profile, String sector, boolean active) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.profile = profile;
        this.sector = sector;
        this.active = active;
    }

    public User(int id, String username, String name, String profile, String sector) {
        this(id, username, null, name, profile, sector, true);
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getProfile() { return profile; }
    public String getSector() { return sector; }
    public boolean isActive() { return active; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", profile='" + profile + '\'' +
                ", sector='" + sector + '\'' +
                ", active=" + active +
                '}';
    }
}
