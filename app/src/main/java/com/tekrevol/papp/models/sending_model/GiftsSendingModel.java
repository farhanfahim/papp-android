package com.tekrevol.papp.models.sending_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tekrevol.papp.managers.retrofit.GsonFactory;

public class GiftsSendingModel {

    @Expose
    @SerializedName("points")
    private int points;
    @Expose
    @SerializedName("gift_id")
    private int gift_id;

    public GiftsSendingModel(int points, int gift_id) {
        this.points = points;
        this.gift_id = gift_id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGift_id() {
        return gift_id;
    }

    public void setGift_id(int gift_id) {
        this.gift_id = gift_id;
    }

    @Override
    public String toString() {
        return GsonFactory.getSimpleGson().toJson(this);
    }
}
