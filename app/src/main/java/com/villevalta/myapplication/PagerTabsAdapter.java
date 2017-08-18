package com.villevalta.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.villevalta.myapplication.fragment.ListFragment;

/**
 * Created by villevalta on 8/18/17.
 */

public class PagerTabsAdapter extends FragmentStatePagerAdapter {

    public PagerTabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0){
            // ensimmäinen tab
            return ListFragment.newInstance("trump");

        }else if(position == 1){
            // keskimmäinen tab
            return ListFragment.newInstance("android");

        }else {
            // viimeinen tab
            return ListFragment.newInstance("spaceX");
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            // ensimmäinen tab
            return "trump";

        }else if(position == 1){
            // keskimmäinen tab
            return "android";

        }else {
            // viimeinen tab
            return "spaceX";
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
