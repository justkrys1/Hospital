package com.example.helloAndroid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.qozix.tileview.TileView;
import com.qozix.tileview.markers.MarkerEventListener;

public class Map extends BaseActivity{
    TileView currentTileView, tileView1, tileViewBuilding1, tileViewBuilding7, tileViewBuilding15, tileViewBuilding50;
    TileView tvRoom1103, tvRoom116, tvRoom111;
    LinearLayout linearLayout;
    Spinner buildingSpinner, building1Spinner;
    String[] buildingArray;
    ArrayAdapter spinnerArrayAdapter, spinnerArrayAdapterBuilding1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(linearLayout);

        buildingSpinner = new Spinner(this);
        LinearLayout.LayoutParams spinnerViewLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.addView(buildingSpinner, spinnerViewLayout);

/*        tileView1 = new TileView(this);
        tileView1.setSize(2424, 1771);
        tileView1.addDetailLevel(1f, "tilesMain/1000_%col%_%row%.gif", "downsamplesMain/map.gif");
        tileView1.addDetailLevel(0.5f, "tilesMain/500_%col%_%row%.gif", "downsamplesMain/map.gif");
        tileView1.addDetailLevel(0.25f, "tilesMain/250_%col%_%row%.gif", "downsamplesMain/map.gif");
        tileView1.addDetailLevel(0.125f, "tilesMain/125_%col%_%row%.gif", "downsamplesMain/map.gif ");
        tileView1.addTileViewEventListener(new TileView.TileViewEventListenerImplementation());
        tileView1.moveToAndCenter(2424, 1771);
        tileView1.slideToAndCenter(2424, 1771);
        tileView1.setScale(0.25);

        LinearLayout.LayoutParams tileViewLayout = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
        linearLayout.addView(tileView1, tileViewLayout);*/
        tileView1 = CreateTileView(2424, 1771, "tilesMain", "downsamplesMain");
        tileViewBuilding1 = CreateTileView(2000, 1429, "tiles1", "downsamples1");
        tileViewBuilding7 = CreateTileView(2000, 1429, "tiles7", "downsamples7");
        tileViewBuilding15 = CreateTileView(2000, 1429, "tiles15", "downsamples15");
        tileViewBuilding50 = CreateTileView(2000, 1429, "tiles50", "downsamples50");
        currentTileView = tileView1;
        LinearLayout.LayoutParams tileViewLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
        linearLayout.addView(currentTileView, tileViewLayout);
       /* tileView1.addTileViewEventListener(new TileView.TileViewEventListenerImplementation(){
            @Override
        public void onTap(int x, int y){
                Log.d("onTap", "X: " + x + " Y: " + y);
            }
        });

        tileViewBuilding1 = new TileView(this);
        tileViewBuilding1.setSize(2000, 1429);
        tileViewBuilding1.addDetailLevel(1f, "tiles1/1000_%col%_%row%.gif", "downsamples1/map.gif");
        tileViewBuilding1.addDetailLevel(0.5f, "tiles1/500_%col%_%row%.gif", "downsamples1/map.gif");
        tileViewBuilding1.addDetailLevel(0.25f, "tiles1/250_%col%_%row%.gif", "downsamples1/map.gif");
        tileViewBuilding1.addDetailLevel(0.125f, "tiles1/125_%col%_%row%.gif", "downsamples1/map.gif");
        tileViewBuilding1.addTileViewEventListener(new TileView.TileViewEventListenerImplementation());
        tileViewBuilding1.moveToAndCenter(2000, 1429);
        tileViewBuilding1.slideToAndCenter(2000, 1429);
        tileViewBuilding1.setScale(0.25);

        tileViewBuilding7 = new TileView(this);
        tileViewBuilding7.setSize(2000, 1429);
        tileViewBuilding7.addDetailLevel(1f, "tiles7/1000_%col%_%row%.gif", "downsamples7/map.gif");
        tileViewBuilding7.addDetailLevel(0.5f, "tiles7/500_%col%_%row%.gif", "downsamples7/map.gif");
        tileViewBuilding7.addDetailLevel(0.25f, "tiles7/250_%col%_%row%.gif", "downsamples7/map.gif");
        tileViewBuilding7.addDetailLevel(0.125f, "tiles7/125_%col%_%row%.gif", "downsamples7/map.gif");
        tileViewBuilding7.addTileViewEventListener(new TileView.TileViewEventListenerImplementation());
        tileViewBuilding7.moveToAndCenter(2000, 1429);
        tileViewBuilding7.slideToAndCenter(2000, 1429);
        tileViewBuilding7.setScale(0.25);

        tileViewBuilding15 = new TileView(this);
        tileViewBuilding15.setSize(2000, 1429);
        tileViewBuilding15.addDetailLevel(1f, "tiles15/1000_%col%_%row%.gif", "downsamples15/map.gif");
        tileViewBuilding15.addDetailLevel(0.5f, "tiles15/500_%col%_%row%.gif", "downsamples15/map.gif");
        tileViewBuilding15.addDetailLevel(0.25f, "tiles15/250_%col%_%row%.gif", "downsamples15/map.gif");
        tileViewBuilding15.addDetailLevel(0.125f, "tiles15/125_%col%_%row%.gif", "downsamples15/map.gif");
        tileViewBuilding15.addTileViewEventListener(new TileView.TileViewEventListenerImplementation());
        tileViewBuilding15.moveToAndCenter(2424, 1771);
        tileViewBuilding15.slideToAndCenter(2424, 1771);
        tileViewBuilding15.setScale(0.25);

        tileViewBuilding50 = new TileView(this);
        tileViewBuilding50.setSize(2000, 1429);
        tileViewBuilding50.addDetailLevel(1f, "tiles50/1000_%col%_%row%.gif", "downsamples50/map.gif");
        tileViewBuilding50.addDetailLevel(0.5f, "tiles50/500_%col%_%row%.gif", "downsamples50/map.gif");
        tileViewBuilding50.addDetailLevel(0.25f, "tiles50/250_%col%_%row%.gif", "downsamples50/map.gif");
        tileViewBuilding50.addDetailLevel(0.125f, "tiles50/125_%col%_%row%.gif", "downsamples50/map.gif");
        tileViewBuilding50.addTileViewEventListener(new TileView.TileViewEventListenerImplementation());
        tileViewBuilding50.moveToAndCenter(2424, 1771);
        tileViewBuilding50.slideToAndCenter(2424, 1771);
        tileViewBuilding50.setScale(0.25);
*/
        initializeApp();
    }

    private TileView CreateTileView(Integer width, Integer height, String folder, String ds){
       TileView tv = new TileView(this);
        tv.setSize(width, height);
        tv.addDetailLevel(1f, folder + "/1000_%col%_%row%.gif", ds + "/map.gif");
        tv.addDetailLevel(.5f, folder + "/500_%col%_%row%.gif", ds + "/map.gif");
        tv.addDetailLevel(.25f, folder + "/250_%col%_%row%.gif", ds + "/map.gif");
        tv.addDetailLevel(.125f, folder + "/125_%col%_%row%.gif", ds + "/map.gif");
        tv.moveToAndCenter(width, height);
        tv.slideToAndCenter(width, height);
        tv.setScale(0.25);
        return tv;
    }

    private void initializeApp(){

        spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,
                new Building[]  {
                        new Building("Campus Map", 0, 0, 0.25),
                        new Building("Building 1", 823, 1447, 1.0),
                        new Building("Building 5", 1165, 958, 1.0),
                        new Building("Building 6", 870, 1038, 1.0),
                        new Building("Building 7", 1160, 723, 1.0),
                        new Building("Building 8", 561, 565, 1.0),
                        new Building("Building 9", 985, 533, 1.0),
                        new Building("Building 10", 738, 392, 1.0),
                        new Building("Building 15", 522, 736, 1.0),
                        new Building("Building 16", 563, 1030, 1.0),
                        new Building("Building 18", 1669, 1402, 1.0),
                        new Building("Building 19", 1427, 617, 1.0),
                        new Building("Building 20", 934, 425, 1.0),
                        new Building("Building 23", 204, 1590, 1.0),
                        new Building("Building 26", 781, 532, 1.0),
                        new Building("Building 27", 1565, 1131, 1.0),
                        new Building("Building 28", 2176, 775, 1.0),
                        new Building("Building 29", 1302, 721, 1.0),
                        new Building("Building 50", 324, 199, 1.0),
                        new Building("Building 111", 363, 723, 1.0),
                                 });
        buildingSpinner.setAdapter(spinnerArrayAdapter);

        spinnerArrayAdapterBuilding1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                new Building[]{
                        new Building("Campus Map", 0, 0, 0.25),
                        new Building("Room 1103", 500, 500, 1.0),
                        new Building("Room 1111", 400, 500, 1.0),
                        new Building("Room 1116", 300, 500, 1.0)
                });
        buildingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              Building b = (Building) parent.getSelectedItem();
                positionMap(b);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Building b = (Building) parent.getSelectedItem();
                positionMap(b);
            }
        });

        buildingSpinner.setSelection(3);
        ImageView marker1 = new ImageView(this);
        marker1.setImageResource(R.drawable.marker_blue);
        marker1.setTag("CIS Classes");

        ImageView marker7 = new ImageView(this);
        marker7.setImageResource(R.drawable.marker_blue);
        marker7.setTag("Nursing");

        ImageView marker15 = new ImageView(this);
        marker15.setImageResource(R.drawable.marker_blue);
        marker15.setTag("Financial Aid");

        ImageView marker50 = new ImageView(this);
        marker50.setImageResource(R.drawable.marker_blue);
        marker50.setTag("Cashier");

        tileView1.addMarker(marker1, 857, 1406, -0.5f, -1.0f);
        tileView1.addMarker(marker7, 1157, 694, -0.5f, -1.0f);
        tileView1.addMarker(marker15, 521, 708, -0.5f, -1.0f);
        tileView1.addMarker(marker50, 325, 175, -0.5f, -1.0f);

        tileView1.addMarkerEventListener(new MarkerEventListener(){
            @Override
            public void onMarkerTap( View view, int x, int y ){
                Log.d("Marker Event", "marker tag = " + view.getTag());
                linearLayout.removeView(tileView1);
                LinearLayout.LayoutParams tileViewLayout = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
                linearLayout.addView(tileViewBuilding1, tileViewLayout);
                tileViewBuilding1.setScale(0.50);
            }
        });
    }
    private void positionMap(Building b){
        if(b.name.equals("Campus Map")){
            linearLayout.removeView(currentTileView);
            LinearLayout.LayoutParams tileViewLayout = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
            currentTileView = tileView1;
            buildingSpinner.setAdapter(spinnerArrayAdapter);
            buildingSpinner.setSelection(1);
            linearLayout.addView(currentTileView, tileViewLayout);
            currentTileView.setScale(1);

        }
            //currentTileView.setScale(b.scale);
            currentTileView.slideToAndCenter(b.x, b.y);
        }
//        tileView1.slideToAndCenter(b.x, b.y);
//        tileView1.setScale(1f);
    }

///////////////////////////////////////////////////////////////////////////