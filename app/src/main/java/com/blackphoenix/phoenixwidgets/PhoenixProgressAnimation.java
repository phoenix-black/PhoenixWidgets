package com.blackphoenix.phoenixwidgets;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.blackphoenix.phoenixwidgets.core.Phoenix;


/**
 * Created by Praba on 08-02-2017.
 */

public class PhoenixProgressAnimation extends AlertDialog {

    private final long DEFAULT_TIMEOUT_DURATION = 5 * 1000;
    private long timeout_duration; // 5 Seconds
    private boolean isTimerEnabled = true;

    private Phoenix.PhoenixTimeoutListener phoenixTimeoutListener;

    private Handler timerHandler;

    public PhoenixProgressAnimation(Context context, int themeResId) {
        super(context, themeResId);
        initDialog(DEFAULT_TIMEOUT_DURATION);
    }

    public PhoenixProgressAnimation(Context context, int themeResId, long timeoutDuration){
        super(context, themeResId);
        initDialog(timeoutDuration);
    }

    public PhoenixProgressAnimation(Context context, long timeoutDuration){
        super(context, R.style.PxwProgressDialogTheme);
        initDialog(timeoutDuration);
    }


    private void initDialog(long duration){
        if(duration>0) {
            this.timeout_duration = duration;
        } else {
            this.timeout_duration = DEFAULT_TIMEOUT_DURATION;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pxw_dialog_progress_circle);
        setCanceledOnTouchOutside(false);
        setCancelable(false);

        ImageView imageViewInner = (ImageView) findViewById(R.id.progress_innerImage);
        ImageView imageViewOuter = (ImageView) findViewById(R.id.progress_outerImage);

        timerHandler = new Handler();

        RotateAnimation rotateAnimationInner = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimationInner.setRepeatCount(Animation.INFINITE);
        rotateAnimationInner.setInterpolator(new LinearInterpolator());
        rotateAnimationInner.setDuration(2000);


        RotateAnimation rotateAnimationOuter = new RotateAnimation(360, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimationOuter.setRepeatCount(Animation.INFINITE);
        rotateAnimationOuter.setInterpolator(new LinearInterpolator());
        rotateAnimationOuter.setDuration(2000);

        imageViewInner.startAnimation(rotateAnimationInner);
        imageViewOuter.startAnimation(rotateAnimationOuter);
    }



    @Override
    protected void onStop() {
        super.onStop();
        if(timerHandler!=null)
            timerHandler.removeCallbacksAndMessages(null);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(isTimerEnabled) {
            if (timerHandler == null) {
                timerHandler = new Handler();
            }

            timerHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isShowing()) {

                        if (phoenixTimeoutListener != null)
                            phoenixTimeoutListener.onTimeOut();

                        dismiss();
                    }
                }
            }, timeout_duration);
        }
    }

    public PhoenixProgressAnimation enableTimer(boolean status){
        this.isTimerEnabled = status;
        return this;
    }

    public PhoenixProgressAnimation setTimeoutDuration(long durationInMs){
        if(durationInMs>0){
            timeout_duration = durationInMs;
        } else {
            // ToDo: Error Handler / Error Listener
        }
        return this;
    }

    public PhoenixProgressAnimation setTimeoutListener(Phoenix.PhoenixTimeoutListener listener){
        this.phoenixTimeoutListener = listener;
        return this;
    }
}
