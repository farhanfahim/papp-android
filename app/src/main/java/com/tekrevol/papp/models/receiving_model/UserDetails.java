package com.tekrevol.papp.models.receiving_model;

import com.tekrevol.papp.managers.retrofit.GsonFactory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetails {

    @Expose
    @SerializedName("total_points")
    private int totalPoints;
    @Expose
    @SerializedName("agency")
    private String agency;
    @Expose
    @SerializedName("is_social_login")
    private int isSocialLogin;
    @Expose
    @SerializedName("email_updates")
    private int emailUpdates;
    @Expose
    @SerializedName("image")
    private String image = "";
    @Expose
    @SerializedName("gender")
    private int gender;
    @Expose
    @SerializedName("designation")
    private String designation;
    @Expose
    @SerializedName("last_name")
    private String lastName;
    @Expose
    @SerializedName("first_name")
    private String firstName;
    @Expose
    @SerializedName("full_name")
    private String fullName;
    @Expose
    @SerializedName("category_id")
    private int categoryId;
    @Expose
    @SerializedName("user_id")
    private int userId;
    @Expose
    @SerializedName("parent_id")
    private int parentId;
    @Expose
    @SerializedName("department_id")
    private int departmentId;
    @Expose
    @SerializedName("id")
    private int id;

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public int getIsSocialLogin() {
        return isSocialLogin;
    }

    public void setIsSocialLogin(int isSocialLogin) {
        this.isSocialLogin = isSocialLogin;
    }

    public int getEmailUpdates() {
        return emailUpdates;
    }

    public void setEmailUpdates(int emailUpdates) {
        this.emailUpdates = emailUpdates;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
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
