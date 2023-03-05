package com.brandon.corejava.utilities;

import android.content.Context;

import com.brandon.uicore.R;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.annotation.Nullable;

public class CustomDatePicker {
    public void openDialog(
            Context context,
            @Nullable Runnable onOpen,
            @Nullable Consumer<List<Integer>> onDateSet,
            @Nullable Runnable onDismiss
    ) {
        Objects.requireNonNull(onOpen).run();
        Calendar calendar = Calendar.getInstance();
        android.app.DatePickerDialog dialog = new android.app.DatePickerDialog(
                context,
                R.style.MySpinnerStyle,
                null,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dialog.setOnDateSetListener((view, year, month, dayOfMonth) ->
                Objects.requireNonNull(onDateSet).accept(List.of(year, month, dayOfMonth))
        );
        dialog.setOnDismissListener(view -> Objects.requireNonNull(onDismiss).run());
        dialog.show();
    }
}
