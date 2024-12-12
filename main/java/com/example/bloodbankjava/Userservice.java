package com.example.bloodbankjava;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

public class Userservice {
    private static List<User> users = new ArrayList<>();

    // Fetch all users
    public static List<User> getAllUsers() {
        // Example: Replace with your actual implementation to fetch users
        List<User> users = new ArrayList<>();
        users.add(new User("Admin", "admin@example.com")); // Sample user
        return users;
    }


    // Add a new user
    public static void addUser(User user) {
        // Example: Replace with your actual implementation
        System.out.println("User added: " + user.getName() + ", " + user.getEmail());
        // Add to storage/database
    }

    // Update an existing user
    public static void updateUser(User user) {
        // Example: Replace with your actual implementation
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(user.getEmail())) {
                users.set(i, user);
                break;
            }
        }
    }

    // System.out.println("User updated: " + user.getName() + ", " + user.getEmail());
    // Update user in storage/database

    // Remove a user
    public static void removeUser(User user) {
        // Check if the user exists in the list
        if (users.removeIf(existingUser -> existingUser.getEmail().equals(user.getEmail()))) {
            System.out.println("User removed: " + user.getName() + ", " + user.getEmail());
        } else {
            System.out.println("User not found: " + user.getName() + ", " + user.getEmail());
        }
    }

    public User login(String email, String password) {
 return null;   }
}
