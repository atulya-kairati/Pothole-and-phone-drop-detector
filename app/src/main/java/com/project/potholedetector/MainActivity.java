package com.project.potholedetector;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final Object LAYER_TYPE_HARDWARE =0;
    TextView x, y, z, location1;

    double threshold =15;


    Sensor accelerometer;
    SensorManager sm;
    LocationManager locationManager;
    LocationListener locationListener;
    Location current;

    long lastUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        current = new Location("Dummy");

        x = (TextView) findViewById(R.id.x);
        y = (TextView) findViewById(R.id.y);
        z = (TextView) findViewById(R.id.z);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sm != null) {
            accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                location1 = (TextView) findViewById(R.id.location1);
                location1.setText("Location:  " + location.getLatitude() + " | " + location.getLongitude());
                current = location;

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        //checking for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.INTERNET, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SEND_SMS
                }, 10);
            }
        } else {
            locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
        }
    }

    //#########################################################################################################################################
    @Override
    public void onSensorChanged(SensorEvent event) {

        x.setText("x= " + event.values[0]);
        y.setText("y= " + event.values[1]);
        z.setText("z= " + event.values[2]);

        //Makin an obj of SmsHandler class And FileHandler class
        SmsHandler sh = new SmsHandler();
        FileHandler fh = new FileHandler();


        //This is done to prevent multiple values above threshold being registered as multiple potholes
        long actualTime = event.timestamp;
        try {
            if (actualTime - lastUpdate > 100000000) {
                lastUpdate = actualTime;
                if (event.values[2] > threshold) {
                    Toast.makeText(getApplicationContext(), "Pothole Detected", Toast.LENGTH_SHORT).show();
                    fh.saveFile(current);//Saving potholes in txt database.

                    final MediaPlayer scream = MediaPlayer.create(this, R.raw.beep);
                    scream.start();
                    ///////////////////////////////////////////////






                    ///////////////////////////////////////////
                } else if (Math.abs(event.values[2]) < 1 && Math.abs(event.values[1]) < 1 && Math.abs(event.values[0]) < 1) {



                    Intent drop;
                    drop = new Intent(this, Drop.class);
                    final MediaPlayer scream = MediaPlayer.create(this, R.raw.scream);
                    scream.start();
                    //>>calling send method from Sms handler  class

                    drop.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(drop);

                    sh.send(current);

                }
            }
        } catch (Exception e) {
            lastUpdate = actualTime;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
//#########################################################################################################################################

    public void readfile(View v){

        FileHandler fh = new FileHandler();
        TextView dat = findViewById(R.id.data);
        String db = fh.read();
        dat.setText(db);


    }
    public void delfile(View v){

        FileHandler fh = new FileHandler();
        TextView dat = findViewById(R.id.data);
        fh.delData();
        dat.setText(getString(R.string.db_deletion)+"\n\n"+getString(R.string.info));

    }

    public  void savethres(View v){

        EditText setthres =findViewById(R.id.editThres);
        threshold = Double.parseDouble(setthres.getText().toString());

    }






}