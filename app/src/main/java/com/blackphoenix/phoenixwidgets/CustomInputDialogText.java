package com.blackphoenix.phoenixwidgets;

/**
 * Created by shree on 1/2/2018.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.blackphoenix.phoenixwidgets.listeners.InputTextChangeListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Praba on 26-02-2017.
 */

public abstract class CustomInputDialogText extends Dialog {

    private Context dn_dialogContext;
    private static String LOG_TITLE = CustomInputDialogText.class.getSimpleName();
    private String defaultName = null;
    private boolean isMobileNumberValid = false;

    public CustomInputDialogText(Context context, String name){
        this(context);
        this.defaultName = name;
    }

    protected CustomInputDialogText(Context context) {
        this(context,  R.style.CustomDialogTheme);
        dn_dialogContext = context;
    }

    private CustomInputDialogText(Context context, int themeResId) {
        super(context, themeResId);
        dn_dialogContext = context;
    }

    EditText dds_inputName;
    EditText dds_inputTitle;

    Button dds_buttonCancel;
    Button dds_buttonCreate;

    boolean _isNameAvailable = false, _isTitleAvailable = false;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_input_text);
        setCanceledOnTouchOutside(false);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        dds_inputName = (EditText)findViewById(R.id.arenaNameDialog_arenaName);
        dds_buttonCancel = (Button)findViewById(R.id.arenaNAmeDialog_actionCancel);
        dds_buttonCreate = (Button)findViewById(R.id.arenaNAmeDialog_actionCreate);


        if(defaultName!=null){
            dds_inputName.setText(defaultName);
            _isNameAvailable = true;
        }

        dds_inputName.addTextChangedListener(new InputTextChangeListener() {
            @Override
            public void validateText(String data) {
                String USERNAME_PATTERN = "[a-zA-Z0-9]{1,8}";
                Pattern pattern = Pattern.compile(USERNAME_PATTERN);
                Matcher matcher = pattern.matcher(data);
                if(!matcher.matches()){
                    dds_inputName.setError("Input must contain alphabets/numbers and size 1-8");
                    _isNameAvailable = false;
                } else{
                    dds_inputName.setError(null);
                    _isNameAvailable = true;
                }
            }
        });


        dds_buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dds_inputName.setText("");
                //dds_inputTitle.setText("");
                dismiss();
            }
        });


        dds_buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!_isNameAvailable){
                    Toast.makeText(dn_dialogContext,"Enter Valid Input",Toast.LENGTH_SHORT).show();
                } else {
                    String password = dds_inputName.getText().toString();
                    dds_inputName.setText("");
                    Log.e("Password",""+password);
                    onInputReady(password);
                    dismiss();
                }
            }
        });

    }

    public abstract void onInputReady(String input);
}
