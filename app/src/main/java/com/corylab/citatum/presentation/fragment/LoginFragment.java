package com.corylab.citatum.presentation.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import androidx.navigation.Navigation;

import com.corylab.citatum.R;
import com.corylab.citatum.databinding.FragmentLoginBinding;
import com.corylab.citatum.presentation.activity.LoginActivity;
import com.corylab.citatum.presentation.activity.MainActivity;
import com.corylab.citatum.presentation.enumeration.AccountStatus;
import com.corylab.citatum.presentation.fragment.dialog.ShowNotificationFragment;
import com.corylab.citatum.presentation.viewmodel.SharedPreferencesViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginActivity activity;
    private FirebaseAuth auth;
    private SharedPreferencesViewModel sharedPreferencesViewModel;

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        activity = (LoginActivity) context;
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
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }
        sharedPreferencesViewModel = new ViewModelProvider(this).get(SharedPreferencesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
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
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.image_scale);
        binding.lfRegisterCv.setOnClickListener(view -> {
            view.startAnimation(animation);
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
        });
        binding.lfEntranceIcon.setOnClickListener(view -> {
            view.startAnimation(animation);
            singIn();
        });

        binding.lfForgotText.setOnClickListener(view -> {
            view.startAnimation(animation);
            try {
                auth.sendPasswordResetEmail(binding.lfEmailEt.getText().toString());
                showNotification(AccountStatus.FORGOT_SENT);
            } catch (Exception e) {
                showNotification(AccountStatus.FORGOT);
            }
        });
    }

    private void singIn() {
        if (!isNetworkAvailable()) {
            showNotification(AccountStatus.UNCONNECTED);
            return;
        }
        if (sharedPreferencesViewModel.getString("quote_text", "default").equals("default"))
            sharedPreferencesViewModel.setString("quote_text", getString(R.string.hf_day_base_quote));
        String email = binding.lfEmailEt.getText().toString();
        String password = binding.lfPasswordEt.getText().toString();
        if (!email.equals("") && !password.equals("")) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    String userId = user.getUid();
                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                    usersRef.child(userId).child("username").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            sharedPreferencesViewModel.setString("username", dataSnapshot.getValue(String.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                } else showNotification(AccountStatus.INCORRECT);
            });
        } else showNotification(AccountStatus.INCORRECT);
    }

    private void showNotification(AccountStatus status) {
        String title = "", message = "", button = "";
        if (status == AccountStatus.UNCONNECTED) {
            title = getString(R.string.no_connection_title_text);
            message = getString(R.string.no_connection_message_text);
            button = getString(R.string.no_connection_button_text);
        } else if (status == AccountStatus.INCORRECT) {
            title = getString(R.string.incorrect_login_title_text);
            message = getString(R.string.incorrect_login_message_text);
            button = getString(R.string.incorrect_login_button_text);
        } else if (status == AccountStatus.FORGOT) {
            title = getString(R.string.forgot_password_title_text);
            message = getString(R.string.forgot_password_message_text);
            button = getString(R.string.forgot_password_button_text);
        } else if (status == AccountStatus.FORGOT_SENT) {
            title = getString(R.string.forgot_password_sent_title_text);
            message = getString(R.string.forgot_password_sent_message_text);
            button = getString(R.string.forgot_password_sent_button_text);
        }
        ShowNotificationFragment dialog = ShowNotificationFragment.newInstance(title, message, button);
        dialog.show(getParentFragmentManager(), "dialog");
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}