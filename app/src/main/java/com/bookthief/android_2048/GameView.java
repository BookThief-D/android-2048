package com.bookthief.android_2048;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.jar.Attributes;

/**
 * Created by BookThief on 2014/11/5.
 */
public class GameView extends GridLayout{
    @TargetApi(Build.VERSION_CODES.L)
    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initGameView();
    }
    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }
    public GameView(Context context) {
        super(context);
        initGameView();
    }
    private void initGameView(){
        setOnTouchListener(new View.OnTouchListener(){
            private float startx,starty,offsetx,offsety;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startx = event.getX();
                        starty = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetx = event.getX() - startx;
                        offsety = event.getY() - starty;
                        if(Math.abs(offsetx) >Math.abs(offsety)) {
                            if (offsetx < -5) {
                                SwipeLeft();
                            } else if (offsetx > 5) {
                                SwipeRight();
                            }
                        }else {
                            if (offsety < -5) {
                                SwipeUp();
                            }else if (offsety > 5) {
                                SwipeDown();
                            }

                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        }
        private void SwipeLeft(){

        }
        private void SwipeRight(){

        } private void SwipeUp(){

        } private void SwipeDown(){

        }

    }
