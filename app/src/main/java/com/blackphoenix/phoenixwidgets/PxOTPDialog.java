package com.blackphoenix.phoenixwidgets;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blackphoenix.phoenixwidgets.listeners.InputTextChangeListener;
import com.blackphoenix.phoenixwidgets.listeners.ProgressDialogDataInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Praba on 27-03-2018.
 * ToDo
 * 1. Request New OTP Listener is compulsory -> So why not add it as parameter in constructor or abstract method
 * 2. OpenMessage and RequestNewOTP -> Convert them as generic : ActionOne and ActionTwo : so that user can define their owen Actions
 */

public class PxOTPDialog extends Dialog {

    private Context dn_dialogContext;
    private static String LOG_TITLE = PxOTPDialog.class.getSimpleName();
    private final long MINUTE = 60;
    private final long otpCountDownTime = 60 * 1000;

    private String otpID = null;
    private String openMessageInfo = "If the registered mobile number is present in this device, Click this button to open the messages";
    private String requestNewOTPInfo = "If you haven't received OTP yet, click this button to request for a new OTP";

    private long openMessageTime = -1;
    private long newOTPTime = otpCountDownTime;

    private boolean isOpenMessageInfoEnabled = true;
    private boolean isRequestNewOTPInfoEnabled = true;

    private String otpValidationRule = null;
    private String otpValidationMessage = null;

    private boolean _isOTPValid = false;


    private TextView vDialogTitle;
    private TextView vOTPID;
    private TextView vOTPTimer;
    private EditText vOTPInput;

    private Button vActionCancel;
    private Button vActionVerify;

    private ImageView vActionOpenMessage;
    private Button vActionRequestNewOTP;


    private RelativeLayout vLayoutAdvancedAction;


    private OnOTPVerifyListener otpVerifyListener;
    private OnCancelListener cancelListener;
    private OnRequestNewOTPListener requestNewOTPListener;
    private UIInterface uiInterface;

    private CountDownTimer otpCountDownTimer;

    public interface OnOTPVerifyListener {
        void onOTPVerify(String data);
    }

    public interface OnCancelListener {
        void onCancelled();
    }

    public interface OnRequestNewOTPListener {
        void onRequestNewOTP(View view);
    }

    public interface UIInterface {
        void setOTPID(String data);
    }

    /*
        setData
     */

    public PxOTPDialog setOTPID(@NonNull String text){
        this.otpID = text;
        return this;
    }

    public PxOTPDialog setOpenMessageInfo(@NonNull String text){
        this.openMessageInfo = text;
        return this;
    }

    public PxOTPDialog setRequestNewOTPInfo(@NonNull String text){
        this.requestNewOTPInfo = text;
        return this;
    }

    public PxOTPDialog setOpenMessageInfoEnabled(boolean status){
        this.isOpenMessageInfoEnabled = status;
        return this;
    }

    public PxOTPDialog setRequestNewOTPInfo(boolean status){
        this.isRequestNewOTPInfoEnabled = status;
        return this;
    }


    public PxOTPDialog setOpenMessageTime(long timeMilliSec){
        this.openMessageTime = timeMilliSec;
        return this;
    }

    public PxOTPDialog setNewOTPTime(long timeMilliSec){
        this.newOTPTime = timeMilliSec;
        return this;
    }


    /*
        Set Listeners
     */

    public PxOTPDialog setSetDataValidationRule(@NonNull String rule, @NonNull String errorMessage){
        this.otpValidationRule = rule;
        this.otpValidationMessage = errorMessage;
        return this;
    }

    public PxOTPDialog setOTPVerifyListener(@NonNull OnOTPVerifyListener listener){
        this.otpVerifyListener = listener;
        return this;
    }

    public PxOTPDialog setCancelListener(@NonNull OnCancelListener listener){
        this.cancelListener = listener;
        return this;
    }

    public PxOTPDialog setRequestNewOTPListener(@NonNull OnRequestNewOTPListener listener){
        this.requestNewOTPListener = listener;
        return this;
    }

    public boolean updateDataInput(@NonNull String data){
        if(uiInterface!=null) {
            uiInterface.setOTPID(data);
            return true;
        } else {
            return false;
        }
    }



    public PxOTPDialog(Context context) {
        this(context,  R.style.PxwCustomDialogTheme);
    }

    public PxOTPDialog(Context context, int themeResId) {
        super(context, themeResId);
        dn_dialogContext = context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pxw_dialog_otp);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        Window dialogWindow = getWindow();

        if(dialogWindow!=null) {
            dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }

        vOTPID = (TextView) findViewById(R.id.dialogOTP_otpIDInput);
        vOTPTimer = (TextView) findViewById(R.id.dialogOTP_otpTimer);


        vOTPInput = (EditText)findViewById(R.id.dialogOTP_otpInput);

        vActionCancel = (Button)findViewById(R.id.dialogOTP_actionCancel);
        vActionVerify = (Button)findViewById(R.id.dialogOTP_actionVerify);

        vActionRequestNewOTP = (Button) findViewById(R.id.dialogOTP_actionRequestNewOTP);
        vActionOpenMessage = (ImageView) findViewById(R.id.dialogOTP_actionOpenMessage);

        vLayoutAdvancedAction = (RelativeLayout) findViewById(R.id.dialogOTP_layoutAdvancedAction);


        if(otpID !=null && otpID.length()>0){
            vOTPID.setText(otpID);
        }

        uiInterface = new UIInterface() {
            @Override
            public void setOTPID(String data) {
                vOTPID.setText(data);
            }
        };


        /*
            Cancel Click Listener
         */

        vActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cancelListener!=null){
                    cancelListener.onCancelled();
                }
                try {
                    if (otpCountDownTimer != null) {
                        otpCountDownTimer.cancel();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

                dismiss();
            }
        });

        /*
            Verify Click Listener
         */

        vActionVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!_isOTPValid){
                    Toast.makeText(dn_dialogContext,"Enter Valid OTP\nOTP should contain only numbers 0-9",Toast.LENGTH_SHORT).show();
                } else {
                    if(otpVerifyListener!=null){
                        otpVerifyListener.onOTPVerify(vOTPInput.getText().toString());
                    }
                    vOTPInput.setText("");
                    try {
                        if (otpCountDownTimer != null) {
                            otpCountDownTimer.cancel();
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    dismiss();
                }
            }
        });

        /*
            Open Message Listener
         */

        vActionOpenMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ToDo : Add this feature here
                Toast.makeText(dn_dialogContext,"Sorry! This Feature is not yet added", Toast.LENGTH_SHORT).show();
            }
        });


        /*
            Request New OTP Listener
         */

        vActionRequestNewOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e(LOG_TITLE,"Request New OTP");

                if(requestNewOTPListener!=null){
                    requestNewOTPListener.onRequestNewOTP(view);
                } else {
                    //ToDo : Handle Here
                    Toast.makeText(dn_dialogContext,"01 BUG Occurred!",Toast.LENGTH_SHORT).show();
                }
            }
        });



        if(otpValidationRule == null || otpValidationRule.length()>0) {
           otpValidationRule = "^[0-9]+$";
           otpValidationMessage = "OTP should be only numbers 0-9";
           // ToDo : Write a proper message
            // else part is not required to handle
        }

        vOTPInput.addTextChangedListener(new InputTextChangeListener() {
            @Override
            public void validateText(String data) {
                String VALIDATION_PATTERN = otpValidationRule;
                Pattern pattern = Pattern.compile(VALIDATION_PATTERN);
                Matcher matcher = pattern.matcher(data);
                if (!matcher.matches()) {
                    vOTPInput.setError(otpValidationMessage);
                    _isOTPValid = false;
                } else {
                    vOTPInput.setError(null);
                    _isOTPValid = true;
                }
            }
        });

        if(newOTPTime <= 5000){
            // ToDo : Cross Check the minimum Request OTP Time
            newOTPTime = 5000;
        }

        if(openMessageTime <= 3000){
            // ToDo : Cross Check the minimum Request OTP Time
            openMessageTime = 3000;
        }

        otpCountDownTimer = new CountDownTimer(newOTPTime,1000) {
            @Override
            public void onTick(long l) {
                long minute = (l / 1000) / 60;
                long seconds = (l / 1000) % 60;

                //if ((l / 1000) == openMessageTime) {
                //Log.e(LOG_TITLE,"Timer : "+l+" Threshold: "+openMessageTime );
                if ((l/1000) == (openMessageTime/1000)) {
                    showOpenMessageAction(true);
                }

                String minuteString = (minute < 10) ? "0" + minute : "" + minute;
                String secondsString = (seconds < 10) ? "0" + seconds : "" + seconds;
                String time = "" + minuteString + ":" + secondsString;

                vOTPTimer.setText(time);
            }

            @Override
            public void onFinish() {
                vOTPTimer.setText("00:00");
                // ToDo : Upgrade this alert Dialog (Message and Design)
                if(requestNewOTPListener!=null) {
                    new PxAlertDialog(dn_dialogContext, "Still haven't received OTP Message!\nRequest for new a OTP")
                            .setAlertMessage("Still haven't received OTP Message!\nRequest for new a OTP")
                            .setPositiveButtonText("Request New OTP")
                            .setNegativeButtonText("Cancel")
                            .setPositiveButtonClickListener(new PxAlertDialog.OnPositiveButtonClickListener() {
                                @Override
                                public void onClick(View view) {
                                    requestNewOTPListener.onRequestNewOTP(view);
                                }
                            }).show();
                } else {
                    // ToDo : BUG Handle Here
                    Toast.makeText(dn_dialogContext,"02 BUG Occurred!",Toast.LENGTH_SHORT).show();
                }
                showRequestNewOTPAction(true);
            }
        };

        otpCountDownTimer.start();

    }

    private void showOpenMessageAction(boolean status){
        if(status){
            //ToDo : Add Animation Here
            vLayoutAdvancedAction.setVisibility(View.VISIBLE);
        } else {
            //ToDo : Add Animation Here
            vLayoutAdvancedAction.setVisibility(View.GONE);
        }
    }

    private void showRequestNewOTPAction(boolean status){
        if(status){
            //ToDo : Add Animation Here
            if(requestNewOTPListener!=null) {
                vActionRequestNewOTP.setVisibility(View.VISIBLE);
            } else {
                // ToDo : BUG : Handle it
            }

        } else {
            //ToDo : Add Animation Here
            vActionRequestNewOTP.setVisibility(View.GONE);
        }
    }

    private MessageDialog getMessageDialog(Context context, String message){
        return new MessageDialog(context,message){

            @Override
            public void onInterfaceReady(ProgressDialogDataInterface dialogInterface) {

            }

            @Override
            public void onTimedOut() {

            }

            @Override
            public void onDismissed() {

            }
        };
    }

    @NonNull
    @Override
    public Bundle onSaveInstanceState() {
        return super.onSaveInstanceState();
    }


    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e(LOG_TITLE,"OnBackPressed");
    }
}
