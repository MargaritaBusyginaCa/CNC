package com.example.cnc.supporters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by NyNguyen on Feb 10, 2021
 */

public class InputValidation {
    private Context context;

    /**
     * constructor
     *
     * @param context
     */
    public InputValidation(Context context) {
        this.context = context;
    }

    /**
     * method to check InputEditText filled .
     *
     * @param inputText
     * @param textView
     * @param message
     * @return
     */
    public boolean isFilled(EditText inputText, TextView textView, String message) {
        String value = inputText.getText().toString().trim();

        if (value.isEmpty()) {
            // textView.setError(message);
            inputText.setError(message);
            hideKeyboardFrom(inputText);
            return false;
        } else {
            //textView.setErrorEnabled(false);
            // textView.setError(null);
            inputText.setError(null);
        }

        return true;
    }


    /**
     * method to check inputText has valid email .
     *
     * @param inputText
     * @param textView
     * @param message
     * @return
     */
    public boolean isEmail(EditText inputText, TextView textView, String message) {
        String value = inputText.getText().toString().trim();
        String emailPattern = ".*@algonquinlive\\.com";
//        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
        if (value.isEmpty()) {
            //textView.setError(message);
            inputText.setError(message);
            hideKeyboardFrom(inputText);
            return false;
            //only algonquin email ar
        }else if (!value.matches(emailPattern)){
            inputText.setError("Email address should be an algonquin's email");
            hideKeyboardFrom(inputText);
            return false;
        } else {
            // textView.setErrorEnabled(false);
            //textView.setError(null);
            inputText.setError(null);
        }
        return true;
    }
    /**
     * method to check inputText1 has matched inputText2 .
     *
     * @param inputText1
     * @param inputText2
     * @param textView
     * @param message
     * @return
     */
    public boolean isInputMatches(EditText inputText1, EditText inputText2, TextView textView, String message) {
        String value1 = inputText1.getText().toString().trim();
        String value2 = inputText2.getText().toString().trim();
        if (!value1.contentEquals(value2)) {
            //textView.setError(message);
            inputText2.setError(message);
            hideKeyboardFrom(inputText2);
            return false;
        } else {
            // textView.setErrorEnabled(false);
            // textView.setError(null);
            inputText2.setError(null);
        }
        return true;
    }

    /**
     * method to Hide keyboard
     *
     * @param view
     */
    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
