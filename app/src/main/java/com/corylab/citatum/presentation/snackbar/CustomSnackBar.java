package com.corylab.citatum.presentation.snackbar;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.corylab.citatum.R;
import com.google.android.material.snackbar.Snackbar;

public class CustomSnackBar {

    public static void createSnackbar(View view, Context context, int id) {
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbar.getView().getLayoutParams();
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        params.topMargin = 15;
        View customView = LayoutInflater.from(context).inflate(R.layout.show_snackbar, null);
        TextView message = customView.findViewById(R.id.ss_text);
        message.setText(id);
        snackbar.getView().setBackgroundResource(R.drawable.empty_drawable);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.addView(customView, 0);
        snackbar.show();
    }
}
