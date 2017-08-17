package com.villevalta.myapplication.model;

import android.graphics.Color;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by villevalta on 8/14/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Article extends RealmObject{

    @PrimaryKey
    private String id;

    private String webTitle;
    private boolean isHosted;
    private String webUrl;
    private String sectionId;

    public String getKuvaUrl(){
        return "https://robohash.org/" + id.hashCode();
    }

    public int getColor(){
        return Color.RED;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public boolean isHosted() {
        return isHosted;
    }

    public void setHosted(boolean hosted) {
        isHosted = hosted;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
