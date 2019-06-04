package com.tekrevol.papp.models.sending_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tekrevol.papp.managers.retrofit.GsonFactory;

public class TaskAcceptSendingModel {

    @Expose
    @SerializedName("task_id")
    private int taskId;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return GsonFactory.getSimpleGson().toJson(this);
    }
}
