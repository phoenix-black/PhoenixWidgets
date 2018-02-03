package com.blackphoenix.phoenixwidgets.listeners;

/**
 * Created by Praba on 30-01-2017.
 */

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class PhoenixTouchListener implements OnTouchListener {

    private final GestureDetector gestureDetector;
    private View currentView;

    public PhoenixTouchListener(Context ctx){
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        currentView = v;
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
       //     onSingleTapUpDone(event);
            onShortClicked(currentView);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent event){
            onLongClicked(currentView);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight(currentView);
                        } else {
                            onSwipeLeft(currentView);
                        }
                    }
                    result = true;
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom(currentView);
                    } else {
                        onSwipeTop(currentView);
                    }
                }
                result = true;

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight(View view) {
    }

    public void onSwipeLeft(View view) {
    }

    public void onSwipeTop(View view) {
    }

    public void onSwipeBottom(View view) {
    }

   /* public void onSingleTapUpDone(MotionEvent event) {

    }*/

    public void onShortClicked(View view) {

    }

    public void onLongClicked(View view){

    }
}