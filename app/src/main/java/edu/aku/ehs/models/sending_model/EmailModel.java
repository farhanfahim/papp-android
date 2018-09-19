package edu.aku.ehs.models.sending_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import edu.aku.ehs.managers.retrofit.GsonFactory;

public class EmailModel {


    @Expose
    @SerializedName("Subject")
    private String Subject;
    @Expose
    @SerializedName("Message")
    private String Message;
    @Expose
    @SerializedName("Receipients")
    private List<String> Receipients;

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public List<String> getReceipients() {
        return Receipients;
    }

    public void setReceipients(List<String> Receipients) {
        this.Receipients = Receipients;
    }


    @Override
    public String toString() {
        return GsonFactory.getConfiguredGson().toJson(this);
    }

}
