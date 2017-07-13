package com.example.oscarruiz.misseries.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.oscarruiz.misseries.R;
import com.example.oscarruiz.misseries.adapters.SeriesListAdapter;
import com.example.oscarruiz.misseries.controllers.DataController;
import com.example.oscarruiz.misseries.dialogs.Dialogs;
import com.example.oscarruiz.misseries.interfaces.AppInterfaces;
import com.example.oscarruiz.misseries.models.Serie;
import com.example.oscarruiz.misseries.models.User;
import com.example.oscarruiz.misseries.session.Session;
import com.example.oscarruiz.misseries.utils.Animations;
import com.example.oscarruiz.misseries.utils.Constants;

import com.yydcdut.sdlv.SlideAndDragListView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    /**
     * Options menu
     */
    private PopupMenu popupMenu;

    /**
     * Menu button
     */
    private ImageButton menuButton;

    /**
     * Animations instance
     */
    private Animations animations;

    /**
     * Header Text View
     */
    private TextView headerTV;

    /**
     * Nick text view
     */
    private TextView nickTV;

    /**
     * Spinner
     */
    private Spinner spinner;

    /**
     * Array Adapter
     */
    private ArrayAdapter arrayAdapter;

    /**
     * Data controller instace
     */
    private DataController dataController;

    /**
     * List adapter
     */
    private SeriesListAdapter adapter;

    /**
     * User series list
     */
    private ArrayList<Serie> series;

    /**
     * Actual user
     */
    private User user;

    /**
     * Series Listview
     */
    private ListView listView;

    /**
     * Layout that shows there are no series registered
     */
    private LinearLayout noSeriesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Hide Action Bar
        getSupportActionBar().hide();

        //Check internet connection
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        Dialogs dialogs = new Dialogs(HomeActivity.this);
        if (netInfo == null || !netInfo.isConnected()) {
            AlertDialog dialog = dialogs.showConnectionErrorDialog(HomeActivity.this);
            dialog.show();
        } else {
            dataController = new DataController();

            getViews();
            getInfo();
            setListeners();
            startAnims();
        }
    }

    /**
     * Method to get views
     */
    private void getViews() {
        noSeriesLayout = (LinearLayout)findViewById(R.id.no_series_layout);
        listView = (ListView) findViewById(R.id.series_list_view);
        spinner = (Spinner)findViewById(R.id.spinner);
        nickTV = (TextView)findViewById(R.id.nick_text_view);
        headerTV = (TextView)findViewById(R.id.header);
        menuButton = (ImageButton)findViewById(R.id.menu_button);
    }

    /**
     * Method to get and set info
     */
    private void getInfo() {

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, Constants.SORT_BY);
        spinner.setAdapter(arrayAdapter);

        if (Session.getInstance().getUser() != null) {
            user = Session.getInstance().getUser();

            //show users nick on header
            nickTV.setText(user.getNick());

            //if user hasn´t registered series, show noSeriesLayout
            if (user.getSeries() == null) {
                noSeriesLayout.setVisibility(View.VISIBLE);
            } else {
                //take user series to show
                series = user.getSeries();
                //show series list
                adapter = new SeriesListAdapter(series, this, false, null);
                listView.setAdapter(adapter);
            }
        }

        //Create options menu
        popupMenu = new PopupMenu(HomeActivity.this, menuButton);
        popupMenu.getMenuInflater().inflate(R.menu.context_menu, popupMenu.getMenu());

//        registerForContextMenu(listView);
    }

    /**
     * Method to set listeners
     */
    private void setListeners() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //send position in bundle
                Bundle bundle = new Bundle();
                bundle.putInt("position", i);

                //show serie detail
                Intent intent = new Intent(HomeActivity.this, DetailSerie.class);
                intent.putExtras(bundle);
                startActivity(intent);

                // finish();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0: //Sort by name
                        if (series != null){
                            Collections.sort(series, new Comparator<Serie>() {
                                @Override
                                public int compare(Serie name1, Serie name2) {
                                    return  name1.getTitle().compareToIgnoreCase(name2.getTitle());
                                }
                            });
                            //Reload list
                            adapter = new SeriesListAdapter(series, HomeActivity.this, false, null);
                            listView.setAdapter(adapter);
                        }
                        break;
                    case 1:
                        //Convert String to date format
                        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        Collections.sort(series, new Comparator<Serie>() {
                            @Override
                            public int compare(Serie serie, Serie serie2) {
                                try {
                                    //replace / character by -
                                    String finalSerie = serie.getNewSeason().replace("/", "-");
                                    String finalSerie2 = serie2.getNewSeason().replace("/", "-");
                                    //Parse dateformat to string
                                    return dateFormat.parse(finalSerie).compareTo(dateFormat.parse(finalSerie2));
                                    //return dateFormat.parse(serie.getNewSeason()).compareTo(dateFormat.parse(serie2.getNewSeason()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    return 0;
                                }
                            }
                        });
                        //Reload list
                        adapter = new SeriesListAdapter(series, HomeActivity.this, false, null);
                        listView.setAdapter(adapter);

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.add_serie:
                                //change activity to addserie
                                startActivity(new Intent(HomeActivity.this, AddSerie.class));
                                break;
                            case R.id.delete_serie:
                                //create adapter with isDeleting true
                                adapter = new SeriesListAdapter(series, HomeActivity.this, true, new AppInterfaces.IPressDeleteSerie() {
                                    @Override
                                    public void isDeleting(int position) {
                                        //delete serie
                                        series.remove(position);

                                        //save series
                                        user.setSeries(series);
                                        Session.getInstance().setUser(user);

                                        dataController.saveUser(user);

                                        //reload list with isDeleting false
                                        adapter = new SeriesListAdapter(series, HomeActivity.this, false, null);
                                        listView.setAdapter(adapter);
                                    }
                                });
                                listView.setAdapter(adapter);
                                break;
                            case R.id.close_session:
                                //Show alert dialog
                                final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                                builder.setTitle(getString(R.string.close_session_title_dialog));
                                builder.setMessage(R.string.close_session_message_dialog);
                                builder.setPositiveButton(getString(R.string.dialog_ok_button), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //change preferences to not logged in
                                        SharedPreferences sharedPreferences = getSharedPreferences(Constants.USER_LOGGED, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean(Constants.USER_LOGGED, false);
                                        editor.commit();

                                        //go to login activity
                                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                });
                                builder.setNegativeButton(getString(R.string.dialog_cancel_button), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                break;
                        }
                        return true;
                    }
                });
            }
        });
    }

//    /**
//     * Method to set Menu
//     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuItem addSerie = menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, getResources().getString(R.string.add_serie));
//        MenuItem deleteSerie = menu.add(Menu.NONE, Menu.FIRST+1, Menu.NONE, getResources().getString(R.string.delete_serie));
//        MenuItem closeSession = menu.add(Menu.NONE, Menu.FIRST+2, Menu.NONE, getResources().getString(R.string.close_sesion));
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    /**
//     * Method to set actions when user choose an option from menu
//     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case 1: //Add serie
//                //change activity to addserie
//                startActivity(new Intent(HomeActivity.this, AddSerie.class));
//                break;
//
//            case 2: //Delete serie
//                //create adapter with isDeleting true
//                adapter = new SeriesListAdapter(series, this, true, new AppInterfaces.IPressDeleteSerie() {
//                    @Override
//                    public void isDeleting(int position) {
//                        //delete serie
//                        series.remove(position);
//
//                        //save series
//                        user.setSeries(series);
//                        Session.getInstance().setUser(user);
//
//                        dataController.saveUser(user);
//
//                        //reload list with isDeleting false
//                        adapter = new SeriesListAdapter(series, HomeActivity.this, false, null);
//                        listView.setAdapter(adapter);
//                    }
//                });
//                listView.setAdapter(adapter);
//                break;
//
//            case 3: //Close session
//                //Show alert dialog
//                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle(getString(R.string.close_session_title_dialog));
//                builder.setMessage(R.string.close_session_message_dialog);
//                builder.setPositiveButton(getString(R.string.dialog_ok_button), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //change preferences to not logged in
//                        SharedPreferences sharedPreferences = getSharedPreferences(Constants.USER_LOGGED, Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putBoolean(Constants.USER_LOGGED, false);
//                        editor.commit();
//
//                        //go to login activity
//                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
//                    }
//                });
//                builder.setNegativeButton(getString(R.string.dialog_cancel_button), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    /**
//     * Method to create Context Menu
//     */
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//
//        if (v.getId()==R.id.series_list_view) {
//            MenuInflater inflater = new MenuInflater(this);
//            inflater.inflate(R.menu.context_menu, menu);
//        }
//    }
//
//    /**
//     * Method manage menu item selected
//     */
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        //get item which has been long pressed
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//
//
//        switch (item.getItemId()) {
//            case R.id.button1_menu_opt1:
//                Toast.makeText(this, series.get(info.position).getTitle(), Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.button1_menu_opt2:
//                Toast.makeText(this, "opción 2", Toast.LENGTH_SHORT).show();
//                break;
//        }
//        return super.onContextItemSelected(item);
//    }

    /**
     * Method to load animations
     */
    private void startAnims() {
        animations = new Animations();
        animations.homeAnimations(nickTV, headerTV, listView, HomeActivity.this);
    }

    /**
     * Method to check users internet connection
     */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Check internet connection
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        Dialogs dialogs = new Dialogs(HomeActivity.this);
        if (netInfo == null || !netInfo.isConnected()) {
            AlertDialog dialog = dialogs.showConnectionErrorDialog(HomeActivity.this);
            dialog.show();
        } else {
            getInfo();
            startAnims();
        }
    }
}

