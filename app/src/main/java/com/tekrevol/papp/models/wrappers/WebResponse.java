package com.tekrevol.papp.models.wrappers;

import com.google.gson.annotations.SerializedName;
import com.tekrevol.papp.managers.retrofit.entities.ErrorModel;

import java.util.ArrayList;

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

    @SerializedName("errors")
    public ArrayList<ErrorModel> errorList;

    public boolean isSuccess() {
        return success;
    }
}
