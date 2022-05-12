package com.example.soundflows.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikeSong {
    @SerializedName("ID_song")
    @Expose
    private int idSong;

    @SerializedName("Email")
    @Expose
    private String email;

    public int getIdSong() {
        return idSong;
    }

    public void setIdSong(int idSong) {
        this.idSong = idSong;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
