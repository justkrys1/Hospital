package com.example.helloAndroid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class BaseActivity extends Activity {

    String FILENAME = "bloodsugar_log.csv";
    public static final String EXT_FOLDERNAME = "/HealthApp/";
    public String DR_EMAIL = "", MY_EMAIL = "", SUBJECT = "", MESSAGE = "", START_TIME = "", END_TIME = "";
    public Boolean EMAIL_ME, EMAIL_DOC, DEFAULT_FORMULA;
    public int hospitalIcon, urgentIcon, userIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences("HealthAppPreferences", 0);
        DR_EMAIL = settings.getString("doctorEmail", "codespork@gmail.com");
        MY_EMAIL = settings.getString("myEmail", "justkrys1@yahoo.com.com");
        SUBJECT = settings.getString("bloodsugar", "Way too high!");
        MESSAGE = settings.getString("content", "I feel like crap!");
        START_TIME = settings.getString("start", "8:00");
        END_TIME = settings.getString("end", "11:00");
        EMAIL_ME = settings.getBoolean("emailMe", true);
        EMAIL_DOC = settings.getBoolean("emailDoc", true);
        DEFAULT_FORMULA = settings.getBoolean("DCCT", true);
        userIcon = R.drawable.yellow_point;
        hospitalIcon = R.drawable.red_point;
        urgentIcon = R.drawable.green_point;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mastermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.goToCalc:
                startActivity(new Intent(this, MyActivity.class));
                break;
            case R.id.goToLog:
                startActivity(new Intent(this, BloodsugarLog.class));

                break;
            case R.id.goToAlarm:
                startActivity(new Intent(this, Alarm.class));
                break;
            case R.id.goToChart:
                startActivity(new Intent(this, Achart.class));
                break;
            case R.id.goToPrefs:
                startActivity(new Intent(this, Preferences.class));
                break;
            case R.id.goToMap:
                startActivity(new Intent(this, Map.class));
                break;
            case R.id.goToHospital:
                startActivity(new Intent(this, Hospital.class));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void savePreferences() {
        SharedPreferences settings = getSharedPreferences("HealthAppPreferences", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("doctorEmail", DR_EMAIL);
        editor.putString("myEmail", MY_EMAIL);
        editor.putString("bloodsugar", SUBJECT);
        editor.putString("content", MESSAGE);
        editor.putString("start", START_TIME);
        editor.putString("end", END_TIME);
        editor.putBoolean("emailMe", EMAIL_ME);
        editor.putBoolean("emailDoc", EMAIL_DOC);
        editor.putBoolean("DCCT", DEFAULT_FORMULA);
        editor.commit();
    }

    public File copyFileToExternal(String fileName) {
        File file = null;
        String newPath = Environment.getExternalStorageDirectory() + EXT_FOLDERNAME;
        try {
            File f = new File(newPath);
            f.mkdirs();
            FileInputStream file_in = openFileInput(fileName);
            FileOutputStream file_out = new FileOutputStream(newPath + fileName);
            byte[] myBuffer = new byte[1024];
            int length = 0;
            while ((length = file_in.read(myBuffer)) != -1) {
                file_out.write(myBuffer, 0, length);
            }
            file_in.close();
            file_out.close();
            file = new File(newPath + fileName);
            if (file.exists())
                return file;
        } catch (Exception e) {
            toastIt("Failed to copy file!");
        }
        return null;
    }


    public void toastIt(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
