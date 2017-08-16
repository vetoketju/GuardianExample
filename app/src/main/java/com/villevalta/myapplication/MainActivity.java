package com.villevalta.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.villevalta.myapplication.model.Article;
import com.villevalta.myapplication.model.Articles;
import com.villevalta.myapplication.model.Page;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";

    private Realm realm;
    private Articles articles;

    String hakusana = "trump";

    Button tyhjenna;
    RecyclerView recycler;
    ArticlesAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tyhjenna = (Button) findViewById(R.id.clear_db);

        adapter = new ArticlesAdapter();
        recycler = (RecyclerView) findViewById(R.id.recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);


        tyhjenna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(articles != null && realm !=null){
                    realm.beginTransaction();
                    articles.reset();
                    realm.commitTransaction();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        realm = Realm.getDefaultInstance();

        articles = realm.where(Articles.class).equalTo("id", hakusana).findFirst();

        // Articles objektia ei löytynyt realmista, luodaan se:
        if(articles == null){
            articles = new Articles();
            articles.setId(hakusana);

            realm.beginTransaction();
            articles = realm.copyToRealm(articles);
            realm.commitTransaction();
        }

        if(articles.getCurrentPage() <= 1){
            getPage();
        }else{
            // artikkelit tietokannasta:
            Log.i(TAG, "Articles from realm database: haetut sivut: " + articles.getCurrentPage() + " Last updated=" + articles.getLastUpdated());
            for (Article article : articles.getItems()) {
                Log.d(TAG, "item: " + article.getWebTitle());
            }
        }

        adapter.initialize(articles);

    }

    private void getPage(){

        adapter.setIsLoading(true);
        GuardianApp.getInstance().getApiService().search(hakusana, articles.getCurrentPage()).enqueue(new Callback<Page>() {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {
                adapter.setIsLoading(false);

                List<Article> results = response.body().getResponse().getResults();

                Log.i(TAG, "Articles from web: ");
                for (Article result : results) {
                    Log.d(TAG, "item: " + result.getWebTitle());
                }

                realm.beginTransaction();

                articles.setCurrentPage(articles.getCurrentPage() + 1);
                articles.getItems().addAll(results);
                long lastUpdated = new Date().getTime();
                articles.setLastUpdated(lastUpdated);

                realm.commitTransaction();
            }

            @Override
            public void onFailure(Call<Page> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                adapter.setIsLoading(false);
            }
        });

    }

    @Override
    protected void onPause() {

        if(realm != null && !realm.isClosed()){
            realm.close();
        }


        super.onPause();
    }
}
