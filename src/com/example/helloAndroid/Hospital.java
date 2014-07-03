package com.example.helloAndroid;
import android.location.LocationListener;
import android.os.Bundle;
import android.content.Context;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import android.location.Location;
import android.location.LocationManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.os.AsyncTask;

public class Hospital extends BaseActivity implements LocationListener {
    private int userIcon, hospitalIcon, urgentIcon;
    private GoogleMap hospitalMap;
    private LocationManager locationManager;
    private Marker userMarker;
    private Marker[] placeMarkers;
    private final int MAX_PLACES = 20;
    private MarkerOptions[] places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital);
        userIcon = R.drawable.yellow_point;
        hospitalIcon = R.drawable.red_point;
        urgentIcon = R.drawable.blue_point;

        if(hospitalMap == null){
            hospitalMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.nearestMap)).getMap();
            if(hospitalMap != null){
                hospitalMap.setMapType(GoogleMap.MAP_TYPE_Normal);
                placeMarkers = new Marker[MAX_PLACES];
            }
        }
        updateLocation();
    }

    private void updateLocation(){
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location lastLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        double lat = lastLoc.getLatitude();
        double lng = lastLoc.getLongitude();
        LatLng lastLatLng = new LatLng(lat, lng);
        if(userMarker!=null) userMarker.remove();
        userMarker = hospitalMap.addMarker(new MarkerOptions()
                .position(lastLatLng)
                .title("You are here")
                .icon(BitmapDescriptorFactory.fromResource(userIcon))
                .snippet("Your last recorded location"));
        hospitalMap.animateCamera(CameraUpdateFactory.newLatLng(lastLatLng), 3000, null);
        String placesSearchStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
                "json?location = "+lat+","+lng+
                "&radius = 1000&sensor=true" +
                "&types = hospital|urgent_care"+
                "&key = AIzaSyASShPnduRjQ-WHMqgLJF9fW_DJTeTTQ_Q";
        new GetPlaces().execute(placesSearchStr);
    }

    private class GetPlaces extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... placesURL) {
            StringBuilder placesBuilder = new StringBuilder();
            for (String placeSearchURL : placesURL) {
                HttpClient placesClient = new DefaultHttpClient();
                try {
                    HttpGet placesGet = new HttpGet(placeSearchURL);
                    HttpResponse placesResponse = placesClient.execute(placesGet);
                    StatusLine placeSearchStatus = placesResponse.getStatusLine();
                    if (placeSearchStatus.getStatusCode() == 200) {
                        HttpEntity placesEntity = placesResponse.getEntity();
                        InputStream placesContent = placesEntity.getContent();
                        InputStreamReader placesInput = new InputStreamReader(placesContent);
                        BufferedReader placesReader = new BufferedReader(placesInput);
                        String lineIn;
                        while ((lineIn = placesReader.readLine()) != null) {
                            placesBuilder.append(lineIn);
                        }
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            return placesBuilder.toString();
        }
        protected void onPostExecute(String result) {
            if(placeMarkers!=null){
                for(int pm=0; pm<placeMarkers.length; pm++){
                    if(placeMarkers[pm]!=null)
                    try {
                        JSONObject resultObject = new JSONObject(result);
                        JSONArray placesArray = resultObject.getJSONArray("results");
                        places = new MarkerOptions[placesArray.length()];
                        for (int p = 0; p < placesArray.length(); p++) {
                            boolean missingValue = false;
                            LatLng placeLL = null;
                            String placeName = "";
                            String vicinity = "";
                            int currIcon = userIcon;
                            try{
                                missingValue = false;
                                JSONObject placeObject = placesArray.getJSONObject(p);
                                JSONObject loc = placeObject.getJSONObject("geometry").getJSONObject("location");
                                placeLL = new LatLng(
                                        Double.valueOf(loc.getString("lat")),
                                        Double.valueOf(loc.getString("lng")));
                                JSONArray types = placeObject.getJSONArray("types");
                                for(int t = 0; t < types.length(); t++){
                                    String thisType=types.get(t).toString();
                                    if(thisType.contains("hospital")){
                                        currIcon = hospitalIcon;
                                        break;
                                    }
                                    else if(thisType.contains("urgent_care")){
                                        currIcon = urgentIcon;
                                        break;
                                    }
                                }
                                vicinity = placeObject.getString("vicinity");
                                placeName = placeObject.getString("name");
                            }
                            catch(JSONException jse){
                                missingValue = true;
                                jse.printStackTrace();
                            }
                            if(missingValue)    places[p]=null;
                            else
                                places[p]=new MarkerOptions()
                                        .position(placeLL)
                                        .title(placeName)
                                        .icon(BitmapDescriptorFactory.fromResource(currIcon))
                                        .snippet(vicinity);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        if(places != null && placeMarkers!=null){
            for(int p=0; p<places.length && p<placeMarkers.length; p++){
                //will be null if a value was missing
                if(places[p]!=null)
                    placeMarkers[p] = hospitalMap.addMarker(places[p]);
            }
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 100, (LocationListener) this);
    }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v("Hospital", "location changed");
        updateLocation();
    }
    @Override
    public void onProviderDisabled(String provider){
        Log.v("Hospital", "provider disabled");
    }
    @Override
    public void onProviderEnabled(String provider) {
        Log.v("Hospital", "provider enabled");
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.v("Hospital", "status changed");
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(hospitalMap!=null){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 100, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(hospitalMap!=null){
            locationManager.removeUpdates(this);
        }
    }
}
