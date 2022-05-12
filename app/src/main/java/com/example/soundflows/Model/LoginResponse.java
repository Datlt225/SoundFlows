package com.example.soundflows.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LoginResponse {
    @SerializedName("ID_user")
    @Expose
    private int idUser;

    @SerializedName("Name")
    @Expose
    private String Name;

    private ArrayList<List<String>> data;

    public ArrayList<List<String>> getData() {
        return data;
    }

    public void setData(ArrayList<List<String>> data) {
        this.data = data;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
