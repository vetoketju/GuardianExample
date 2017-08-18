package com.villevalta.myapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.villevalta.myapplication.ArticlesAdapter;
import com.villevalta.myapplication.GuardianApp;
import com.villevalta.myapplication.R;
import com.villevalta.myapplication.model.Article;
import com.villevalta.myapplication.model.Articles;
import com.villevalta.myapplication.model.Page;
import com.villevalta.myapplication.view.MyRecyclerView;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by villevalta on 8/18/17.
 */

public class ListFragment extends Fragment implements MyRecyclerView.LoadMoreListener {

    String TAG = "ListFragment";

    String hakusana;

    private Realm realm;
    private Articles articles;

    SwipeRefreshLayout swiper;
    MyRecyclerView recycler;
    ArticlesAdapter adapter;

    private boolean isLoading;


    public static ListFragment newInstance(String hakusana){
        Bundle args = new Bundle();
        args.putString("query", hakusana);
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        hakusana = args.getString("query");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_list, container, false);

        adapter = new ArticlesAdapter();
        recycler = (MyRecyclerView) root.findViewById(R.id.recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);

        recycler.setLoadMoreListener(this);

        swiper = (SwipeRefreshLayout) root.findViewById(R.id.swiper);

        swiper.setColorSchemeColors(Color.CYAN);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(articles != null && realm !=null){
                    realm.beginTransaction();
                    articles.reset();
                    realm.commitTransaction();
                }
                getPage();
                swiper.setRefreshing(false);
            }
        });


        return root;
    }


    private void getPage(){

        isLoading = true;
        adapter.setIsLoading(true);
        GuardianApp.getInstance().getApiService().search(hakusana, articles.getCurrentPage()).enqueue(new Callback<Page>() {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {
                adapter.setIsLoading(false);
                isLoading = false;

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
                isLoading = false;
            }
        });

    }

    @Override
    public void onPause() {

        if(realm != null && !realm.isClosed()){
            realm.close();
        }


        super.onPause();
    }

    @Override
    public void shouldLoadMore() {
        if(!isLoading){
            Log.d(TAG, "shouldLoadMore: Starting to load more");
            getPage();
        }
    }

}
