package com.example.oscarruiz.misseries.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.oscarruiz.misseries.R;
import com.example.oscarruiz.misseries.fragments.SerieInfoFragment;
import com.example.oscarruiz.misseries.fragments.SerieSeasonFragment;
import com.example.oscarruiz.misseries.models.ResponseSerie;

import java.util.ArrayList;

/**
 * Created by Oscar Ruiz on 27/07/2017.
 */

public class ViewPagerSerieInfo extends FragmentPagerAdapter {

    /**
     * Context
     */
    private Context context;

    /**
     * Selected serie
     */
    private ResponseSerie serie;

    /**
     * fragment list
     */
    private ArrayList<Fragment> fragments;

    /**
     * list of tabs
     */
    private int tabs;

    public ViewPagerSerieInfo(Context context, FragmentManager fm, int tabs, ResponseSerie serie) {
        super(fm);
        this.tabs = tabs;
        this.serie = serie;
        this.context = context;

        this.fragments = createListFragment();
    }


    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";

        if (position == 0) {
            title = context.getResources().getString(R.string.tab_info);
        } else {
            title = context.getResources().getString(R.string.season)+ " " + position;
        }
        return title;
    }

    /**
     * Method to create fragment list
     */
    private ArrayList<Fragment> createListFragment(){

        //create list
        ArrayList<Fragment> fragments = new ArrayList<>();
        //create fragment with categories
        for(int i = 0; i < tabs; i++){
            //create fragment
            if (i==0) {
                //info fragment
                SerieInfoFragment fragment = new SerieInfoFragment();
                //set fragemnt info
                fragment.setPosition(i, serie);
                //add fragment to list
                fragments.add(fragment);

            } else {
                //season fragment
                SerieSeasonFragment fragment = new SerieSeasonFragment();
                //set fragemnt info
                fragment.setPosition(i, serie);
                //add fragment to list
                fragments.add(fragment);
            }
        }

        //return list
        return fragments;
    }
}
