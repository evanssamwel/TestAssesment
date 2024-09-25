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
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class UserBean {
    private User newUser; // User instance for new user creation
    private List<User> users; // List of users from the database

   public UserBean() {
    users = loadUsers();  // Load users from the database
    newUser = new User(); // Initialize new user object for creation
}

    // Getter for users
    public List<User> getUsers() {
        return users;
    }

    // Getter for newUser
    public User getNewUser() {
        return newUser;
    }

    // Prepare to create a new user
    public void prepareCreate() {
        newUser = new User(); // Initialize a new user instance
    }

    // Create a new user
    public void createUser() {
        try (Connection conn = DatabaseMySQL.getMySQLConnection()) {
            String insertQuery = "INSERT INTO users (username, password, firstname, lastname, email, phone) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertQuery);
            stmt.setString(1, newUser.getUsername());
            stmt.setString(2, newUser.getPassword()); // Consider hashing the password here
            stmt.setString(3, newUser.getFirstname());
            stmt.setString(4, newUser.getLastname());
            stmt.setString(5, newUser.getEmail());
            stmt.setString(6, newUser.getPhone());
            stmt.executeUpdate();

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "User created successfully.", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error creating user: " + e.getMessage(), null));
        } finally {
            users = loadUsers(); // Refresh the user list after creating
        }
    }

    // Delete a user
    public void deleteUser(User user) {
        try (Connection conn = DatabaseMySQL.getMySQLConnection()) {
            String deleteQuery = "DELETE FROM users WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(deleteQuery);
            stmt.setInt(1, user.getId());
            stmt.executeUpdate();

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "User deleted successfully.", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error deleting user: " + e.getMessage(), null));
        } finally {
            users = loadUsers(); // Refresh the user list after deletion
        }
    }

    // Load users from the database
    private List<User> loadUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection conn = DatabaseMySQL.getMySQLConnection()) {
            String query = "SELECT * FROM users";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password")); // Store hash or raw password as necessary
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions as appropriate
        }
        return userList;
    }
}
