package com.brandon.corejava.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.brandon.uicore.R;

import javax.annotation.Nullable;

public class NotificationDialog {

    public void createNotification(Context context, String title, String body, @Nullable Runnable onDismiss, @Nullable Runnable onOpen) {
        if (onOpen != null) {
            onOpen.run();
        }
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle(title);
        dialog.setMessage(body);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE,
                context.getString(R.string.core_error_dismiss),
                (dialog1, which) -> {
                    dialog.dismiss();
                    if (onDismiss != null) {
                        onDismiss.run();
                    }
                });
        dialog.setOnDismissListener(v -> {
            dialog.dismiss();
            if (onDismiss != null) {
                onDismiss.run();
            }
        });
        dialog.show();
    }
}
