package com.example.helloAndroid;
import android.app.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.ToggleButton;
import android.app.AlarmManager;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MedicineAlarm extends BaseActivity {
    Intent alarmIntent;
    Integer alarmID;
    Context context;
    Calendar cal;
    private AlarmManager am;
    private Alarm currentAlarm;
    private ToggleButton toggleButton1, toggleButton2, toggleButton3, toggleButton4, toggleButton5, toggleButton6, toggleButton7, toggleButton8, toggleButton9, toggleButton10;
    private EditText editNotes1, editNotes2, editNotes3, editNotes4, editNotes5, editNotes6, editNotes7, editNotes8, editNotes9, editNotes10;
    private EditText dateText1, dateText2, dateText3, dateText4, dateText5, dateText6, dateText7, dateText8, dateText9, dateText10;
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
        this.toggleButton1 = toggleButton1;
        this.cal = cal;

        this.alarmIntent = new Intent( "com.example.helloAndroid" );
        alarmIntent.putExtra("notes", this.editNotes1.getText().toString());
        this.pi = PendingIntent.getBroadcast( context, this.alarmID,
                this.alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT );
        updateDateTime();
    }

    // Used to get the various edit fields and buttons associated with this alarm.
    public void setTags() {
        editNotes1.setTag( this );
        dateText1.setTag( this );
        toggleButton1.setTag( this );
    }

    public void setNotes( String notes ) {
        editNotes1.setText( notes );
        alarmIntent.putExtra( "notes", editNotes1.getText().toString() );
    }



    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.alarm );
        initializeApp();
    }

    private void initializeApp() {


       // alarms[0] = new Alarm( this, editNotes1, dateText1, toggleButton1, 0, Calendar.getInstance() );
        //alarms[0].setTags();

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
        if(toggleButton1.isChecked()){
            //am.set(AlarmManager.RTC, SystemClock.elapsedRealtime() + 3000, pi);
            am.set(AlarmManager.RTC, c.getTimeInMillis(), pi);
            toastIt("Alarm On:" + editNotes1.getText().toString());// + dateText1.getText().toString());
        }else{
            toastIt(("Alarm Off:" + editNotes1.getText().toString()));// + dateText1.getText().toString()));
        }
        if(toggleButton2.isChecked()){
            //am.set(AlarmManager.RTC, SystemClock.elapsedRealtime() + 3000, pi);
            am.set(AlarmManager.RTC, c.getTimeInMillis(), pi);
            toastIt("Alarm On:" + editNotes1.getText().toString());// + dateText1.getText().toString());
        }else{
            toastIt(("Alarm Off:" + editNotes1.getText().toString()));// + dateText1.getText().toString()));
        }
        if(toggleButton3.isChecked()){
            //am.set(AlarmManager.RTC, SystemClock.elapsedRealtime() + 3000, pi);
            am.set(AlarmManager.RTC, c.getTimeInMillis(), pi);
            toastIt("Alarm On:" + editNotes1.getText().toString());// + dateText1.getText().toString());
        }else{
            toastIt(("Alarm Off:" + editNotes1.getText().toString()));// + dateText1.getText().toString()));
        }
        if(toggleButton4.isChecked()){
            //am.set(AlarmManager.RTC, SystemClock.elapsedRealtime() + 3000, pi);
            am.set(AlarmManager.RTC, c.getTimeInMillis(), pi);
            toastIt("Alarm On:" + editNotes1.getText().toString());// + dateText1.getText().toString());
        }else{
            toastIt(("Alarm Off:" + editNotes1.getText().toString()));// + dateText1.getText().toString()));
        }
        if(toggleButton5.isChecked()){
            //am.set(AlarmManager.RTC, SystemClock.elapsedRealtime() + 3000, pi);
            am.set(AlarmManager.RTC, c.getTimeInMillis(), pi);
            toastIt("Alarm On:" + editNotes1.getText().toString());// + dateText1.getText().toString());
        }else{
            toastIt(("Alarm Off:" + editNotes1.getText().toString()));// + dateText1.getText().toString()));
        }
        if(toggleButton6.isChecked()){
            //am.set(AlarmManager.RTC, SystemClock.elapsedRealtime() + 3000, pi);
            am.set(AlarmManager.RTC, c.getTimeInMillis(), pi);
            toastIt("Alarm On:" + editNotes1.getText().toString());// + dateText1.getText().toString());
        }else{
            toastIt(("Alarm Off:" + editNotes1.getText().toString()));// + dateText1.getText().toString()));
        }
        if(toggleButton7.isChecked()){
            //am.set(AlarmManager.RTC, SystemClock.elapsedRealtime() + 3000, pi);
            am.set(AlarmManager.RTC, c.getTimeInMillis(), pi);
            toastIt("Alarm On:" + editNotes1.getText().toString());// + dateText1.getText().toString());
        }else{
            toastIt(("Alarm Off:" + editNotes1.getText().toString()));// + dateText1.getText().toString()));
        }
        if(toggleButton8.isChecked()){
            //am.set(AlarmManager.RTC, SystemClock.elapsedRealtime() + 3000, pi);
            am.set(AlarmManager.RTC, c.getTimeInMillis(), pi);
            toastIt("Alarm On:" + editNotes1.getText().toString());// + dateText1.getText().toString());
        }else{
            toastIt(("Alarm Off:" + editNotes1.getText().toString()));// + dateText1.getText().toString()));
        }
        if(toggleButton9.isChecked()){
            //am.set(AlarmManager.RTC, SystemClock.elapsedRealtime() + 3000, pi);
            am.set(AlarmManager.RTC, c.getTimeInMillis(), pi);
            toastIt("Alarm On:" + editNotes1.getText().toString());// + dateText1.getText().toString());
        }else{
            toastIt(("Alarm Off:" + editNotes1.getText().toString()));// + dateText1.getText().toString()));
        }
        if(toggleButton10.isChecked()){
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

           // am.cal.set( Calendar.YEAR, year );
           // am.cal.set( Calendar.MONTH, monthOfYear );
          //  am.cal.set( Calendar.DAY_OF_MONTH, dayOfMonth );

            timePicker.show();
        }
    };

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet( TimePicker view, int hour, int minute ) {
            Alarm am = (Alarm)currentAlarm;
          //  am.cal.set( Calendar.HOUR, hour );
          //  am.cal.set( Calendar.MINUTE, minute );
          //  am.updateDateTime();
        }
    };

    public void setCurrentDateOnView(){
        int month, day, year, hours, minutes;
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hours = c.get(Calendar.HOUR);
        minutes = c.get(Calendar.MINUTE);
        timePicker = new TimePickerDialog(MedicineAlarm.this, time, hours, minutes, false);
        updateDateTime();
    }

    public void updateDateTime(){
        String myFormat = "MM-dd-yy hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateText1.setText(sdf.format(c.getTime()));
    }

public void dateOnClick( View view ) {
Alarm am = (Alarm)view.getTag();
   // currentAlarm = am;
   //timePicker = new TimePickerDialog( MedicineAlarm.this, time,
     //                                  am.cal.get( Calendar.HOUR ),
   // am.cal.get( Calendar.MINUTE ), false );
    //new DatePickerDialog( MedicineAlarm.this, date,
    //                      am.cal.get( Calendar.YEAR ),
   // am.cal.get( Calendar.MONTH ),
    //        am.cal.get( Calendar.DAY_OF_MONTH ) ).show();
}

  //    if( v.getId() == toggleButton1.getId() ) {
//        if( toggleButton1.isChecked() ) {
//            alarms[0].setNotes( editNotes1.getText().toString() );
//            am.set( AlarmManager.RTC, c.getTimeInMillis(), alarms[0].pi );
//            toastIt( "Alarm On: " + alarms[0] );
//
//        } else {
//            toastIt( "Alarm Off: " + editNotes1.getText().toString() );
//        }
//    } else if( v.getId() == toggleButton2.getId() ) {
//        if( toggleButton2.isChecked() ) {
//            alarms[1].setNotes( editNotes2.getText().toString() );
//            am.set( AlarmManager.RTC, c.getTimeInMillis(), alarms[1].pi );
//            toastIt( "Alarm On: " + alarms[1] );
//
//        } else {
//            toastIt( "Alarm Off: " + editNotes1.getText().toString() );
//        }
//    }



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

/*
        toggleButton1 = (ToggleButton)findViewById( R.id.toggleButton1 );
        editNotes1 = (EditText)findViewById( R.id.editNotes1 );
        dateText1 = (EditText)findViewById( R.id.editText1 );
        toggleButton2 = (ToggleButton)findViewById( R.id.toggleButton2 );
        editNotes2 = (EditText)findViewById( R.id.editNotes2 );
        dateText2 = (EditText)findViewById( R.id.editText2 );
 toggleButton3 = (ToggleButton)findViewById( R.id.toggleButton3 );
        editNotes3 = (EditText)findViewById( R.id.editNotes3 );
        dateText3 = (EditText)findViewById( R.id.editText3 );
        toggleButton4 = (ToggleButton)findViewById( R.id.toggleButton4 );
        editNotes4 = (EditText)findViewById( R.id.editNotes4 );
        dateText4 = (EditText)findViewById( R.id.editText4 );
 toggleButton5 = (ToggleButton)findViewById( R.id.toggleButton5 );
        editNotes5 = (EditText)findViewById( R.id.editNotes5 );
        dateText5 = (EditText)findViewById( R.id.editText5 );
        toggleButton6 = (ToggleButton)findViewById( R.id.toggleButton6 );
        editNotes6 = (EditText)findViewById( R.id.editNotes6 );
        dateText6 = (EditText)findViewById( R.id.editText6 );
 toggleButton7 = (ToggleButton)findViewById( R.id.toggleButton7 );
        editNotes7 = (EditText)findViewById( R.id.editNotes7 );
        dateText7 = (EditText)findViewById( R.id.editText7 );
        toggleButton8 = (ToggleButton)findViewById( R.id.toggleButton8 );
        editNotes8 = (EditText)findViewById( R.id.editNotes8 );
        dateText8 = (EditText)findViewById( R.id.editText8 );
 toggleButton9 = (ToggleButton)findViewById( R.id.toggleButton9 );
        editNotes9 = (EditText)findViewById( R.id.editNotes9 );
        dateText9 = (EditText)findViewById( R.id.editText9 );
        toggleButton10 = (ToggleButton)findViewById( R.id.toggleButton10 );
        editNotes10 = (EditText)findViewById( R.id.editNotes10 );
        dateText10 = (EditText)findViewById( R.id.editText10 );





    }
*/

