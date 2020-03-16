package com.tekrevol.papp.models.receiving_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tekrevol.papp.managers.retrofit.GsonFactory;

import java.util.List;

public class TaskReceivingModel {


    @Expose
    @SerializedName("task_users")
    private TaskUser taskUsers;
    @Expose
    @SerializedName("task_category")
    private TaskCategory taskCategory;
    @Expose
    @SerializedName("updated_at")
    private String updatedAt;
    @Expose
    @SerializedName("created_at")
    private String createdAt;
    @Expose
    @SerializedName("icon")
    private String icon;
    @Expose
    @SerializedName("icon_url")
    private String iconUrl;
    @Expose
    @SerializedName("duration")
    private String duration;
    @Expose
    @SerializedName("reward_points")
    private int rewardPoints;
    @Expose
    @SerializedName("type")
    private int type;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("task_category_id")
    private int taskCategoryId;
    @Expose
    @SerializedName("id")
    private int id;


    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public TaskUser getTaskUsers() {
        return taskUsers;
    }

    public void setTaskUsers(TaskUser taskUsers) {
        this.taskUsers = taskUsers;
    }

    public TaskCategory getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(TaskCategory taskCategory) {
        this.taskCategory = taskCategory;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTaskCategoryId() {
        return taskCategoryId;
    }

    public void setTaskCategoryId(int taskCategoryId) {
        this.taskCategoryId = taskCategoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return GsonFactory.getSimpleGson().toJson(this);
    }
}
