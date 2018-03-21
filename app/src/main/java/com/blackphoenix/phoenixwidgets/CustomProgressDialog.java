package com.blackphoenix.phoenixwidgets;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;


/**
 * Created by Praba on 08-02-2017.
 */

public class CustomProgressDialog extends AlertDialog {

    public CustomProgressDialog(Context context, int themeResId) {
        super(context, themeResId);

//        show();
        //di
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pxw_dialog_progress_circle);
        setCanceledOnTouchOutside(false);
        setCancelable(false);

        ImageView imageViewInner = (ImageView) findViewById(R.id.progress_innerImage);
        ImageView imageViewOuter = (ImageView) findViewById(R.id.progress_outerImage);


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
}
