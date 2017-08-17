package com.villevalta.myapplication.viewholder;

import android.support.v7.widget.RecyclerView;

import com.villevalta.myapplication.databinding.ListArticleBinding;


/**
 * Created by villevalta on 8/16/17.
 */

public class ArticleViewHolder extends RecyclerView.ViewHolder {

    public ListArticleBinding binding;

    public ArticleViewHolder(ListArticleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }


}
