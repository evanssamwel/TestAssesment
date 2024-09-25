package beans;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Bixermann
 */
import database.DatabaseMySQL;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.mindrot.jbcrypt.BCrypt;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Named
@SessionScoped // Use session scope to keep user logged in during the session
public class LoginBean implements Serializable {
    private String username;
    private String password;

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Login method
    public String login() {
        boolean isValidUser = validateCredentials(username, password);
        if (isValidUser) {
            return "users.xhtml?faces-redirect=true";  // redirect to users page if successful
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid username or password.", null));
            return null;  // stay on login page if authentication fails
        }
    }

    // Method to validate login credentials using DatabaseMySQL class
    private boolean validateCredentials(String username, String password) {
        try (Connection conn = DatabaseMySQL.getMySQLConnection()) {
            String query = "SELECT password FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password"); // Retrieve the stored password hash
                return BCrypt.checkpw(password, storedHash);  // Compare hashed passwords
            }
        } catch (Exception e) {
            e.printStackTrace();  // Consider using a logger instead of printStackTrace
        }
        return false;  // If no user found or error occurs
    }
}
