package com.example.bloodbankjava;

public class User {
    private int userId;
    private String name;
    private String role;
    private String email;
    private String password;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(int userId, String name, String role, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String toString() {
        return name + " (" + email + ")";
    }
}
