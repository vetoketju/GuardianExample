package com.villevalta.myapplication.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.villevalta.myapplication.R;

/**
 * Created by villevalta on 8/16/17.
 */

public class ArticleViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;

    public ArticleViewHolder(View itemView) {
        super(itemView);
        titleView = (TextView) itemView.findViewById(R.id.title);
    }


}
