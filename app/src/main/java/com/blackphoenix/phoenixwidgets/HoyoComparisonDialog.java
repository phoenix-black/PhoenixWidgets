package com.blackphoenix.phoenixwidgets;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blackphoenix.phoenixwidgets.listeners.ComparisonDialogInterface;


/**
 * Created by Praba on 08-02-2017.
 */

public abstract class HoyoComparisonDialog extends AlertDialog {

    private TextView textViewTitleOne;
    private TextView textViewTitleTwo;

    private TextView textViewContentOne;
    private TextView textViewContentTwo;

    private LinearLayout layoutOne;
    private LinearLayout layoutTwo;

    private ImageView closeButton;

    ComparisonDialogInterface comparisonDialogInterface;

    private String progressTextOne = "Please Wait...";
    private String progressTextTwo = "Please Wait...";
    private String progressTitleOne = "Loading!";
    private String progressTitleTwo = "Loading!";


    long WAIT_TIME = 60*1000;
    Context _context;

    public abstract void onTimedOut();

    public HoyoComparisonDialog(Context context, String textOne, String textTwo) {
        super(context, R.style.ProgressDialogTheme);
        this.progressTextOne = textOne;
        this.progressTextTwo = textTwo;
        this._context = context;
    }


    public HoyoComparisonDialog(Context context) {
        super(context, R.style.ProgressDialogTheme);
        this._context = context;
    }

    public HoyoComparisonDialog setContentOne(String text){
        if(_context != null) {
            this.progressTextOne = text;
            return this;
        }
        return null;
    }

    public HoyoComparisonDialog setContentTwo(String text){
        if(_context != null) {
            this.progressTextTwo = text;
            return this;
        }
        return null;
    }

    public HoyoComparisonDialog setTitleOne(String text){
        if(_context != null) {
            this.progressTitleOne = text;
            return this;
        }
        return null;
    }

    public HoyoComparisonDialog setTitleTwo(String text){
        if(_context != null) {
            this.progressTitleTwo = text;
            return this;
        }
        return null;
    }

    public HoyoComparisonDialog setInterface(ComparisonDialogInterface dialogDataInterface){
        if(_context != null) {
            this.comparisonDialogInterface = dialogDataInterface;
            return this;
        }
        return null;
    }



    @Override
    protected void onStart(){
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_comparison);
        setCanceledOnTouchOutside(false);
        setCancelable(false);

        textViewContentOne = (TextView)findViewById(R.id.dialogCompare_contentOne);
        textViewContentTwo = (TextView)findViewById(R.id.dialogCompare_contentTwo);
        textViewTitleOne = (TextView)findViewById(R.id.dialogCompare_titleOne);
        textViewTitleTwo = (TextView)findViewById(R.id.dialogCompare_titleTwo);
        closeButton = findViewById(R.id.dialogCompare_close);


        textViewContentOne.setText(progressTextOne);
        textViewContentTwo.setText(progressTextTwo);
        textViewTitleOne.setText(progressTitleOne);
        textViewTitleTwo.setText(progressTitleTwo);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTimedOut();
                dismiss();
            }
        });

        comparisonDialogInterface = new ComparisonDialogInterface() {

            @Override
            public void updateDataOne(@NonNull String newData) {
                textViewContentOne.setText(newData);
            }

            @Override
            public void updateDataTwo(@NonNull String newData) {
                textViewContentTwo.setText(newData);
            }

            @Override
            public void updateTitleOne(@NonNull String newData) {
                textViewTitleOne.setText(newData);
            }

            @Override
            public void updateTitleTwo(@NonNull String newData) {
                textViewTitleTwo.setText(newData);
            }
        };

    }

    public ComparisonDialogInterface getProgressDialogDataInterface(){
        return this.comparisonDialogInterface;
    }

    public boolean updateContentOne(@NonNull String text){
        if(isInterfaceReady()){
            comparisonDialogInterface.updateDataOne(text);
            return true;
        }
        return false;
    }

    public boolean updateContentTwo(@NonNull String text){
        if(isInterfaceReady()){
            comparisonDialogInterface.updateDataTwo(text);
            return true;
        }
        return false;
    }

    public boolean updateTitleOne(@NonNull String text){
        if(isInterfaceReady()){
            comparisonDialogInterface.updateTitleOne(text);
            return true;
        }
        return false;
    }

    public boolean updateTitleTwo(@NonNull String text){
        if(isInterfaceReady()){
            comparisonDialogInterface.updateTitleTwo(text);
            return true;
        }
        return false;
    }

    public boolean isInterfaceReady(){
        return comparisonDialogInterface!=null;
    }

    /*@Override
    public void show(){
        super.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isShowing()) {
                    Toast.makeText(_context, "Timed Out", Toast.LENGTH_SHORT).show();
                    onTimedOut();
                    dismiss();
                }
            }
        },WAIT_TIME);

    }*/
}
