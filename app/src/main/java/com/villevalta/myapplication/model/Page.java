package com.villevalta.myapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by villevalta on 8/14/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Page {

    Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
