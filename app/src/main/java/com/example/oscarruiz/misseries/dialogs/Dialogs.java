package com.example.oscarruiz.misseries.dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.oscarruiz.misseries.R;
import com.example.oscarruiz.misseries.activities.DetailSerie;
import com.example.oscarruiz.misseries.models.ResponseSerie;
import com.example.oscarruiz.misseries.utils.Constants;

import java.util.Calendar;

/**
 * Created by Oscar Ruiz on 04/07/2017.
 */

public class Dialogs extends Dialog {
    /**
     * Image dialog
     */
    private AlertDialog imageDialog;

    /**
     * Error dialog
     */
    private AlertDialog errorDialog;

    /**
     * Context
     */
    private Context context;

    public Dialogs(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    /**
     * Create Date Picker Dialog
     */
    public DatePickerDialog showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(context, (DatePickerDialog.OnDateSetListener)context, year, month, day);
    }

    /**
     * Connection error dialog
     */
    public AlertDialog showConnectionErrorDialog (final Activity activity) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //Inflate dialog xml
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.error_dialog, null);

        builder.setView(v);
        builder.setCancelable(false);

        //set views
        Button button = (Button)v.findViewById(R.id.accept);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reload activity
                Intent intent = activity.getIntent();
                activity.finish();
                activity.startActivity(intent);
            }
        });

        errorDialog = builder.create();
        return errorDialog;
    }

}
