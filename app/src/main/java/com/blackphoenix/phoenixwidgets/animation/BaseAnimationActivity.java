package com.blackphoenix.phoenixwidgets.animation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.blackphoenix.phoenixwidgets.R;


/**
 * Created by Praba on 8/9/2017.
 */
public class BaseAnimationActivity extends AppCompatActivity {

    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.pxw_slide_from_right, R.anim.pxw_slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.pxw_slide_from_left, R.anim.pxw_slide_to_right);
    }


}
