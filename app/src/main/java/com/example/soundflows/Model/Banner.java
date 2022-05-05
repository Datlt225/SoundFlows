package com.example.soundflows.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Banner implements Serializable {

    @SerializedName("ID_ads")
    @Expose
    private String iDAds;
    @SerializedName("Img_ads")
    @Expose
    private String imgAds;
    @SerializedName("Content")
    @Expose
    private String content;
    @SerializedName("ID_song")
    @Expose
    private String iDSong;
    @SerializedName("Name_song")
    @Expose
    private String nameSong;
    @SerializedName("Img_song")
    @Expose
    private String imgSong;

    public String getIDAds() {
    return iDAds;
    }

    public void setIDAds(String iDAds) {
    this.iDAds = iDAds;
    }

    public String getImgAds() {
    return imgAds;
    }

    public void setImgAds(String imgAds) {
    this.imgAds = imgAds;
    }

    public String getContent() {
    return content;
    }

    public void setContent(String content) {
    this.content = content;
    }

    public String getIDSong() {
    return iDSong;
    }

    public void setIDSong(String iDSong) {
    this.iDSong = iDSong;
    }

    public String getNameSong() {
    return nameSong;
    }

    public void setNameSong(String nameSong) {
    this.nameSong = nameSong;
    }

    public String getImgSong() {
    return imgSong;
    }

    public void setImgSong(String imgSong) {
    this.imgSong = imgSong;
    }

}