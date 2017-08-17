package com.villevalta.myapplication;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by villevalta on 8/17/17.
 */

public class ViewBindingAdapters {

    // app:kuvaurl
    @BindingAdapter("kuvaurl")
    public static void LataaKuva(ImageView imageView, String url){

        Picasso.with(imageView.getContext())
                .load(url)
                .into(imageView);

    }



}
