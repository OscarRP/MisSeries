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
import com.example.oscarruiz.misseries.models.ResponseSerie;

import java.util.ArrayList;

/**
 * Created by Carlos Ruiz on 27/07/2017.
 */

public class ViewPagerSerieInfo extends FragmentPagerAdapter {

    /**
     * fragment list
     */
    private ArrayList<Fragment> fragments;

    /**
     * list of tabs
     */
    private ArrayList<Integer> tabs;

    public ViewPagerSerieInfo(FragmentManager fm, ArrayList<Integer> tabs, ResponseSerie serie) {
        super(fm);
        this.tabs = tabs;
        this.fragments = createListFragment();
    }


    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    /**
     * Method to create fragment list
     * @return
     */
    private ArrayList<Fragment> createListFragment(){

        //create list
        ArrayList<Fragment> fragments = new ArrayList<>();
        //create fragment with categories
        for(int i =0;i<tabs.size();i++){
            //create fragment
            SerieInfoFragment fragment = new SerieInfoFragment();
            //set fragemnt info
            //fragment.setFragemntInfo();
            //add fragment to list
            fragments.add(fragment);
        }

        //return list
        return fragments;
    }

//    /**
//     * tabLayout position
//     */
//    private int tabPosition;
//
//    /**
//     * Context
//     */
//    private Context context;
//
//    public ViewPagerSerieInfo(Context context, int tabPosition) {
//        this.tabPosition = tabPosition;
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return 0;
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == object;
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup pager, int position) {
//        //Actual view
//        final View view;
//
//        //inflate view
//        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = layoutInflater.inflate(R.layout.serie_info_item, null);
//
//        //get views
//        final LinearLayout infoLayout = (LinearLayout)view.findViewById(R.id.info_layout);
//        final LinearLayout seasonLayout = (LinearLayout)view.findViewById(R.id.season_layout);
//
//        if (position == 0) {
//            infoLayout.setVisibility(View.VISIBLE);
//            seasonLayout.setVisibility(View.GONE);
//        } else {
//            infoLayout.setVisibility(View.GONE);
//            seasonLayout.setVisibility(View.VISIBLE);
//        }
//
//        //add view to viewpager
//        pager.addView(view);
//
//        return view;
//    }
//
//    /**
//     * Method to delete viewpager view
//     */
//    @Override
//    public void destroyItem(ViewGroup pager, int position, Object object) {
//        pager.removeView((View) object);
//    }
}
