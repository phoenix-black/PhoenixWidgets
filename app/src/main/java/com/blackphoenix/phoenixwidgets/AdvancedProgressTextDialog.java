package com.blackphoenix.phoenixwidgets;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blackphoenix.phoenixwidgets.listeners.ProgressDialogDataInterface;


/**
 * Created by Praba on 08-02-2017.
 */

public abstract class AdvancedProgressTextDialog extends AlertDialog {

    private TextView textViewContent;
    ProgressDialogDataInterface progressDialogDataInterface;

    private String progressText = "Please Wait...";
    long WAIT_TIME = 60*1000;
    Context _context;

    public abstract void onInterfaceReady(ProgressDialogDataInterface dialogInterface);
    public abstract void onTimedOut();

    public AdvancedProgressTextDialog(Context context, int themeResId, String text) {
        super(context, themeResId);
        this.progressText = text;
        this._context = context;
    }

    public AdvancedProgressTextDialog(Context context, String text) {
        super(context, R.style.PxwProgressDialogTheme);
        this.progressText = text;
        this._context = context;
    }

    public AdvancedProgressTextDialog(Context context, int themeResId, String text, long timeout /* TIMEOUT in Milliseconds*/) {
        super(context, themeResId);
        this.progressText = text;
        this.WAIT_TIME = timeout;
        this._context = context;
    }

    @Override
    protected void onStart(){
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pxw_dialog_progress_text);
        setCanceledOnTouchOutside(false);
        setCancelable(false);

        ImageView imageViewInner = (ImageView) findViewById(R.id.progress_smallInnerImage);
        ImageView imageViewOuter = (ImageView) findViewById(R.id.progress_smallOuterImage);
        textViewContent = (TextView)findViewById(R.id.dialogMessage_content);
        textViewContent.setText(progressText);

        progressDialogDataInterface = new ProgressDialogDataInterface() {
            @Override
            public void updateData(String newData) {
                textViewContent.setText(newData);
            }
        };

        onInterfaceReady(progressDialogDataInterface);

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

    public ProgressDialogDataInterface getProgressDialogDataInterface(){
        return this.progressDialogDataInterface;
    }

    public boolean isInterfaceReady(){
        return progressDialogDataInterface!=null;
    }

    @Override
    public void show(){
        super.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isShowing()) {
                   // Toast.makeText(_context, "Timed Out", Toast.LENGTH_SHORT).show();
                    onTimedOut();
                    dismiss();
                }
            }
        },WAIT_TIME);

    }
/*
    public void setProgressDialogText(String message){
            if(message!=null){
                if(!message.matches("") || !message.equals("")) {
                    textViewContent.setText(message);
                    return;
                }
            }
            textViewContent.setText("Please Wait...");
    }*/
}
