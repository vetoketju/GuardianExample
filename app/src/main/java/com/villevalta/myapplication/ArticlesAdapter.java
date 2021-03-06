package com.villevalta.myapplication;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.villevalta.myapplication.databinding.ListArticleBinding;
import com.villevalta.myapplication.model.Article;
import com.villevalta.myapplication.model.Articles;
import com.villevalta.myapplication.viewholder.ArticleViewHolder;
import com.villevalta.myapplication.viewholder.LoadingViewHolder;

import io.realm.RealmChangeListener;

/**
 * Created by villevalta on 8/16/17.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RealmChangeListener<Articles> {

    private static final int KEY_POSITION_LOADING = 0;
    private static final int KEY_POSITION_ITEM = 1;

    private Articles items;
    private boolean isLoading = false;

    public void initialize(Articles articles){
        this.items = articles;
        notifyDataSetChanged();
        items.addChangeListener(this);
    }

    @Override
    public void onChange(Articles articles) {
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if(viewType == KEY_POSITION_ITEM){
            View view = inflater.inflate(R.layout.list_article, parent, false);
            ListArticleBinding binding = ListArticleBinding.inflate(inflater, parent, false);
            return new ArticleViewHolder(binding);
        }else if(viewType == KEY_POSITION_LOADING){
            View view = inflater.inflate(R.layout.list_loading, parent, false);
            return new LoadingViewHolder(view);
        }


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ArticleViewHolder){
            Article article = items.getItems().get(position);


            ((ArticleViewHolder)holder).binding.setArticleItem(article);


            //Linkify.addLinks(((ArticleViewHolder)holder).urlView, Linkify.ALL);
        }

    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        // Reset views here if needed
        super.onViewRecycled(holder);
    }

    public void setIsLoading(boolean currentlyLoading){
        isLoading = currentlyLoading;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        int count = 0;

        if(items != null && items.getItems() != null){
            count += items.getItems().size();
        }

        if(isLoading) count++;

        return count;
    }

    @Override
    public int getItemViewType(int position) {

        if(isLoading){
            if(items != null && items.getItems() != null && items.getItems().size() == position){
                return KEY_POSITION_LOADING;
            }
        }

        return KEY_POSITION_ITEM;
    }

}
