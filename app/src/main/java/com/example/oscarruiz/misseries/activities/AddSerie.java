package com.example.oscarruiz.misseries.activities;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.oscarruiz.misseries.R;
import com.example.oscarruiz.misseries.adapters.ViewPagerMatchesAdapter;
import com.example.oscarruiz.misseries.controllers.DataController;
import com.example.oscarruiz.misseries.controllers.SearchMatches;
import com.example.oscarruiz.misseries.dialogs.Dialogs;
import com.example.oscarruiz.misseries.interfaces.AppInterfaces;
import com.example.oscarruiz.misseries.models.ResponseSerie;
import com.example.oscarruiz.misseries.models.Serie;
import com.example.oscarruiz.misseries.models.User;
import com.example.oscarruiz.misseries.session.Session;
import com.example.oscarruiz.misseries.utils.Constants;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class AddSerie extends AppCompatActivity {

    /**
     * boolean to know if a serie was selected from viewpager
     */
    private boolean serieSelected;

    /**
     * Results found adding serie boolean
     */
    private boolean resultsFound;

    /**
     * Viewpager page selected
     */
    private int pageSelected;

    /**
     * Circle page indicator
     */
    private CircleIndicator indicator;

    /**
     * Viewpager adapter
     */
    private ViewPagerMatchesAdapter adapter;

    /**
     * Viewpager layout
     */
    private LinearLayout viewPagerLayout;

    /**
     * No results layout
     */
    private LinearLayout noResultsLayout;

    /**
     * id series list
     */
    private ArrayList<ResponseSerie> idSeriesList;

    /**
     * Show matches dialog
     */
    private Dialog matchesDialog;

    /**
     * Matched series list
     */
    private ArrayList<ResponseSerie> matches;

    /**
     * Dialog viewpager
     */
    private ViewPager viewPager;

    /**
     * Dialog select button
     */
    private Button select;

    /**
     * Dialog no select button
     */
    private Button noSelect;

    /**
     * Data controller instace
     */
    private DataController dataController;

    /**
     * series list
     */
    private ArrayList<Serie> series;

    /**
     * serie title
     */
    private String title;

    /**
     * serie season
     */
    private String season;

    /**
     * serie chapter
     */
    private String chapter;

    /**
     * Actual user
     */
    private User user;

    /**
     * Serie
     */
    private Serie serie;

    /**
     * Save Button
     */
    private Button save;

    /**
     * Cancel Button
     */
    private Button cancel;

    /**
     * title edit text
     */
    private EditText titleTV;

    /**
     * season edit text
     */
    private EditText seasonTV;

    /**
     * chapter edit text
     */
    private EditText chapterTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_serie);

        //Hide Action Bar
        getSupportActionBar().hide();

        dataController = new DataController();

        getInfo();
        getViews();
        setListeners();
    }

    /**
     * Method to get user
     */
    private void getInfo() {
        if (Session.getInstance().getUser() != null) {
            user = Session.getInstance().getUser();
            //get series
            series = user.getSeries();

            if (series == null) {
                //init series
                series = new ArrayList<Serie>();
            }
        }
    }

    /**
     * Method to get views
     */
    private void getViews() {
        titleTV = (EditText)findViewById(R.id.title);
        seasonTV = (EditText)findViewById(R.id.season);
        chapterTV = (EditText)findViewById(R.id.chapter);
        save = (Button)findViewById(R.id.save_button);
        cancel = (Button)findViewById(R.id.cancel_button);
    }

    /**
     * Methdo to set listeners
     */
    private void setListeners() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleTV.getText().toString().isEmpty()) {
                    Toast.makeText(AddSerie.this, getResources().getString(R.string.no_title_typed), Toast.LENGTH_SHORT).show();
                } else {
                    if (checkFields()) {
                        if (isAdded()) {
                            Toast.makeText(AddSerie.this, getResources().getString(R.string.serie_exist), Toast.LENGTH_SHORT).show();
                        } else {
                            //Check internet connection
                            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo netInfo = cm.getActiveNetworkInfo();

                            Dialogs dialogs = new Dialogs(AddSerie.this);
                            if (netInfo == null || !netInfo.isConnected()) {
                                android.support.v7.app.AlertDialog dialog = dialogs.showConnectionErrorDialog(AddSerie.this);
                                dialog.show();
                            } else {
                                showMatches();
                            }

                        }
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddSerie.this, HomeActivity.class));
                finish();
            }
        });
    }

    /**
     * Method to check if all fields are filled
     */
    private boolean checkFields() {
        if (titleTV.getText().toString().isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.no_title_typed), Toast.LENGTH_SHORT).show();
            return false;
//        } else if (chapterTV.getText().toString().isEmpty()) {
//            Toast.makeText(this, getResources().getString(R.string.no_chapter_typed), Toast.LENGTH_SHORT).show();
//            return false;
//        } else if (seasonTV.getText().toString().isEmpty()) {
//            Toast.makeText(this, getResources().getString(R.string.no_season_typed), Toast.LENGTH_SHORT).show();
//            return false;
        } else {
            return true;
        }
    }

    /**
     * Method to check if serie is already added
     */
    private boolean isAdded() {
        boolean added = false;

        //compare title typed with all series title added previously
        for (int i = 0; i<series.size(); i++) {
            if (series.get(i).getTitle().equalsIgnoreCase(titleTV.getText().toString())) {
                added = true;
            }
        }
        return added;
    }

    /**
     * Method to check is selected serie was added
     */
    private boolean isSelectedAdded() {
        boolean added = false;

        //compare title typed with all series title added previously
        for (int i = 0; i<series.size(); i++) {
            if (series.get(i).getTitle().equals(idSeriesList.get(pageSelected).getName())) {
                added = true;
            }
        }
        return added;
    }

    /**
     * Method to save serie into user
     */
    private boolean saveSerie() {
        //get info from edit text
        if (serieSelected) {
            title = idSeriesList.get(pageSelected).getName();
        } else {
            title = titleTV.getText().toString();
        }
        season = seasonTV.getText().toString();
        chapter = chapterTV.getText().toString();

        if (season.isEmpty()) {
            season = "0";
        }
        if (chapter.isEmpty()) {
            chapter = "0";
        }

        //save serie
        serie = new Serie(title, Integer.parseInt(season), Integer.parseInt(chapter));
        //default new season date to order series by date
        serie.setNewSeason(Constants.DEFAULT_DATE);

        //save serie id && serie poster
        if (serieSelected) {
            serie.setId(idSeriesList.get(pageSelected).getId());
            serie.setPosterURL(idSeriesList.get(pageSelected).getPoster());
        } else {
            serie.setId(Constants.DEFAULT_ID);
            serie.setPosterURL(Constants.DEFAULT_POSTER_URL);
        }

        series.add(serie);

        //save user
        user.setSeries(series);
        Session.getInstance().setUser(user);

        //save user in firebase
        dataController.saveUser(user);

        return true;
    }

    /**
     * Method to show matches
     */
    private void showMatches() {
        //matches array init
        matches = new ArrayList<>();

        //dialog configuration
        matchesDialog = new Dialog(this);
        matchesDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        matchesDialog.setCancelable(true);
        matchesDialog.setContentView(R.layout.add_serie_dialog);

        //set dialog views
        select = matchesDialog.findViewById(R.id.select_button);
        noSelect = matchesDialog.findViewById(R.id.no_select_button);
        viewPager = matchesDialog.findViewById(R.id.viewpager);
        viewPagerLayout = matchesDialog.findViewById(R.id.viewpager_layout);
        noResultsLayout = matchesDialog.findViewById(R.id.no_results_layout);
        indicator = matchesDialog.findViewById(R.id.indicator);

        //search matches
        new SearchMatches(titleTV.getText().toString(), new AppInterfaces.ISearchMatches() {
            @Override
            public void getMatches(ArrayList<ResponseSerie> matchesList) {
                idSeriesList = matchesList;

                if (matchesList == null || matchesList.size() == 0) {
                    resultsFound = false;
                    //show no results layout
                    viewPagerLayout.setVisibility(View.GONE);
                    noResultsLayout.setVisibility(View.VISIBLE);
                    select.setText(getResources().getString(R.string.try_again));
                    noSelect.setText(getResources().getString(R.string.no_matter));
                } else {
                    resultsFound = true;
                    //set viewpager info
                    adapter = new ViewPagerMatchesAdapter(AddSerie.this, matchesList);
                    viewPager.setAdapter(adapter);
                    indicator.setViewPager(viewPager);
                }

            }
        }).execute();

        //set listeners
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                pageSelected = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serieSelected = true;
                if (resultsFound) {
                    if (!isSelectedAdded()) {
                        //save serie id
                        if (saveSerie()) {
                            //return to home activity
                            startActivity(new Intent(AddSerie.this, HomeActivity.class));
                            //close dialog
                            matchesDialog.dismiss();
                            //close activity
                            finish();
                        }
                    } else {
                        Toast.makeText(AddSerie.this, getResources().getString(R.string.serie_exist), Toast.LENGTH_SHORT).show();
                        matchesDialog.dismiss();
                    }
                } else {
                    matchesDialog.dismiss();
                }
            }
        });

        noSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serieSelected = false;
                if (saveSerie()) {
                    //return to home activity
                    startActivity(new Intent(AddSerie.this, HomeActivity.class));
                    //close dialog
                    matchesDialog.dismiss();
                    //close activity
                    finish();
                }
            }
        });
        matchesDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(AddSerie.this, HomeActivity.class));
    }
}


