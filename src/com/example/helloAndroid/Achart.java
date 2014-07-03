package com.example.helloAndroid;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Achart extends BaseActivity {

    private enum ChartStyle {BARCHART, LINECHART}

    ;
    ChartStyle selectedChartStyle = ChartStyle.BARCHART;
    ArrayList<Integer> bloodsugarList = new ArrayList<Integer>();
    ArrayList<String> dateList = new ArrayList<String>();
    private GraphicalView mChart;
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
    private XYSeries mCurrentSeries;
    private XYSeriesRenderer mCurrentRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readBloodsugarLog();
        setContentView(R.layout.achart);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu chartMenu) {
        super.onCreateOptionsMenu( chartMenu );
        getMenuInflater().inflate(R.menu.chart, chartMenu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem chartItem) {
        switch (chartItem.getItemId()) {
            case R.id.achart:
                selectedChartStyle = ChartStyle.BARCHART;
                drawChart();
                break;
            case R.id.chart:
                selectedChartStyle = ChartStyle.LINECHART;
                drawChart();
                break;
            default:
                return super.onOptionsItemSelected(chartItem);
        }
        return true;
    }

    private void drawChart() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.achart);
        layout.removeView(mChart);
        switch (selectedChartStyle) {
            case BARCHART:
                mChart = ChartFactory.getBarChartView(this, mDataset, mRenderer, BarChart.Type.DEFAULT);
                break;
            case LINECHART:
                mChart = ChartFactory.getCubeLineChartView(this, mDataset, mRenderer, 0.3f);
                break;
            default:
                break;
        }
        layout.addView(mChart);
    }

    private void initAChart() {
        mCurrentSeries = new XYSeries("Bloodsugar Log");
        mDataset.addSeries(mCurrentSeries);
        mCurrentRenderer = new XYSeriesRenderer();
        mRenderer.addSeriesRenderer(mCurrentRenderer);
        mRenderer.setAxisTitleTextSize(20);
        mRenderer.setLabelsTextSize(20);
        mRenderer.setChartTitleTextSize(35);
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.LTGRAY);
        mRenderer.setMarginsColor(Color.BLUE);
    }

    private void readBloodsugarLog() {
        String temp;
        String logArray[];
        FileInputStream inputStream = null;

        try {
            inputStream = openFileInput(FILENAME);
            byte[] reader = new byte[inputStream.available()];
            while (inputStream.read(reader) != -1){}
            Scanner scan = new Scanner(new String(reader));
            scan.useDelimiter("\\n");
            while (scan.hasNext()) {
                temp = scan.next();
                logArray = temp.split(",");
                dateList.add(logArray[0]);
                bloodsugarList.add(Integer.parseInt(logArray[2]));
            }
            scan.close();
        } catch (Exception e) {
            Log.e(getString(R.string.chart4), e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(getString(R.string.chart3), e.getMessage());
                }
            }
        }
    }

    private void addData() {
        Integer x = 20;
        for (Integer sugar : bloodsugarList) {
            mCurrentSeries.add(x += 20, sugar);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mChart == null) {
            initAChart();
            addData();
            drawChart();
        } else {
            mChart.repaint();
        }
    }
}
