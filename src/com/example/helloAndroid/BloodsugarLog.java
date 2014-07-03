package com.example.helloAndroid;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BloodsugarLog extends BaseActivity {
    private Intent currentIntent;
    private EditText editTextLog, editTextDate, editTextTime, editTextNotes;
    //private CheckBox checkBoxAfterMeal, checkBoxBeforeMeal;
    //private boolean before = checkBoxBeforeMeal.isChecked();
    //private boolean after = checkBoxAfterMeal.isChecked();
    //private TextView textViewNotes;
    final Calendar c = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bloodsugarlog);
        initializeView();
    }

    private void initializeView() {
        editTextLog = (EditText) findViewById(R.id.editTextLog);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextTime = (EditText) findViewById(R.id.editTextTime);
        editTextNotes = (EditText) findViewById(R.id.editTextNotes);
        currentIntent = getIntent();
        //checkBoxAfterMeal = (CheckBox) findViewById(R.id.checkBoxAfterMeal);
        //checkBoxBeforeMeal = (CheckBox) findViewById(R.id.checkBoxBeforeMeal);
        Bundle extras = currentIntent.getExtras();
        if (extras != null) {
            editTextLog.setText(extras.getString("eAG"));

        }
        setCurrentDate();
    }


    public void goToCalc(View v) {
        finish();
    }
     public void setCurrentDate() {
        String dateFormat = ("MM-dd-yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        editTextDate.setText(sdf.format(c.getTime()));

        String timeFormat = ("hh:mm a");
        SimpleDateFormat stf = new SimpleDateFormat(timeFormat, Locale.US);
        editTextTime.setText(stf.format(c.getTime()));
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, monthOfYear);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setCurrentDate();
        }
    };

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            c.set(Calendar.MINUTE, minute);
            setCurrentDate();
        }
    };

    public void dateOnClick(View view) {
        new DatePickerDialog(BloodsugarLog.this, date, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

    }

    public void timeOnClick(View view) {
        new TimePickerDialog(BloodsugarLog.this, time, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false).show();
    }

    public void saveLogOnClick(View view) {

        String entry = editTextDate.getText().toString() + "," +
                editTextTime.getText().toString() + "," +
                editTextLog.getText().toString() + "\n";
        try {
            FileOutputStream out = openFileOutput(FILENAME, Context.MODE_APPEND);
            out.write(entry.getBytes());
            out.close();
            toastIt( getString(R.string.record) );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToChart(View view){
        Intent chart = new Intent(this, Chart.class);
        startActivity(chart);
    }

    public void goToAChart(View view){
        Intent aChart = new Intent(this, Achart.class);
        startActivity(aChart);
    }

}

