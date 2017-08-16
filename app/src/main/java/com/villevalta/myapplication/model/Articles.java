package com.villevalta.myapplication.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by villevalta on 8/15/17.
 */

public class Articles extends RealmObject {

    @PrimaryKey
    private String id;
    private int currentPage = 1;
    private RealmList<Article> items = new RealmList<>();
    private long lastUpdated;

    public Articles(){

    }

    public void reset(){
        currentPage = 1;
        items.clear();
        lastUpdated = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RealmList<Article> getItems() {
        return items;
    }

    public void setItems(RealmList<Article> items) {
        this.items = items;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
