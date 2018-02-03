package com.blackphoenix.phoenixwidgets.listeners;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by w on 23-02-2017.
 */

public abstract class InputTextChangeListener implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        validateText(editable.toString());
    }

    public abstract void validateText(String data);

}
