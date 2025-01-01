package fr.voteright.model;

import com.google.gson.annotations.SerializedName;

public class Community {

    @SerializedName("CMY_id_NB")
    private int id;

    @SerializedName("CMY_name_VC")
    private String name;

    @SerializedName("CMY_image_VC")
    private String image;

    @SerializedName("CMY_emoji_VC")
    private String emoji;

    @SerializedName("CMY_color_VC")
    private String color;

    @SerializedName("CMY_description_TXT")
    private String description;

    @SerializedName("CMY_nb_member_NB")
    private int numberOfMembers;

    // Constructeurs, getters et setters

    public Community(int id, String name, String image, String emoji, String color, String description, int numberOfMembers) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.emoji = emoji;
        this.color = color;
        this.description = description;
        this.numberOfMembers = numberOfMembers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfMembers() {
        return numberOfMembers;
    }

    public void setNumberOfMembers(int numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }
}