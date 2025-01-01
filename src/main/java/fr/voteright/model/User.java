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

    @SerializedName("USR_address_VC")
    private String address;

    @SerializedName("USR_zipcode_CH")
    private String zipcode;

    @SerializedName("USR_birthdate_DATE")
    private String birthdate;

    @SerializedName("USR_notification_frequency_CH")
    private String notificationFrequency;

    @SerializedName("USR_notify_proposal_BOOL")
    private boolean notifyProposal;

    @SerializedName("USR_notify_vote_BOOL")
    private boolean notifyVote;

    @SerializedName("USR_notify_reaction_BOOL")
    private boolean notifyReaction;

    @SerializedName("USR_newsletter_BOOL")
    private boolean newsletter;

    // Constructeurs
    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(int id, String lastName, String firstName, String email, String password, String address,
                String zipcode, String birthdate, String notificationFrequency, boolean notifyProposal,
                boolean notifyVote, boolean notifyReaction, boolean newsletter) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.zipcode = zipcode;
        this.birthdate = birthdate;
        this.notificationFrequency = notificationFrequency;
        this.notifyProposal = notifyProposal;
        this.notifyVote = notifyVote;
        this.notifyReaction = notifyReaction;
        this.newsletter = newsletter;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getNotificationFrequency() {
        return notificationFrequency;
    }

    public void setNotificationFrequency(String notificationFrequency) {
        this.notificationFrequency = notificationFrequency;
    }

    public boolean isNotifyProposal() {
        return notifyProposal;
    }

    public void setNotifyProposal(boolean notifyProposal) {
        this.notifyProposal = notifyProposal;
    }

    public boolean isNotifyVote() {
        return notifyVote;
    }

    public void setNotifyVote(boolean notifyVote) {
        this.notifyVote = notifyVote;
    }

    public boolean isNotifyReaction() {
        return notifyReaction;
    }

    public void setNotifyReaction(boolean notifyReaction) {
        this.notifyReaction = notifyReaction;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public void setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
    }
}
