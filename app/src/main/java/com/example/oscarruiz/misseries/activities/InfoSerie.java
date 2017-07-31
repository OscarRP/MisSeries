package com.example.oscarruiz.misseries.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.oscarruiz.misseries.R;
import com.example.oscarruiz.misseries.adapters.ViewPagerMatchesAdapter;
import com.example.oscarruiz.misseries.adapters.ViewPagerSerieInfo;
import com.example.oscarruiz.misseries.models.ResponseSerie;
import com.example.oscarruiz.misseries.session.Session;

public class InfoSerie extends AppCompatActivity {

    /**
     * View pager adapter
     */
    private ViewPagerSerieInfo adapter;

    /**
     * Serie selected
     */
    private ResponseSerie serie;

    /**
     * Viewpager
     */
    private ViewPager viewPager;

    /**
     * TabLayout
     */
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_serie);

        //Hide Action Bar
        getSupportActionBar().hide();

        getViews();
        setInfo();
        setListeners();
    }

    /**
     * Method to get views
     */
    private void getViews() {
        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        viewPager = (ViewPager)findViewById(R.id.viewpager);

    }

    /**
     * Method to set listeners
     */
    private void setListeners() {

    }

    /**
     * Method to set info
     */
    private void setInfo() {
        //take serie from session
        Session session = Session.getInstance();
        serie = session.getSerie();

        //set tabs
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_info)));
        //check serie seasons and add tab for each season
        for (int i=0;i<serie.getSeasons().size();i++) {
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.season) + " " + String.valueOf(i+1)));
        }

      //  adapter = new ViewPagerSerieInfo(InfoSerie.this.getSupportFragmentManager(), tabLayout.getId(), serie);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }
}
