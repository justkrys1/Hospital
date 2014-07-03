package com.example.helloAndroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.*;

import java.text.DecimalFormat;
import java.text.ParseException;

public class MyActivity extends BaseActivity {
    private Button buttonCalc;
    private RadioGroup radioFormula;
    private RadioButton btnDCCT, btnADAG;
    private TextView bloodsugar, message;
    private boolean isDcct = true;
    public EditText a1cIn, glucoseIn;
    private Double eAG = 0.0;
    private Double a1c = 0.0;
    private Double glucose = 0.0;
    private Double glucose2ac1 = 0.0;
    private String convertA1C, convertGlucose;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initializeApp();
    }

    private void initializeApp() {

        a1cIn = (EditText) findViewById(R.id.a1cInput);
        glucoseIn = (EditText) findViewById(R.id.glucoseInput);
        bloodsugar = (TextView) findViewById(R.id.bloodsugar);
        radioFormula = (RadioGroup) findViewById(R.id.radioFormula);
        btnDCCT = (RadioButton) findViewById(R.id.btnDCCT);
        btnADAG = (RadioButton) findViewById(R.id.btnADAG);
        buttonCalc = (Button) findViewById(R.id.buttonCalc);
        buttonCalc.setOnClickListener(new View.OnClickListener() {
            @Override
//
            public void onClick(View v) {
                //Log.i("onClick", "Button clicked");
                calculatedEAG();
            }
        });

/*        a1cIn.setOnFocusChangeListener( new View.onFocusChangeListener(){
            //@Override
                public void onFocusChange(View v, boolean hasFocus){
                    handleOnFocusChange(v, hasFocus);
            }
        });
        glucoseIn.setOnFocusChangeListener( new View.onFocusChangeListener(){
            //@Override
            public void onFocusChange(View v, boolean hasFocus){
                handleOnFocusChange(v, hasFocus);
            }
        });
    }

    public void handleOnFocusChange( View v, boolean hasFocus){
        if(hasFocus){
            toastIt( v.toString() + " got the focus");
        }else{
            toastIt(v.toString() + " lost the focus");
        }*/
    }

    public void calculatedEAG() {
        try {
            convertA1C = a1cIn.getText().toString();
            a1c = Double.parseDouble(convertA1C);
            convertGlucose = glucoseIn.getText().toString();
            glucose = Double.parseDouble(convertGlucose);
            if (convertA1C == "") {
                a1c = 0.0;
            } else {
                a1c = Double.parseDouble(convertA1C);

            }

            if (convertGlucose == "") {
                glucose = 0.0;
            } else {
                glucose = Double.parseDouble(convertGlucose);
            }
        } catch (NumberFormatException e) {
            Log.d(getString(R.string.error), getString(R.string.parse) + glucoseIn.getText().toString());
            System.out.println(getString(R.string.noparse));
        }
        if (glucose > 0) {
            glucose2ac1 = (glucose + 100) / 36;
            a1c = 0.0;
        } else if (a1c > 0) {
            glucose2ac1 = a1c;
            glucose = 0.0;
        }

        if (isDcct) {
            eAG = (glucose2ac1 * 35.6) - 77.3;
        } else {
            eAG = (1.583 * glucose2ac1 - 2.52) * 18.05;
        }
        String result = String.format("%.0f", eAG);
        bloodsugar.setText(result);
    }

    public void radioCheck(View v) {
        //int selectedId = radioFormula.getCheckedRadioButtonId();
        //RadioButton selected = (RadioButton) findViewById(selectedId);
        //toastIt(selected.getText().toString());
        isDcct = btnDCCT.isChecked();
    }




    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("isDCCT", btnDCCT.isChecked());
        savedInstanceState.putString("a1cIn", a1cIn.getText().toString());
        savedInstanceState.putString("glucoseIn", glucoseIn.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isDcct = savedInstanceState.getBoolean("isDCCT");
        a1cIn.setText(savedInstanceState.getString("a1cIn"));
        glucoseIn.setText(savedInstanceState.getString("glucoseIn"));
    }

    public void goToLog(View v) {
        Intent sugarlog = new Intent(this, BloodsugarLog.class);
        sugarlog.putExtra("eAG", bloodsugar.getText().toString());
        startActivity(sugarlog);
    }

    public void goToChart(View view) {
        Intent aChart = new Intent(this, Achart.class);
        startActivity(aChart);
    }
    public void goToAlarm(View view) {
        Intent alarm = new Intent(this, Alarm.class);
        startActivity(alarm);
    }
}
