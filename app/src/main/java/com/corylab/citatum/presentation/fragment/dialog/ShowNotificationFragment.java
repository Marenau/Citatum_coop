package com.corylab.citatum.presentation.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.corylab.citatum.R;

public class ShowNotificationFragment extends DialogFragment {

    private String title;
    private String message;
    private String buttonText;

    public static ShowNotificationFragment newInstance(String title, String message, String buttonText) {
        ShowNotificationFragment fragment = new ShowNotificationFragment();
        fragment.title = title;
        fragment.message = message;
        fragment.buttonText = buttonText;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_show_notification, null);

        TextView titleTextView = view.findViewById(R.id.snf_title);
        TextView messageTextView = view.findViewById(R.id.snf_message);
        Button button = view.findViewById(R.id.snf_button);

        titleTextView.setText(title);
        messageTextView.setText(message);
        button.setText(buttonText);
        button.setOnClickListener(view1 -> dismiss());

        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.empty_drawable);

        return dialog;
    }
}