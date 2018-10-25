package com.blackphoenix.phoenixwidgets;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackphoenix.phoenixwidgets.listeners.ProgressDialogDataInterface;


/**
 * Created by Praba on 08-02-2017.
 */

public class PxActionDialog extends AlertDialog {

    ProgressDialogDataInterface progressDialogDataInterface;

    private String _positiveButtonText;
    private String _negativeButtonText;
    private String _alertTitle;
    private String _alertMessage;
    private int _alertIconResourceID;

    private boolean isPositiveButtonEnabled = true;
    private boolean isNegativeButtonEnabled = true;

    private OnPositiveButtonClickListener _positiveButtonClickListener;
    private OnNegativeButtonClickListener _negativeButtonClickListener;

    public interface OnPositiveButtonClickListener {
        void onClick(View view);
    }

    public interface OnNegativeButtonClickListener {
        void onClick(View view);
    }

    public PxActionDialog(Context context) {
        super(context,R.style.PxwProgressDialogTheme);
    }

    public PxActionDialog(Context context, String text) {
        this(context,R.style.PxwProgressDialogTheme,text);
    }

    public PxActionDialog(Context context, int themeResId, String text) {
        super(context, themeResId);
        this._alertMessage = text;
    }

    public PxActionDialog setPositiveButtonText(String buttonText){
        this._positiveButtonText = buttonText;
        return this;
    }

    public PxActionDialog setNegativeButtonText(String buttonText) {
        this._negativeButtonText = buttonText;
        return this;
    }

    public PxActionDialog setAlertTitle(String title) {
        this._alertTitle = title;
        return this;
    }

    public PxActionDialog setAlertMessage(String message) {
        this._alertMessage = message;
        return this;
    }

    public PxActionDialog setAlertIcon(int resourceID){
        this._alertIconResourceID = resourceID;
        return this;
    }

    public PxActionDialog setPositiveButtonClickListener(OnPositiveButtonClickListener listener){
        this._positiveButtonClickListener = listener;
        return this;
    }

    public PxActionDialog setNegativeButtonClickListener(OnNegativeButtonClickListener listener){
        this._negativeButtonClickListener = listener;
        return this;
    }

    public PxActionDialog setPositiveButtonEnabled(boolean status){
        this.isPositiveButtonEnabled = status;
        return this;
    }

    public PxActionDialog setNegativeButtonEnabled(boolean status){
        this.isNegativeButtonEnabled = status;
        return this;
    }


    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pxw_dialog_action_rect);
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        TextView alertTitle = findViewById(R.id.dialogActionRect_title);
        TextView alertMessage = findViewById(R.id.dialogActionRect_content);
        ImageView alertIcon =  findViewById(R.id.dialogActionRect_icon);
        Button finishButton = findViewById(R.id.dialogActionRect_positiveButton);
        Button cancelButton = findViewById(R.id.dialogActionRect_negativeButton);

        if(_alertTitle!=null){
            alertTitle.setText(_alertTitle);
        }

        if(_alertMessage!=null){
            alertMessage.setText(_alertMessage);
            alertMessage.setMovementMethod(new ScrollingMovementMethod());
        }

        if(isPositiveButtonEnabled) {
            finishButton.setVisibility(View.VISIBLE);
        } else {
            finishButton.setVisibility(View.INVISIBLE);
        }

        if(isNegativeButtonEnabled) {
            cancelButton.setVisibility(View.VISIBLE);
        } else {
            cancelButton.setVisibility(View.INVISIBLE);
        }

        if(_positiveButtonText!=null){
            finishButton.setText(_positiveButtonText);
        }

        if(_negativeButtonText != null){
            cancelButton.setText(_negativeButtonText);
        }

        if(_alertIconResourceID > 0){
            alertIcon.setImageResource(_alertIconResourceID);
        }


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(_positiveButtonClickListener!=null){
                    _positiveButtonClickListener.onClick(view);
                }

                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(_negativeButtonClickListener!=null){
                    _negativeButtonClickListener.onClick(view);
                }

                dismiss();
            }
        });
    }



    public static PxActionDialog build(Context context, String title, String message){
        return new PxActionDialog(context,message).setAlertTitle(title).setNegativeButtonEnabled(false);
    }

    public static PxActionDialog build(Context context, String title, String message,
                                       OnPositiveButtonClickListener positiveButtonClickListener){
        return new PxActionDialog(context,message).setAlertTitle(title).setPositiveButtonClickListener(positiveButtonClickListener);
    }

    public static PxActionDialog build(Context context, String title, String message,
                                       OnPositiveButtonClickListener positiveButtonClickListener, OnNegativeButtonClickListener negativeButtonClickListener){
        return new PxActionDialog(context,message).setAlertTitle(title).setPositiveButtonClickListener(positiveButtonClickListener).setNegativeButtonClickListener(negativeButtonClickListener);
    }


}
