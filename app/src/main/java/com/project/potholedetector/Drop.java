package com.project.potholedetector;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Drop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop);
    }

    public void safe(View view){
        Intent myIntent = new Intent(this,MainActivity.class);


        //startActivity(myIntent);
        finish();
    }
}
