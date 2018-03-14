package edu.aku.akuh_health_first.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Webandmobilemodel {
    @Expose
    @SerializedName("ImageURL")
    private String imageurl;
    @Expose
    @SerializedName("Link")
    private String link;
    @Expose
    @SerializedName("Title")
    private String title;

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
