package com.villevalta.myapplication;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by villevalta on 8/17/17.
 */

public class ViewBindingAdapters {

    // app:kuvaurl
    @BindingAdapter({"kuvaurl", "kaannos"})
    public static void LataaKuva(ImageView imageView, String url, float kaannos){

        Log.d("ADAPTER", "LataaKuva: " + kaannos);

        Picasso.with(imageView.getContext())
                .load(url)
                .noFade()
                .error(R.drawable.ic_pets_black_24dp)
                .placeholder(R.drawable.ic_directions_boat_black_24dp)
                .rotate(kaannos)
                .into(imageView);

    }



}
