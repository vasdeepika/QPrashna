package com.android.qprashna.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersResponse {

    @SerializedName("userResult")
    @Expose
    private List<UserResult> userResult = null;

    public List<UserResult> getUserResult() {
        return userResult;
    }

    public void setUserResult(List<UserResult> userResult) {
        this.userResult = userResult;
    }

}
