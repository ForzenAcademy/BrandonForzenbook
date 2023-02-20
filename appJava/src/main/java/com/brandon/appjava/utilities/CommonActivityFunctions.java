package com.brandon.appjava.utilities;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.function.Consumer;

public interface CommonActivityFunctions {

    default void onTextChanged(TextInputEditText editText, Consumer<String> lambda) {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lambda.accept(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };
    }

    default void setFieldVisibility(TextView view, boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

}
