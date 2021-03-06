package com.example.soundflows.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Album implements Serializable {
    @SerializedName("ID_album")
    @Expose
    private String iDAlbum;
    @SerializedName("Img_album")
    @Expose
    private String imgAlbum;
    @SerializedName("Name_album")
    @Expose
    private String nameAlbum;

    public String getIDAlbum() {
        return iDAlbum;
    }

    public void setIDAlbum(String iDAlbum) {
        this.iDAlbum = iDAlbum;
    }

    public String getImgAlbum() {
        return imgAlbum;
    }

    public void setImgAlbum(String imgAlbum) {
        this.imgAlbum = imgAlbum;
    }

    public String getNameAlbum() {
        return nameAlbum;
    }

    public void setNameAlbum(String nameAlbum) {
        this.nameAlbum = nameAlbum;
    }
}
