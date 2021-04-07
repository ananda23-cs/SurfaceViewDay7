package com.example.surfaceviewday7;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.SeekBar;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class MySurfaceView extends SurfaceView implements SeekBar.OnSeekBarChangeListener,
                            View.OnTouchListener, View.OnClickListener, Runnable {
    private float radius;
    private ArrayList<Spot> spots;
    private int greenClicks, imageClicks;
    private boolean ANIMATE = false;

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        spots = new ArrayList<>();

        setOnTouchListener(this);

        //Will not not draw something
        setWillNotDraw(false);

        radius = 100.f;

        greenClicks = 0;
        imageClicks = 0;
        ANIMATE = true;
    }

    protected void onDraw(Canvas canvas){
        if(imageClicks % 2 == 0) {
            Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.image);

            Paint black = new Paint();
            black.setColor(Color.BLACK);
            canvas.drawBitmap(image, 250.f, 50.f, black);
        }

        if(greenClicks % 2 == 0){
            Paint green = new Paint();
            green.setColor(Color.GREEN);
            canvas.drawCircle(700.0f, 100.0f, 100.0f, green);
        }

        Paint red = new Paint();
        red.setColor(Color.RED);
        canvas.drawCircle(100.0f, 100.0f, radius, red);

        //draw each spot from spots
        //Since I could be updating colors while trying to draw,
        //I need to synchronize
        synchronized (spots) {
            for (Spot spot : spots) {
                spot.draw(canvas);
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        radius = progress;
        invalidate();  //The current render is now invalid, please call draw soon.
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            //Find coordinate of touch event
            float x = event.getX();
            float y = event.getY();
            //Create a spot
            Spot newSpot = new Spot(x, y);
            //Add a spot to the array list
            synchronized (spots) {
                spots.add(newSpot);
            }
            //Let myself know that I should draw the new input
            invalidate();
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.greenCircleShow) {
            greenClicks = greenClicks + 1;
            invalidate();
        }
        else if (v.getId() == R.id.imageShow){
            imageClicks = imageClicks + 1;
            invalidate();
        }
    }

    @Override
    public void run() {
        //This is the threaded code that can run separately

        //Want to do, is change the color for every 2 seconds or so
        while(ANIMATE){
            //1. Sleep for a little bit
            try {
                sleep(200);
            } catch (InterruptedException e) {
                //e.printStackTrace();
                ANIMATE = false;
            }
            //2 change the spots' colors
            synchronized (spots) {
                for (Spot spot : spots) {
                    spot.randomizeColor();
                }
            }

            //3. let the surface view know that it should draw itself soon
            invalidate();
        }
    }//run
}//MySurfaceView
