package org.example.Model;

public class User {

    private int Id;


    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(int Id, String username, String password) {
        this.Id = Id;
        this.username = username;
        this.password = password;
    }

    public User(){}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }



}
