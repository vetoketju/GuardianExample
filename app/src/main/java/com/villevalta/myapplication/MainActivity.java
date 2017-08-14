package com.villevalta.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.villevalta.myapplication.model.Article;
import com.villevalta.myapplication.model.Page;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GuardianApp.getInstance().getApiService().search("trump", 1).enqueue(new Callback<Page>() {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {
                List<Article> results = response.body().getResponse().getResults();

                for (Article result : results) {
                    Log.d(TAG, "article: " + result.getWebTitle());
                }


            }

            @Override
            public void onFailure(Call<Page> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });



    }
}
