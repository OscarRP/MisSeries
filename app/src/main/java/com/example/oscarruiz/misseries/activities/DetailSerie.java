package com.example.oscarruiz.misseries.activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.style.DynamicDrawableSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.oscarruiz.misseries.R;
import com.example.oscarruiz.misseries.controllers.DataController;
import com.example.oscarruiz.misseries.dialogs.Dialogs;
import com.example.oscarruiz.misseries.interfaces.AppInterfaces;
import com.example.oscarruiz.misseries.models.ResponseSerie;
import com.example.oscarruiz.misseries.models.Serie;
import com.example.oscarruiz.misseries.models.User;
import com.example.oscarruiz.misseries.session.Session;
import com.example.oscarruiz.misseries.utils.Animations;
import com.example.oscarruiz.misseries.utils.Constants;

import com.example.oscarruiz.misseries.controllers.SearchSerie;
import com.example.oscarruiz.misseries.controllers.SearchDetailSerie;

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class DetailSerie extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    /**
     * Loading dialog
     */
    private Dialog loading;

    /**
     * boolean indicates if zoomImageView is showing
     */
    private Boolean isImageZoom;

    /**
     * Animations
     */
    private Animations animations;

    /**
     * Image View zoom
     */
    private ImageView zoomImageView;

    /**
     * Content layout
     */
    private LinearLayout contentLayout;

    /**
     * Add to calendar checkbox
     */
    private  AppCompatCheckBox addCalendarCB;

    /**
     * Add to calendar layout
     */
    private LinearLayout addCalendarLayout;

    /**
     * No info from Api layout
     */
    private LinearLayout noInfoLayout;

    /**
     * Resonse serie
     */
    private ResponseSerie detailSerie;

    /**
     * Serie ended text view
     */
    private TextView serieEndedTV;

    /**
     * Poster imageView
     */
    private ImageView posterIV;

    /**
     * Year text view
     */
    private TextView yearTV;

    /**
     * Genre text View
     */
    private  TextView genreTV;

    /**
     * Duration Text View
     */
    private TextView durationTV;

    /**
     * Seasons text view
     */
    private TextView seasonsTV;
    /**
     * Info serie layout
     */
    private LinearLayout infoSerieLayout;

    /**
     * Data controller instance
     */
    private DataController dataController;

    /**
     * User instance
     */
    private User user;

    /**
     * Series instance
     */
    private ArrayList<Serie> series;

    /**
     * Serie title
     */
    private TextView titleTV;

    /**
     * Decrease season button
     */
    private Button decreaseSeasonButton;

    /**
     * Increase season Button
     */
    private Button increaseSeasonButton;

    /**
     * Actual season
     */
    private TextView seasonTV;

    /**
     * Decrease Chapter button
     */
    private Button decreaseChapterButton;

    /**
     * Increase Chapter button
     */
    private Button increaseChapterButton;

    /**
     * Last chapter seen
     */
    private TextView lastChapterTV;

    /**
     * Calendar Button
     */
    private Button calendarButton;

    /**
     * Date next season
     */
    private TextView nextSeasonTV;

    /**
     * Accept button
     */
    private Button accept;

    /**
     * Search button
     */
    private ImageButton searchButton;

    /**
     * Season
     */
    private int season;

    /**
     * Chapter
     */
    private int chapter;

    /**
     * Next Season date
     */
    private String date;

    /**
     * Dialogs instance
     */
    private Dialogs dialog;

    /**
     * Date Picker dialog
     */
    private DatePickerDialog datePickerDialog;

    /**
     * Serie to show details
     */
    private Serie serie;

    /**
     * list position pressed
     */
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_serie);

        //Hide Action Bar
        getSupportActionBar().hide();

        //Check internet connection
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        Dialogs dialogs = new Dialogs(DetailSerie.this);
        if (netInfo == null || !netInfo.isConnected()) {
            AlertDialog dialog = dialogs.showConnectionErrorDialog(DetailSerie.this);
            dialog.show();
        } else {
            dataController = new DataController();

            getViews();
            getInfo();
            setListeners();
        }
    }

    /**
     * Method to get views
     */
    private void getViews() {

        titleTV = (TextView)findViewById(R.id.title_text_view);
        decreaseSeasonButton = (Button)findViewById(R.id.decrease_season_button);
        increaseSeasonButton = (Button)findViewById(R.id.increase_season_button);
        seasonTV = (TextView)findViewById(R.id.season);
        decreaseChapterButton = (Button)findViewById(R.id.decrease_chapter_button);
        increaseChapterButton = (Button)findViewById(R.id.increase_chapter_button);
        lastChapterTV = (TextView)findViewById(R.id.chapter);
        calendarButton = (Button)findViewById(R.id.calendar_button);
        nextSeasonTV = (TextView)findViewById(R.id.next_season_text_view);
        accept = (Button)findViewById(R.id.save_button);
        searchButton = (ImageButton)findViewById(R.id.search_button);
        infoSerieLayout = (LinearLayout)findViewById(R.id.info_serie_layout);
        noInfoLayout = (LinearLayout)findViewById(R.id.no_info_layout);
        yearTV = (TextView)findViewById(R.id.year_text_view);
        genreTV = (TextView)findViewById(R.id.genre_text_view);
        durationTV = (TextView)findViewById(R.id.duration_text_view);
        seasonsTV = (TextView)findViewById(R.id.seasons_text_view);
        posterIV = (ImageView)findViewById(R.id.poster_imageview);
        addCalendarLayout = (LinearLayout)findViewById(R.id.add_calendar_layout);
        addCalendarCB = (AppCompatCheckBox)findViewById(R.id.add_to_calendar_check);
        serieEndedTV = (TextView)findViewById(R.id.serie_ended);
        contentLayout = (LinearLayout)findViewById(R.id.contentLayout);
        zoomImageView = (ImageView)findViewById(R.id.zoomImage);
    }

    /**
     * Method to get serie info
     */
    private void getInfo() {


        isImageZoom = false;
        position = getIntent().getExtras().getInt("position");
        serie = Session.getInstance().getUser().getSeries().get(position);

        //init views
        titleTV.setText(serie.getTitle());
        seasonTV.setText(String.valueOf(serie.getSeason()));
        lastChapterTV.setText(String.valueOf(serie.getChapter()));

        //init season and chapter
        season = serie.getSeason();
        chapter = serie.getChapter();

        if (serie.getNewSeason().equals(Constants.DEFAULT_DATE)) {
            nextSeasonTV.setText(getResources().getString(R.string.no_date_new_season_defined));
            addCalendarLayout.setVisibility(View.GONE);
        } else {
            nextSeasonTV.setText(serie.getNewSeason());
            addCalendarLayout.setVisibility(View.VISIBLE);
        }

        //init user and series
        user = Session.getInstance().getUser();
        series = user.getSeries();
        date = serie.getNewSeason();

        if (serie.getId() == Constants.DEFAULT_ID) {
            noInfoLayout.setVisibility(View.VISIBLE);
            infoSerieLayout.setVisibility(View.GONE);
        } else {
//            Dialogs dialogs = new Dialogs(DetailSerie.this);
//            loading = dialogs.loadingDialog();
//            loading.show();
            new SearchDetailSerie(serie.getId(), new AppInterfaces.ISearchDetailSerie() {
                @Override
                public void getDetailSerie(ResponseSerie responseSerie) {
//                    loading.dismiss();
                    if (responseSerie != null) {

                        detailSerie = responseSerie;
                        titleTV.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                        //set serie info
                        if (responseSerie.getAirDate() != null) {
                            yearTV.setText(String.valueOf(responseSerie.getAirDate().substring(0,4)));
                        } else {
                            yearTV.setText(getResources().getString(R.string.without_info));
                        }
                        if (responseSerie.getEpisodeRunTime() != null && responseSerie.getEpisodeRunTime().size() > 0) {
                            durationTV.setText(String.valueOf(responseSerie.getEpisodeRunTime().get(0)));
                        } else {
                            durationTV.setText(getResources().getString(R.string.without_info));
                        }
                        if (responseSerie.getSeasons() != null) {
                            seasonsTV.setText(String.valueOf(responseSerie.getSeasons().size()));
                        } else {
                            seasonsTV.setText(getResources().getString(R.string.without_info));
                        }
                        if (responseSerie.getPoster() != null) {
                            //set poster
                            Glide.with(DetailSerie.this).load(Constants.SERIE_POSTER+responseSerie.getPoster()).into(posterIV);
                            Glide.with(DetailSerie.this).load(Constants.SERIE_POSTER+responseSerie.getPoster()).into(zoomImageView);
                        } else {
                            posterIV.setImageResource(R.mipmap.no_image_available);
                        }

                        //set serie genres
                        if (responseSerie.getGenres() != null){
                            String genres = "";
                            for (int i=0; i<responseSerie.getGenres().size(); i++) {
                                genres =  genres + responseSerie.getGenres().get(i).getGenre() + " ";
                            }
                            genreTV.setText(genres);
                        }

                        //set status serie if its ended
                        if (responseSerie.getStatus().equals(Constants.SERIE_STATUS_ENDED)) {
                            serieEndedTV.setVisibility(View.VISIBLE);
                        } else {
                            serieEndedTV.setVisibility(View.GONE);
                        }
                    }
                }
            }).execute();

        }
    }

    /**
     * Method to set listeners
     */
    private void setListeners() {

        decreaseSeasonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (season > 1){
                    season = season - 1;
                    seasonTV.setText(String.valueOf(season));
                    //set chapter to 0 when season changes
                    chapter = 0;
                    lastChapterTV.setText(String.valueOf(chapter));
                }
            }
        });

        increaseSeasonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check season number
                if (Integer.valueOf(seasonTV.getText().toString()) < detailSerie.getSeasons().size()) {
                    season = season + 1;
                    seasonTV.setText(String.valueOf(season));
                    //set chapter to 0 when season changes
                    chapter = 0;
                    lastChapterTV.setText(String.valueOf(chapter));
                }
            }
        });

        decreaseChapterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chapter > 0) {
                    chapter = chapter - 1;
                    lastChapterTV.setText(String.valueOf(chapter));
                }
            }
        });

        increaseChapterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check number of chapters of actual season selected
                if (detailSerie.getSeasons().size() > 0) {
                    if (Integer.valueOf(lastChapterTV.getText().toString()) < detailSerie.getSeasons().get(Integer.valueOf(seasonTV.getText().toString())-1).getEpisodeCount()) {
//                    Log.i("PRUEBA", "season id." +detailSerie.getSeasons().get(Integer.valueOf(seasonTV.getText().toString())).getId());
                        chapter = chapter + 1;
                        lastChapterTV.setText(String.valueOf(chapter));
                    }
                }
            }
        });

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show date picker dialog
                dialog = new Dialogs(DetailSerie.this);
                datePickerDialog = dialog.showDatePickerDialog();

                datePickerDialog.show();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save changes
                serie.setSeason(season);
                serie.setChapter(chapter);
                serie.setNewSeason(date);

                //save serie in session
                series.set(position, serie);
                user.setSeries(series);
                Session.getInstance().setUser(user);

//                //save user in firebase
//                dataController.saveUser(user);

                if (addCalendarCB.isChecked()) {
                    //put date in calendar
                    setCalendar();
                } else {
                    //save user in firebase
                    dataController.saveUser(user);
                    //go back
                    startActivity(new Intent(DetailSerie.this, HomeActivity.class));
                    finish();
                }

            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Copy serie title to clipboard
                ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(getResources().getString(R.string.copy_to_clipboard), serie.getTitle());
                clipboard.setPrimaryClip(clip);

                //go to webView Activity and send imdbId
                Intent intent = new Intent(DetailSerie.this, WebViewActivity.class);

                if (detailSerie != null && detailSerie.getId() != Constants.DEFAULT_ID) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.SERIE_ID, String.valueOf(detailSerie.getId()));
                    intent.putExtras(bundle);
                }
                startActivity(intent);
            }
        });

        posterIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (detailSerie.getPoster() != null) {
                    //scale and center image
                    animations = new Animations();
                    animations.zoomImageAnimationsIn(DetailSerie.this, contentLayout, zoomImageView);
                    isImageZoom = true;
                }
            }
        });

        zoomImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isImageZoom = false;
                animations = new Animations();
                animations.zoomImageAnimationsOut(DetailSerie.this, contentLayout, zoomImageView);
            }
        });

        titleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save selected serie in session
                Session session = Session.getInstance();
                session.setSerie(detailSerie);
                //go to info serie
                startActivity(new Intent(DetailSerie.this, InfoSerie.class));
            }
        });
    }

    /**
     * Method to add next season date on calendar
     */
    private void setCalendar() {

        //Check permissions
        if (ContextCompat.checkSelfPermission(DetailSerie.this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            //create string dates
            String dateStartString = date + " " + Constants.CALENDAR_START_TIME + " PM";
            String dateEndString = date + " " + Constants.CALENDAR_END_TIME + " PM";

            //set date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
            Date convertedStartDate = new Date();
            Date convertedEndDate = new Date();

            //convert string dates to formatted dates
            try {
                convertedStartDate = dateFormat.parse(dateStartString);
                convertedEndDate = dateFormat.parse(dateEndString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //create title
            String title = getString(R.string.add_calendar_title) +" " + serie.getTitle();

            //configure data to set in calendar
            Intent intent = new Intent();

            intent.setAction(Intent.ACTION_INSERT);
            intent.setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.Events.TITLE, title)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, convertedStartDate.getTime())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, convertedEndDate.getTime());

            //open calendar
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, Constants.CALENDAR_REQUEST_CODE);
            }
        } else {
            requestCalendarPermission();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.CALENDAR_REQUEST_CODE) {
               //save user in firebase
               dataController.saveUser(user);

               //go back
               startActivity(new Intent(DetailSerie.this, HomeActivity.class));
               finish();
        }
    }

    /**
     * Method to request write calendar permission
     */
    private void requestCalendarPermission() {
        if (ContextCompat.checkSelfPermission(DetailSerie.this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DetailSerie.this, new String[]{Manifest.permission.WRITE_CALENDAR}, Constants.CALENDAR_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == Constants.CALENDAR_PERMISSION_REQUEST_CODE) {
                setCalendar();
            }
        }
    }

    /**
     *Method to set next season date format
     */
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        nextSeasonTV.setText(dayOfMonth+"/"+(month+1)+"/"+year);
        date = nextSeasonTV.getText().toString();

        //show add to calendar layout
        addCalendarLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (isImageZoom) {
            animations = new Animations();
            animations.zoomImageAnimationsOut(DetailSerie.this, contentLayout, zoomImageView);
            isImageZoom = false;
        } else {
            super.onBackPressed();
            //startActivity(new Intent(DetailSerie.this, HomeActivity.class));
        }
    }
}
