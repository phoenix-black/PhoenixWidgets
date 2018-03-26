package com.blackphoenix.phoenixwidgets;

/**
 * Created by shree on 1/2/2018.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blackphoenix.phoenixwidgets.listeners.InputTextChangeListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Praba on 26-03-2018.
 */

public class PxEditDialog extends Dialog {

    private Context dn_dialogContext;
    private static String LOG_TITLE = PxEditDialog.class.getSimpleName();
    private boolean isDataValid = false;

    private String dataInput = null;
    private String dataTitle = null;
    private int dataIconID = -1;
    private int dataInputType = -1;
    private String dataValidationRule = null;
    private String dataValidationMessage = null;
    private String buttonUpdateText = null;
    private String buttonCancelText = null;

    private boolean _isInputValid = false;

    private OnUpdateListener updateListener;
    private OnCancelListener cancelListener;
    private OnEditClickListener editClickListener;
    private UIInterface uiInterface;

    private EditText dds_dataInput;
    private TextView dds_dataTitle;
    private ImageView dds_dataIcon;

    private Button dds_buttonCancel;
    private Button dds_buttonUpdate;

    public interface OnUpdateListener {
        void onUpdated(String data);
    }

    public interface OnCancelListener {
        void onCancelled();
    }

    public interface OnEditClickListener {
        void onEditClicked(View view);
    }

    public interface UIInterface {
        void setInput(String data);
        void setTitle(String data);
    }


    public PxEditDialog setDataTitle(@NonNull String text){
        this.dataTitle = text;
        return this;
    }

    public PxEditDialog setDataInput(@NonNull String text){
        this.dataInput = text;
        return this;
    }

    public PxEditDialog setDataIconID(int icon_id){
        this.dataIconID = icon_id;
        return this;
    }

    public PxEditDialog setDataInputType(int type){
        this.dataInputType = type;
        return this;
    }

    public PxEditDialog setSetDataValidationRule(@NonNull String rule, @NonNull String errorMessage){
        this.dataValidationRule = rule;
        this.dataValidationMessage = errorMessage;
        return this;
    }

    public PxEditDialog setUpdateListener(@NonNull OnUpdateListener listener){
        this.updateListener = listener;
        return this;
    }

    public PxEditDialog setCancelListener(@NonNull OnCancelListener listener){
        this.cancelListener = listener;
        return this;
    }

    public PxEditDialog setEditClickListener(@NonNull OnEditClickListener listener){
        this.editClickListener = listener;
        return this;
    }

    public boolean updateDataInput(@NonNull String data){
        if(uiInterface!=null) {
            uiInterface.setInput(data);
            return true;
        } else {
            return false;
        }
    }



    public boolean updateDataTitle(@NonNull String data){
        if(uiInterface!=null) {
            uiInterface.setTitle(data);
            return true;
        } else {
            return false;
        }
    }


    public PxEditDialog(Context context) {
        this(context,  R.style.PxwCustomDialogTheme);
    }

    public PxEditDialog(Context context, int themeResId) {
        super(context, themeResId);
        dn_dialogContext = context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pxw_dialog_edit);
        setCanceledOnTouchOutside(false);
        Window dialogWindow = getWindow();

        if(dialogWindow!=null) {
            dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }

        dds_dataTitle = (TextView) findViewById(R.id.dialogEdit_dataTitle);
        dds_dataIcon = (ImageView) findViewById(R.id.dialogEdit_dataIcon);
        dds_dataInput = (EditText)findViewById(R.id.dialogEdit_dataInput);
        dds_buttonCancel = (Button)findViewById(R.id.dialogEdit_actionCancel);
        dds_buttonUpdate = (Button)findViewById(R.id.dialogEdit_actionUpdate);


        uiInterface = new UIInterface() {
            @Override
            public void setInput(String data) {
                dds_dataInput.setText(data);
            }

            @Override
            public void setTitle(String data) {
                dds_dataTitle.setText(data);
            }
        };


        if(dataInput !=null && dataInput.length()>0){
            dds_dataInput.setText(dataInput);
            _isInputValid = true;
        }

        if(dataIconID != -1){
            dds_dataIcon.setImageResource(dataIconID);
        }

        if(dataTitle!=null && dataTitle.length()>0){
            dds_dataTitle.setText(dataTitle);
            dds_dataInput.setHint(dataTitle);
        }

        if(editClickListener!=null){
            dds_dataInput.setInputType(InputType.TYPE_NULL);
            dds_dataInput.setTextIsSelectable(true);

            dds_dataInput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editClickListener.onEditClicked(view);
                }
            });
        } else {
            if(dataInputType!= -1) {
                dds_dataInput.setInputType(dataInputType);
            }
        }

        if(dataValidationRule!=null && dataValidationRule.length()>0) {
            dds_dataInput.addTextChangedListener(new InputTextChangeListener() {
                @Override
                public void validateText(String data) {
                    String VALIDATION_PATTERN = dataValidationRule;
                    Pattern pattern = Pattern.compile(VALIDATION_PATTERN);
                    Matcher matcher = pattern.matcher(data);
                    if (!matcher.matches()) {
                        dds_dataInput.setError(dataValidationMessage);
                        _isInputValid = false;
                    } else {
                        dds_dataInput.setError(null);
                        _isInputValid = true;
                    }
                }
            });
        }

        dds_buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dds_dataInput.setText("");
                if(cancelListener!=null){
                    cancelListener.onCancelled();
                }
                dismiss();
            }
        });


        dds_buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!_isInputValid){
                    Toast.makeText(dn_dialogContext,"Enter Valid Input",Toast.LENGTH_SHORT).show();
                } else {
                    if(dds_dataInput.getText() !=null){
                        String newData = dds_dataInput.getText().toString();
                        dds_dataInput.setText("");

                        if(updateListener!=null){
                            updateListener.onUpdated(newData);
                        }

                        dismiss();
                    }
                }
            }
        });

    }

}
