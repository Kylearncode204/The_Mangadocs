package kytallo.com.themangadocs;

public class User {
    private int id;
    private  String username;
    private  String email;
    private  String passworld;

    public User(String username, String email, String passworld) {
        this.username = username;
        this.email = email;
        this.passworld = passworld;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassworld() {
        return passworld;
    }

    public void setPassworld(String passworld) {
        this.passworld = passworld;
    }

}
