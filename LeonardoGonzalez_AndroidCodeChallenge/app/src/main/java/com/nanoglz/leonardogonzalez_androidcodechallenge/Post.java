package com.nanoglz.leonardogonzalez_androidcodechallenge;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by m11 on 10/28/2015.
 */
public class Post implements Parcelable {
    String title;
    String author;
    String thumbnail;
    int num_comments;
    int ups;
    int downs;

    public Post(String title, String author, String thumbnail, int num_comments, int ups, int downs) {
        this.title = title;
        this.author = author;
        this.thumbnail = thumbnail;
        this.num_comments = num_comments;
        this.ups = ups;
        this.downs = downs;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDowns(int downs) {
        this.downs = downs;
    }

    public void setNum_comments(int num_comments) {
        this.num_comments = num_comments;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public static Creator<Post> getCREATOR() {
        return CREATOR;
    }

    public int getDowns() {
        return downs;
    }

    public int getNum_comments() {
        return num_comments;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public int getUps() {
        return ups;
    }

    public void setUps(int ups) {
        this.ups = ups;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putString("title", getTitle());
        bundle.putString("author", getAuthor());
        bundle.putString("thumbnail", getThumbnail());
        bundle.putInt("num_comments", getNum_comments());
        bundle.putInt("ups", getUps());
        bundle.putInt("downs", getDowns());
        dest.writeBundle(bundle);
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    protected Post(Parcel in) {
        Bundle bundle  = in.readBundle();
        title = bundle.getString("title");
        author = bundle.getString("author");
        thumbnail = bundle.getString("thumbnail");
        num_comments = bundle.getInt("num_comments");
        ups = bundle.getInt("ups");
        downs = bundle.getInt("downs");
    }

}
