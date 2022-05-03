package com.example.soundflows.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Song implements Parcelable {
    @SerializedName("ID_album")
    @Expose
    private String iDAlbum;
    @SerializedName("ID_category")
    @Expose
    private String iDCategory;
    @SerializedName("ID_playlist")
    @Expose
    private String iDPlaylist;
    @SerializedName("ID_song")
    @Expose
    private Integer iDSong;
    @SerializedName("Img_song")
    @Expose
    private String imgSong;
    @SerializedName("Link_song")
    @Expose
    private String linkSong;
    @SerializedName("Name_song")
    @Expose
    private String nameSong;
    @SerializedName("Singer")
    @Expose
    private String singer;

    protected Song(Parcel in) {
        iDAlbum = in.readString();
        iDCategory = in.readString();
        iDPlaylist = in.readString();
        if (in.readByte() == 0) {
            iDSong = null;
        } else {
            iDSong = in.readInt();
        }
        imgSong = in.readString();
        linkSong = in.readString();
        nameSong = in.readString();
        singer = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public String getIDAlbum() {
        return iDAlbum;
    }

    public void setIDAlbum(String iDAlbum) {
        this.iDAlbum = iDAlbum;
    }

    public String getIDCategory() {
        return iDCategory;
    }

    public void setIDCategory(String iDCategory) {
        this.iDCategory = iDCategory;
    }

    public String getIDPlaylist() {
        return iDPlaylist;
    }

    public void setIDPlaylist(String iDPlaylist) {
        this.iDPlaylist = iDPlaylist;
    }

    public Integer getIDSong() {
        return iDSong;
    }

    public void setIDSong(Integer iDSong) {
        this.iDSong = iDSong;
    }

    public String getImgSong() {
        return imgSong;
    }

    public void setImgSong(String imgSong) {
        this.imgSong = imgSong;
    }

    public String getLinkSong() {
        return linkSong;
    }

    public void setLinkSong(String linkSong) {
        this.linkSong = linkSong;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(iDAlbum);
        dest.writeString(iDCategory);
        dest.writeString(iDPlaylist);
        if (iDSong == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(iDSong);
        }
        dest.writeString(imgSong);
        dest.writeString(linkSong);
        dest.writeString(nameSong);
        dest.writeString(singer);
    }
}
