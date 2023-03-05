package com.brandon.corejava.utilities;


import static com.brandon.corejava.utilities.UserInputErrors.FORMAT_ERROR;
import static com.brandon.corejava.utilities.UserInputErrors.INVALID_INPUT_ERROR;
import static com.brandon.corejava.utilities.UserInputErrors.LENGTH_ERROR;
import static com.brandon.corejava.utilities.UserInputErrors.NO_INPUT_ERROR;

import androidx.core.util.PatternsCompat;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.annotation.Nullable;

public abstract class AccountValidationJava {
    private static final int CODE_CHAR_LIMIT = 6;
    private static final int LOCATION_CHAR_LIMIT = 64;
    private static final int EMAIL_CHAR_LIMIT = 64;
    private static final int DATE_CHAR_LIMIT = 64;
    private static final int NAME_CHAR_LIMIT = 20;
    private static final String INCOMING_DATE_FORMAT = "MMddyyyy";
    private static final String OUTGOING_DATE_FORMAT = "yyyy-MM-dd";

    protected UserInputErrors isValidName(String name) {
        if (name.length() > NAME_CHAR_LIMIT) {
            return LENGTH_ERROR;
        }
        return NO_INPUT_ERROR;
    }

    protected UserInputErrors isValidEmail(String email) {
        if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            return FORMAT_ERROR;
        }
        if (email.length() > EMAIL_CHAR_LIMIT) {
            return LENGTH_ERROR;
        }
        return NO_INPUT_ERROR;
    }

    protected UserInputErrors isValidCode(String code) {
        final char[] codeArr = code.toCharArray();
        for (char element : codeArr) {
            if (!Character.isDigit(element)) return INVALID_INPUT_ERROR;
        }
        if (code.length() > CODE_CHAR_LIMIT) {
            return LENGTH_ERROR;
        }
        return NO_INPUT_ERROR;
    }

    protected UserInputErrors isValidString(String input) {
        if (input.length() > LOCATION_CHAR_LIMIT) {
            return LENGTH_ERROR;
        }
        return NO_INPUT_ERROR;
    }

    @Nullable
    protected Date isValidDate(String date) {
        final char[] dateArr = date.toCharArray();
        final StringBuilder newDate = new StringBuilder();
        for (char element : dateArr) {
            if (Character.isDigit(element)) newDate.append(element);
        }
        final String filteredDate = newDate.toString();
        if (newDate.length() > DATE_CHAR_LIMIT) return null;
        try {
            final SimpleDateFormat inputFormat = new SimpleDateFormat(INCOMING_DATE_FORMAT);
            final java.util.Date tempDate = inputFormat.parse(filteredDate);
            assert tempDate != null;
            final SimpleDateFormat outputFormat = new SimpleDateFormat(OUTGOING_DATE_FORMAT);
            return Date.valueOf(outputFormat.format(tempDate));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
