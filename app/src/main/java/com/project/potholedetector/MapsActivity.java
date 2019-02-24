package com.project.potholedetector;

import android.location.Location;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final String FILE1 = "latData.txt";
    private final String FILE2 = "lonData.txt";

    private GoogleMap googleMap;
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //////////////////////////////////////////////////////
        String lat= "",lon="";
        StringBuffer sb= null;

        File root = Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath()+"/Pothole");
        File file1 =new File(dir,FILE1);
        File file2 =new File(dir,FILE2);
        FileInputStream fis1= null;
        FileInputStream fis2 = null;
        try {
            fis1 = new FileInputStream(file1);
            fis2 = new FileInputStream(file2);
            InputStreamReader isr1 = new InputStreamReader(fis1);
            InputStreamReader isr2 = new InputStreamReader(fis2);
            BufferedReader br1 = new BufferedReader(isr1);
            BufferedReader br2 = new BufferedReader(isr2);
            sb = new StringBuffer();

            while (((lat=br1.readLine()) != null) && (null != (lon = br2.readLine()))){


                latlngs.add(new LatLng(Double.parseDouble(lat),Double.parseDouble(lon))); //some latitude and logitude value

            }


            for (LatLng point : latlngs) {
                options.position(point);
                options.title("someTitle");
                options.snippet("someDesc");
                googleMap.addMarker(options);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




        //////////////////////////////////////////////////////

    }
}
