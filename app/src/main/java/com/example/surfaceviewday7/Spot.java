package com.example.surfaceviewday7;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Spot {

    //instance variables
    private float cx, cy, radius;
    private Paint color;

    //Constructor with a coordinate parameters
    //Also may randomly pick a color
    public Spot(float x, float y){
        cx = x;
        cy = y;

        radius = 60.0f;
        color = new Paint();
        int tmp = Color.rgb((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
        color.setColor(tmp);
    }

    // Know how to draw myself
    public void draw(Canvas canvas){
        canvas.drawCircle(cx, cy, radius,color);
    }
}
