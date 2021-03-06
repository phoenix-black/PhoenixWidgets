package com.blackphoenix.phoenixwidgets;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blackphoenix.phoenixwidgets.listeners.ProgressDialogDataInterface;


/**
 * Created by Praba on 08-02-2017.
 */

public abstract class AdvancedTimerProgressDialog extends AlertDialog {

    private TextView textViewContent;
    ProgressDialogDataInterface progressDialogDataInterface;

    private String progressText = "Please Wait...";
    long WAIT_TIME = 60*1000;
    Context _context;

    public abstract void onInterfaceReady(ProgressDialogDataInterface dialogInterface);
    public abstract void onTimedOut();
    public abstract void onDismissed();

   /* private TextConfig textConfig;
    private FinishButtonConfig finishButtonConfig;
    private CancelButtonConfig cancelButtonConfig;
    private TimerConfig timerConfig;

    public class TextConfig {
        float textSize;
        int textColor;
        LinearLayout.LayoutParams layoutParams;

        public LinearLayout.LayoutParams getLayoutParams() {
            return layoutParams;
        }

        public void setLayoutParams(LinearLayout.LayoutParams layoutParams) {
            this.layoutParams = layoutParams;
        }

        public float getTextSize() {
            return textSize;
        }

        public void setTextSize(float textSize) {
            this.textSize = textSize;
        }

        public int getTextColor() {
            return textColor;
        }

        public void setTextColor(int textColor) {
            this.textColor = textColor;
        }
    }

    public class FinishButtonConfig extends ButtonConfig {

    }

    public class CancelButtonConfig extends ButtonConfig {

    }

    public class ButtonConfig {

    }

    public class TimerConfig {

    }
*/


    public AdvancedTimerProgressDialog(Context context, int themeResId, String text) {
        this(context,themeResId,text,-1);
    }
    public AdvancedTimerProgressDialog(Context context,String text, long timeout /* TIMEOUT in Milliseconds*/) {
        this(context,R.style.PxwProgressDialogTheme,text,timeout);
    }

    public AdvancedTimerProgressDialog(Context context, int themeResId, String text, long timeout /* TIMEOUT in Milliseconds*/) {
        super(context, themeResId);
        this.progressText = text;
        if(timeout!= -1) {
            this.WAIT_TIME = timeout;
        }
        this._context = context;
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pxw_dialog_timer_progress_text);
        setCanceledOnTouchOutside(false);
        setCancelable(false);

      //  ImageView imageViewInner = (ImageView) findViewById(R.id.progress_smallInnerImage);
        final TextView timerText = findViewById(R.id.progress_innerTimerText);
        ImageView imageViewOuter = (ImageView) findViewById(R.id.progress_smallOuterImage);
        textViewContent = (TextView)findViewById(R.id.dialogMessage_content);
        textViewContent.setText(progressText);
        LinearLayout parentLayout = findViewById(R.id.dialog_timer_progress);
/*
        if(textConfig!=null){
            textViewContent.setTextSize(textConfig.getTextSize());
            textViewContent.setTextColor(textConfig.getTextColor());
            textViewContent.setLayoutParams(textConfig.getLayoutParams());
        }*/

/*        GradientDrawable drawable = (GradientDrawable) parentLayout.getBackground();
        if(drawable!=null) {
            drawable.setColor(Color.BLACK);
            drawable.setCornerRadius(15);
            drawable.setStroke(2,Color.parseColor("#00ffff"));
        }*/

        Button finishButton = findViewById(R.id.dialogTimer_finish);
        Button cancelButton = findViewById(R.id.dialogTimer_cancel);

        progressDialogDataInterface = new ProgressDialogDataInterface() {
            @Override
            public void updateData(String newData) {
                textViewContent.setText(newData);
            }
        };

        onInterfaceReady(progressDialogDataInterface);

       /* RotateAnimation rotateAnimationInner = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimationInner.setRepeatCount(Animation.INFINITE);
        rotateAnimationInner.setInterpolator(new LinearInterpolator());
        rotateAnimationInner.setDuration(2000);*/


        RotateAnimation rotateAnimationOuter = new RotateAnimation(360, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimationOuter.setRepeatCount(Animation.INFINITE);
        rotateAnimationOuter.setInterpolator(new LinearInterpolator());
        rotateAnimationOuter.setDuration(2000);

       // imageViewInner.startAnimation(rotateAnimationInner);
        imageViewOuter.startAnimation(rotateAnimationOuter);

        final CountDownTimer countDownTimer = new CountDownTimer(WAIT_TIME,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String TimeMessage = ""+millisUntilFinished/1000;
                timerText.setText(TimeMessage);
            }

            @Override
            public void onFinish() {
                onTimedOut();
                dismiss();
            }
        }.start();


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                onTimedOut();
                dismiss();
            }
        });



        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                onDismissed();
                dismiss();
            }
        });
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
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isShowing()) {
                    Toast.makeText(_context, "Timed Out", Toast.LENGTH_SHORT).show();
                    onTimedOut();
                    dismiss();
                }
            }
        },WAIT_TIME);*/

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
