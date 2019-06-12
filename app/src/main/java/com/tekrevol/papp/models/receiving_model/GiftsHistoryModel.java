package com.tekrevol.papp.models.receiving_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tekrevol.papp.managers.retrofit.GsonFactory;

public  class GiftsHistoryModel {


    @Expose
    @SerializedName("gifts")
    private GiftsModel gifts;
    @Expose
    @SerializedName("created_at")
    private String createdAt;
    @Expose
    @SerializedName("gift_id")
    private int giftId;
    @Expose
    @SerializedName("points")
    private int points;
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

    public GiftsModel getGifts() {
        return gifts;
    }

    public void setGifts(GiftsModel gifts) {
        this.gifts = gifts;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getGiftId() {
        return giftId;
    }

    public void setGiftId(int giftId) {
        this.giftId = giftId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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
