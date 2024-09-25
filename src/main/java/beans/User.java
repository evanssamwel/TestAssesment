/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Bixermann
 */
package beans;

/**
 * Represents a user in the system with attributes such as id, username, password, 
 * first name, last name, email, and phone number.
 */
public class User {
    private int id;            // User ID
    private String username;   // Username
    private String password;   // Hashed password for security
    private String firstname;   // User's first name
    private String lastname;    // User's last name
    private String email;      // Email address
    private String phone;      // Phone number

    // Default constructor
    public User() {
    }

    // Parameterized constructor
    public User(int id, String username, String password, String firstname, String lastname, String email, String phone) {
        this.id = id;
        this.username = username;
        this.password = password; // Ensure to hash the password before setting
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
    }

    // Getter and Setter methods
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        // Consider adding password hashing here before setting
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
public String toString() {
    return new StringBuilder("User{")
            .append("id=").append(id)
            .append(", username='").append(username).append('\'')
            .append(", firstname='").append(firstname).append('\'')
            .append(", lastname='").append(lastname).append('\'')
            .append(", email='").append(email).append('\'')
            .append(", phone='").append(phone).append('\'')
            .append('}').toString();
}


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        User user = (User) obj;
        return id == user.id && username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return 31 * id + username.hashCode();
    }
}


