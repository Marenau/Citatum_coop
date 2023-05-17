package com.corylab.citatum.presentation.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.corylab.citatum.R;
import com.corylab.citatum.databinding.FragmentSettingsBinding;
import com.corylab.citatum.presentation.activity.LoginActivity;
import com.corylab.citatum.presentation.activity.MainActivity;
import com.corylab.citatum.presentation.viewmodel.SharedPreferencesViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private MainActivity activity;
    private FirebaseAuth auth;
    private SharedPreferencesViewModel sharedPreferencesViewModel;

    public SettingsFragment() {
        super(R.layout.fragment_settings);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        activity = (MainActivity) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left);
        } else {
            return AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_left);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        sharedPreferencesViewModel = new ViewModelProvider(this).get(SharedPreferencesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void init() {
        binding.sfUsernameText.setText(sharedPreferencesViewModel.getString("username", "Oops!"));
        FirebaseUser user = auth.getCurrentUser();
        binding.sfEmailText.setText(user.getEmail());

        long creationDate = user.getMetadata().getCreationTimestamp();
        Instant instant = Instant.ofEpochMilli(creationDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy", Locale.getDefault());
        String formattedDate = formatter.format(ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()));
        String sfDateText = getString(R.string.sf_date_text) + " " + formattedDate;
        binding.sfDateText.setText(sfDateText);

        binding.sfQuitBtn.setOnClickListener(view -> {
            auth.signOut();
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
            activity.finish();
        });
    }
}
