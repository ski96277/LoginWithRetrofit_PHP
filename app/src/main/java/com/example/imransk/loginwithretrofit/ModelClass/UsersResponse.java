package com.example.imransk.loginwithretrofit.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersResponse {
   /* //this error name is same from json

    private boolean error;

    //this users name is same from json array
    private List<User> users;*/

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("users")
    @Expose
    private List<User> users = null;

    public UsersResponse(boolean error, List<User> users) {
        this.error = error;
        this.users = users;
    }

    public boolean isError() {
        return error;
    }

    public List<User> getUsers() {
        return users;
    }
}
