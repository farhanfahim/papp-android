package com.tekrevol.papp.models.receiving_model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tekrevol.papp.managers.retrofit.GsonFactory;

import java.util.List;

public class TaskUser {

    @Expose
    @SerializedName("attachment")
    private List<TaskAttachmentModel> attachment;
    @Expose
    @SerializedName("unlock")
    private int unlock;
    @Expose
    @SerializedName("start_date")
    private String startDate;
    @Expose
    @SerializedName("status")
    private int status;
    @Expose
    @SerializedName("task_id")
    private int taskId;
    @Expose
    @SerializedName("user_id")
    private int userId;
    @Expose
    @SerializedName("id")
    private int id;

    public List<TaskAttachmentModel> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<TaskAttachmentModel> attachment) {
        this.attachment = attachment;
    }

    public int getUnlock() {
        return unlock;
    }

    public void setUnlock(int unlock) {
        this.unlock = unlock;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
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

    @Override
    public String toString() {
        return GsonFactory.getSimpleGson().toJson(this);
    }
}
