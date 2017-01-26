package com.ks.fanat94.kschool;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 16.08.2016.
 */
public class FeedItem implements Parcelable {
    String title;
    String link;
    String description;
    String pubDate;
    String thumbnailUrl;

    /*public String titlet;
    public String titlett;
    public String titlettt;
    public String descriptiont;
    public String descriptiontt;*/


    public FeedItem(Parcel source) {

        title=source.readString();
        link = source.readString();
        description=source.readString();
        thumbnailUrl = source.readString();
    }

    public FeedItem() {

    }

    public static final Creator<FeedItem> CREATOR = new Creator<FeedItem>() {
        @Override
        public FeedItem createFromParcel(Parcel in) {
            return new FeedItem(in);
        }

        @Override
        public FeedItem[] newArray(int size) {
            return new FeedItem[size];
        }
    };


    public FeedItem(String nameitem, String pibteacher, String nameitem1, String pibteacher1, String pibteacher2) {
    }

    public FeedItem(String nameitem, String pibteacher, String nameitem1, String pibteacher1, String[] pibteacher201) {
    }

    /*public FeedItem(String titlet, String descriptiont, String titlett, String titlettt, String descriptiontt) {
        this.titlet =titlet;
        this.titlett =titlett;
        this.titlettt =titlettt;
        this.descriptiont = descriptiont;
        this.descriptiontt=descriptiontt;
    }*/

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPubDate() {
        return pubDate;
    }
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(link);
        parcel.writeString(description);
        parcel.writeString(thumbnailUrl);
    }
}

