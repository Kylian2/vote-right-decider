package fr.voteright.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("USR_id_NB")
    private int id;

    @SerializedName("USR_lastname_VC")
    private String lastName;

    @SerializedName("USR_firstname_VC")
    private String firstName;

    @SerializedName("USR_email_VC")
    private String email;

    @SerializedName("USR_password_VC")
    private String password;

    // Constructeurs
    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(int id, String lastName, String firstName, String email, String password) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
