package com.villevalta.myapplication;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.villevalta.myapplication.view.MyRecyclerView;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    String TAG = "MainActivity";

    TabLayout tabs;
    ViewPager pager;
    PagerTabsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = (TabLayout) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);

        adapter = new PagerTabsAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        tabs.setupWithViewPager(pager);


    }



}
