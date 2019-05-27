package com.tekrevol.papp.models.wrappers;

import com.google.gson.annotations.SerializedName;

/**
 * Created by khanhamza on 09-Mar-17.
 */

public class WebResponse<T> {

    @SerializedName("message")
    public String message;

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    public T result;

    public boolean isSuccess() {
        return success;
    }
}
