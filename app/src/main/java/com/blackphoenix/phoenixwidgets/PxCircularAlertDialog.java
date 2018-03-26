package com.blackphoenix.phoenixwidgets;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackphoenix.phoenixwidgets.listeners.ProgressDialogDataInterface;


/**
 * Created by Praba on 08-02-2017.
 * ToDo: Move All String to Resources
 */

public class PxCircularAlertDialog extends AlertDialog {

    ProgressDialogDataInterface progressDialogDataInterface;

    private String _positiveButtonText;
    private String _negativeButtonText;
    private String _alertMessage;
    private int _alertIconResourceID;
    private OnPositiveButtonClickListener _positiveButtonClickListener;
    private OnNegativeButtonClickListener _negativeButtonClickListener;

    public interface OnPositiveButtonClickListener {
        void onClick(View view);
    }

    public interface OnNegativeButtonClickListener {
        void onClick(View view);
    }

    public PxCircularAlertDialog(Context context, String text) {
        this(context,R.style.PxwProgressDialogTheme,text);
    }

    public PxCircularAlertDialog(Context context, int themeResId, String text) {
        super(context, themeResId);
    }

    public PxCircularAlertDialog setPositiveButtonText(String buttonText){
        this._positiveButtonText = buttonText;
        return this;
    }

    public PxCircularAlertDialog setNegativeButtonText(String buttonText) {
        this._negativeButtonText = buttonText;
        return this;
    }

    public PxCircularAlertDialog setAlertMessage(String message) {
        this._alertMessage = message;
        return this;
    }

    public PxCircularAlertDialog setAlertIcon(int resourceID){
        this._alertIconResourceID = resourceID;
        return this;
    }

    public PxCircularAlertDialog setPositiveButtonClickListener(OnPositiveButtonClickListener listener){
        this._positiveButtonClickListener = listener;
        return this;
    }

    public PxCircularAlertDialog setNegativeButtonClickListener(OnNegativeButtonClickListener listener){
        this._negativeButtonClickListener = listener;
        return this;
    }


    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pxw_dialog_alert);
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        TextView alertMessage = findViewById(R.id.dialogAlert_content);
        ImageView alertIcon =  findViewById(R.id.dialogAlert_icon);
        Button finishButton = findViewById(R.id.dialogAlert_positiveButton);
        Button cancelButton = findViewById(R.id.dialogAlert_negativeButton);

        if(_alertMessage!=null){
            alertMessage.setText(_alertMessage);
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

}
