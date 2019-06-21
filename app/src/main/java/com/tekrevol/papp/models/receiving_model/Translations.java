package com.tekrevol.papp.models.receiving_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Translations {
    @Expose
    @SerializedName("status")
    private boolean status;
    @Expose
    @SerializedName("content")
    private String content;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("locale")
    private String locale;
    @Expose
    @SerializedName("page_id")
    private int pageId;
    @Expose
    @SerializedName("id")
    private int id;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
