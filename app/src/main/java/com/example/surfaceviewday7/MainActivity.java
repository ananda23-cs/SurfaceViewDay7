package com.example.surfaceviewday7;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MySurfaceView theView = (MySurfaceView)findViewById(R.id.surfaceView);

        SeekBar mySeekBar = (SeekBar)findViewById(R.id.seekBar);

        //the view needs to know where the seekbar is to appropriately size the circle
        mySeekBar.setOnSeekBarChangeListener(theView);

        //red button -- shows/hides a green circle in MySurfaceView
        Button greenCircleShow = (Button) findViewById(R.id.greenCircleShow);
        greenCircleShow.setBackgroundColor(Color.RED);
        greenCircleShow.setOnClickListener(theView);

        //next button shows/hides an image form the surface view
        Button imageShow = (Button) findViewById(R.id.imageShow);
        imageShow.setBackgroundColor(Color.GREEN);
        imageShow.setOnClickListener(theView);
    }
}