package com.brandon.appjava.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.brandon.appjava.R;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Nullable;

public class NotificationDialog {

    public void createNotification(Context context, String title, String body,@Nullable Runnable runnable) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.notification_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        TextView titleText = view.findViewById(R.id.title_text);
        titleText.setText(title);
        TextView bodyText = view.findViewById(R.id.body_text);
        bodyText.setText(body);
        view.findViewById(R.id.dialog_dismiss_button).setOnClickListener(v -> {
            dialog.dismiss();
            Objects.requireNonNull(runnable).run();
        });
        dialog.show();
    }

}
