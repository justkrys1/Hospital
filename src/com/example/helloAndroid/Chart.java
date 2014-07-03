package com.example.helloAndroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.example.helloAndroid.R;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Chart extends BaseActivity {
    ArrayList<Integer> bloodsugarList = new ArrayList<Integer>();
    private Intent newIntent;
    private TextView textViewY;
    Paint paint;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        readBloodsugarLog();
        setContentView(R.layout.chart);
        initializeChart();
    }

    private void initializeChart() {
        paint = new Paint();
        paint.setColor(Color.CYAN);
        paint.setStrokeWidth(2);
        paint.setTextSize(20);
        //paint.setStyle(Paint.Style.STROKE);

        setContentView(new Panel(this));

    }

    class Panel extends View {
        public Panel(Context context) {
            super(context);
        }

        @Override
        public void onDraw(Canvas canvas) {

            int originX = 10, originY = 800;
            canvas.drawColor(Color.LTGRAY);
            //canvas.drawCircle(300, 80, 30, paint);//(300 = x position, 80 = y position, 30 = radius)

            canvas.drawText("" + canvas.getWidth() + ", " + canvas.getHeight(), 0, 200, paint);
            canvas.drawLine(originX, originY, 600, originY, paint);
            canvas.drawLine(originX, originY, originX, originY - 600, paint);

            //x = date y = bloodsugar
            //(10, 200)  (20, 210)  (30, 180) points, draw from one point to the next
            //canvas.drawLine(originX + 10, originY - 200, originX + 20, originY - 210, paint);//point 1 to point 2
            //canvas.drawLine(originX + 20, originY - 210, originX + 30, originY - 180, paint);//point 2 to point 3, etc.
//loop to go through all the entries, create the points, and draw the lines

            int currentY = bloodsugarList.get(0);
            int currentX = 10;
            for (Integer sugar : bloodsugarList) {
                canvas.drawLine(
                        currentX + originX, originY - currentY,
                        currentX + 10 + originX, originY - sugar,
                        paint);//point 1 to point 2
                currentX += 10;
                currentY = sugar;
            }

        }
    }

    private void readBloodsugarLog() {
        String temp;
        String logArray[];
        FileInputStream inputStream = null;

        try {
            inputStream = openFileInput(FILENAME);
            byte[] reader = new byte[inputStream.available()];
            while (inputStream.read(reader) != -1) {
            }


            Scanner scan = new Scanner(new String(reader));
            scan.useDelimiter("\\n");
            while (scan.hasNext()) {
                temp = scan.next();
                logArray = temp.split(",");
                bloodsugarList.add(Integer.parseInt(logArray[2]));

            }
            scan.close();

        } catch (Exception e) {
            Log.e(getString(R.string.chart), e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(getString(R.string.chart2), e.getMessage());
                }
            }
        }
    }
}
