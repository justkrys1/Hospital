package com.example.helloAndroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.*;

import java.io.File;
import java.net.URI;

public class Preferences extends BaseActivity {


    public EditText editTextDocEmail, editTextMyEmail, editTextSubject, editTextMessage, editTextStartTime, editTextEndTime;
    public Button buttonSendEmail, buttonSavePrefs;
    public RadioButton radioButtonDefaultADAG, radioButtonDefaultDCCT;
    public CheckBox checkBoxEmailMe, checkBoxEmailDoc;
    public RadioGroup radioGroupDefaultFormula;
    public Boolean defaultDCCT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences);

        initializeApp();
    }

    private void initializeApp() {
        editTextDocEmail = (EditText) findViewById(R.id.editTextDocEmail);
        editTextDocEmail.setText(DR_EMAIL);
        editTextMyEmail = (EditText) findViewById(R.id.editTextMyEmail);
        editTextMyEmail.setText(MY_EMAIL);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextSubject.setText(SUBJECT);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);
        editTextMessage.setText(MESSAGE);
        editTextStartTime = (EditText) findViewById(R.id.editTextStartTime);
        editTextStartTime.setText(START_TIME);
        editTextEndTime = (EditText) findViewById(R.id.editTextEndTime);
        editTextEndTime.setText(END_TIME);
        buttonSavePrefs = (Button) findViewById(R.id.buttonSavePrefs);
        buttonSendEmail = (Button) findViewById(R.id.buttonSendEmail);
        radioGroupDefaultFormula = (RadioGroup) findViewById(R.id.radioGroupDefaultFormula);
        radioButtonDefaultADAG = (RadioButton) findViewById(R.id.radioButtonDefaultADAG);
        radioButtonDefaultDCCT = (RadioButton) findViewById(R.id.radioButtonDefaultDCCT);
        checkBoxEmailDoc = (CheckBox) findViewById(R.id.checkBoxEmailDoc);
        checkBoxEmailMe = (CheckBox) findViewById(R.id.checkBoxEmailMe);
    }

    public void savePrefs(View v) {
        DR_EMAIL = editTextDocEmail.getText().toString();
        MY_EMAIL = editTextMyEmail.getText().toString();
        SUBJECT = editTextSubject.getText().toString();
        MESSAGE = editTextMessage.getText().toString();
        START_TIME = editTextStartTime.getText().toString();
        END_TIME = editTextEndTime.getText().toString();
        EMAIL_ME = checkBoxEmailMe.isChecked();
        EMAIL_DOC = checkBoxEmailDoc.isChecked();
        DEFAULT_FORMULA = radioButtonDefaultDCCT.isChecked();
        savePreferences();
        toastIt("Preferences saved.");
    }

    public void defaultFormulaCheck(View v) {
        defaultDCCT = radioButtonDefaultDCCT.isChecked();
    }


    public void sendEmail(View v){
       try{
           String email = MY_EMAIL;
           String drEmail = DR_EMAIL;
           String subject = SUBJECT;
           String message = MESSAGE;
           File attachment = copyFileToExternal(FILENAME);
          // File attachment = new File(Environment.getExternalStorageDirectory() + EXT_FOLDERNAME + FILENAME);
           Uri path = Uri.fromFile(attachment);
           final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
           emailIntent.setType(("plain/text"));
           emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
           emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
           emailIntent.putExtra(Intent.EXTRA_STREAM, path);
           emailIntent.putExtra(Intent.EXTRA_TEXT, message);

           startActivityForResult(Intent.createChooser(emailIntent, "Send email..."), 4520);
       }catch(Throwable t){
           toastIt("Request denied!" + t.toString());
       }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 4520 ){
            File file = new File(Environment.getExternalStorageDirectory() + EXT_FOLDERNAME + FILENAME);
            //will send attachemnt without this but not with file.delete();
        }
    }

}
