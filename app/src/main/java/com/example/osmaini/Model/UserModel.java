package com.example.osmaini.Model;

import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("id")
    String id;
    @SerializedName("username")
    String username;
    @SerializedName("role")
    String role;
    @SerializedName("bidang_kode")
    String bidang_kode;

    public UserModel() {};
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBidang_kode() {
        return bidang_kode;
    }

    public void setBidang_kode(String bidang_kode) {
        this.bidang_kode = bidang_kode;
    }
}
