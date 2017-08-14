package com.villevalta.myapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by villevalta on 8/14/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    String status;

    List<Article> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Article> getResults() {
        return results;
    }

    public void setResults(List<Article> results) {
        this.results = results;
    }
}
