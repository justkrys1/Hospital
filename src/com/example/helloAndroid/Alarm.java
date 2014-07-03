package com.example.helloAndroid;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.app.*;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.app.AlarmManager;

public class Alarm extends BaseActivity{
    Intent alarmIntent;
    Integer alarmID;
    Context context;
    Calendar cal;
    private Alarm currentAlarm;
    private Switch switch1, switch2, switch3, switch4, switch5, switch6, switch7, switch8, switch9, switch10;
    private EditText editNotes1, editNotes2, editNotes3, editNotes4, editNotes5, editNotes6, editNotes7, editNotes8, editNotes9, editNotes10;
    private EditText dateText1, dateText2, dateText3, dateText4, dateText5, dateText6, dateText7, dateText8, dateText9, dateText10;

    private AlarmManager am;
    private PendingIntent pi;
    private BroadcastReceiver br;
    TimePickerDialog timePicker;
    private Alarm[] alarms = new Alarm[10];
    Calendar c = Calendar.getInstance();
    public void Alarm( Context context,
                       EditText editNotes1,
                       EditText dateText1,
                       ToggleButton toggleButton1,
                       Integer alarmID,
                       Calendar cal ) {
        this.alarmID = alarmID;
        this.context = context;
        this.editNotes1 = editNotes1;
        this.dateText1 = dateText1;
        this.switch1 = switch1;
        this.cal = cal;

        this.alarmIntent = new Intent( "com.example.helloAndroid" );
        alarmIntent.putExtra("notes", this.editNotes1.getText().toString());
        this.pi = PendingIntent.getBroadcast( context, this.alarmID,
                this.alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT );
        updateDateTime();
    }
    public void setTags() {
        editNotes1.setTag( this );
        dateText1.setTag( this );
        switch1.setTag( this );
    }

    public void setNotes( String notes ) {
        editNotes1.setText( notes );
        alarmIntent.putExtra( "notes", editNotes1.getText().toString() );
    }
    @Override

    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.alarm );
        initializeApp();
    }

    private void initializeApp() {
        switch1 = (Switch)findViewById(R.id.switch1);
        switch2 = (Switch)findViewById(R.id.switch2);
        switch3 = (Switch)findViewById(R.id.switch3);
        switch4 = (Switch)findViewById(R.id.switch4);
        switch5 = (Switch)findViewById(R.id.switch5);
        switch6 = (Switch)findViewById(R.id.switch6);
        switch7 = (Switch)findViewById(R.id.switch7);
        switch8 = (Switch)findViewById(R.id.switch8);
        switch9 = (Switch)findViewById(R.id.switch9);
        switch10 = (Switch)findViewById(R.id.switch10);
        editNotes1 = (EditText)findViewById(R.id.editNotes1);
        editNotes2 = (EditText)findViewById(R.id.editNotes2);
        editNotes3 = (EditText)findViewById(R.id.editNotes3);
        editNotes4 = (EditText)findViewById(R.id.editNotes4);
        editNotes5 = (EditText)findViewById(R.id.editNotes5);
        editNotes6 = (EditText)findViewById(R.id.editNotes6);
        editNotes7 = (EditText)findViewById(R.id.editNotes7);
        editNotes8 = (EditText)findViewById(R.id.editNotes8);
        editNotes9 = (EditText)findViewById(R.id.editNotes9);
        editNotes10 = (EditText)findViewById(R.id.editNotes10);
        dateText1 = (EditText)findViewById(R.id.dateText1);
        dateText2 = (EditText)findViewById(R.id.dateText2);
        dateText3 = (EditText)findViewById(R.id.dateText3);
        dateText4 = (EditText)findViewById(R.id.dateText4);
        dateText5 = (EditText)findViewById(R.id.dateText5);
        dateText6 = (EditText)findViewById(R.id.dateText6);
        dateText7 = (EditText)findViewById(R.id.dateText7);
        dateText8 = (EditText)findViewById(R.id.dateText8);
        dateText9 = (EditText)findViewById(R.id.dateText9);
        dateText10 = (EditText)findViewById(R.id.dateText10);

        br = new BroadcastReceiver() {
            @Override
            public void onReceive( Context context, Intent intent ) {
                String notes = "";
                Bundle extras = intent.getExtras();
                if( extras != null ) {
                    notes = extras.getString( "notes" );
                }
                toastIt( "Wake UP: " + notes );
                createNotification( notes );
            }
        };
        setCurrentDateOnView();
    }

    public void toggleAlarm( View v ) {
        String notesText = editNotes1.getText().toString();
        registerReceiver( br, new IntentFilter( "com.example.helloAndroid" ));
        Intent alarmIntent = new Intent( "com.example.helloAndroid" );
        alarmIntent.putExtra("notes", notesText);
        pi = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        am = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));
        if(switch1.isChecked()){
            //am.set(AlarmManager.RTC, SystemClock.elapsedRealtime() + 3000, pi);
            am.set(AlarmManager.RTC, c.getTimeInMillis(), pi);
            toastIt("Alarm On:" + editNotes1.getText().toString());// + dateText1.getText().toString());
        }else{
            toastIt(("Alarm Off:" + editNotes1.getText().toString()));// + dateText1.getText().toString()));
        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet( DatePicker view, int year, int monthOfYear, int dayOfMonth ) {
            Alarm am = (Alarm)currentAlarm;

             am.cal.set( Calendar.YEAR, year );
             am.cal.set( Calendar.MONTH, monthOfYear );
             am.cal.set( Calendar.DAY_OF_MONTH, dayOfMonth );

            timePicker.show();
        }
    };

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet( TimePicker view, int hour, int minute ) {
            Alarm am = (Alarm)currentAlarm;
              am.cal.set( Calendar.HOUR, hour );
              am.cal.set( Calendar.MINUTE, minute );
              am.updateDateTime();
        }
    };

    public void setCurrentDateOnView(){
        int month, day, year, hours, minutes;
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hours = c.get(Calendar.HOUR);
        minutes = c.get(Calendar.MINUTE);
        timePicker = new TimePickerDialog(Alarm.this, time, hours, minutes, false);
        //datePicker = new DatePickerDialog(Alarm.this, date, year, month, false);
        updateDateTime();
    }

    public void updateDateTime(){
        String myFormat = "MM-dd-yy hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateText1.setText(sdf.format(c.getTime()));
        dateText2.setText(sdf.format(c.getTime()));
        dateText3.setText(sdf.format(c.getTime()));
        dateText4.setText(sdf.format(c.getTime()));
        dateText5.setText(sdf.format(c.getTime()));
        dateText6.setText(sdf.format(c.getTime()));
        dateText7.setText(sdf.format(c.getTime()));
        dateText8.setText(sdf.format(c.getTime()));
        dateText9.setText(sdf.format(c.getTime()));
        dateText10.setText(sdf.format(c.getTime()));
    }

    public void dateOnClick( View view ) {
        Alarm am = (Alarm)view.getTag();
         currentAlarm = am;
        timePicker = new TimePickerDialog( Alarm.this, time,
                                          am.cal.get( Calendar.HOUR ),
         am.cal.get( Calendar.MINUTE ), false );
        new DatePickerDialog( Alarm.this, date,
                              am.cal.get( Calendar.YEAR ),
         am.cal.get( Calendar.MONTH ),
                am.cal.get( Calendar.DAY_OF_MONTH ) ).show();
    }

    private void createNotification( String notes ) {
        Intent myIntent = new Intent( this, MedicineAlarm.class );
        PendingIntent pendingIntent = PendingIntent.getActivity( this, 0, myIntent, 0 );
        Notification myNotifier = new Notification.Builder( this )
                .setContentTitle( "Medicine Alarm" )
                .setContentText( notes )
                .setSmallIcon( R.drawable.ic_alarm )
                .setContentIntent( pendingIntent )
                .setAutoCancel( true )
                        //.addAction( R.drawable.ic_launcher, "Call", pendingIntent )
                .addAction( R.drawable.ic_launcher, "More", pendingIntent )
                .build();
        NotificationManager notificationManager = (NotificationManager)getSystemService( NOTIFICATION_SERVICE );
        notificationManager.notify( 0, myNotifier );
    }

    @Override
    protected void onDestroy() {
        am.cancel( pi );
        unregisterReceiver( br );
        super.onDestroy();
    }
}
