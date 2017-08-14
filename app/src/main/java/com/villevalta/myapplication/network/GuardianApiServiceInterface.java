package com.villevalta.myapplication.network;

import com.villevalta.myapplication.model.Page;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by villevalta on 8/14/17.
 */

public interface GuardianApiServiceInterface {

    // https://content.guardianapis.com/search?q=debates&api-key=test&page=3
    @GET("search")
    Call<Page> search(@Query("q") String query, @Query("api-key") String apiKey , @Query("page") int page);


}
