package com.tekrevol.papp.models.receiving_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tekrevol.papp.managers.retrofit.GsonFactory;

public class ReviewsModel {


    @Expose
    @SerializedName("user")
    private UserModel user;
    @Expose
    @SerializedName("created_at")
    private String createdAt;
    @Expose
    @SerializedName("review")
    private String review;
    @Expose
    @SerializedName("rating")
    private double rating;
    @Expose
    @SerializedName("mentor_id")
    private int mentorId;
    @Expose
    @SerializedName("user_id")
    private int userId;
    @Expose
    @SerializedName("id")
    private int id;

    @Override
    public String toString() {
        return GsonFactory.getSimpleGson().toJson(this);
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
