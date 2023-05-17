package com.corylab.citatum.presentation.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.corylab.citatum.R;
import com.corylab.citatum.network.ForismaticApi;
import com.corylab.citatum.network.ForismaticQuote;
import com.corylab.citatum.presentation.viewmodel.QuoteViewModel;
import com.corylab.citatum.presentation.viewmodel.SharedPreferencesViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        CardView logoImageView = findViewById(R.id.ss_cv);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logoImageView.startAnimation(animation);

        SharedPreferencesViewModel sharedPreferencesViewModel = new ViewModelProvider(this).get(SharedPreferencesViewModel.class);
        QuoteViewModel quoteViewModel = new ViewModelProvider(this).get(QuoteViewModel.class);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        long lastRequestTimestamp = sharedPreferencesViewModel.getLong("last_request_timestamp",
                (Calendar.getInstance().getTimeInMillis() - 24 * 60 * 60 * 1000) - 1);
        Calendar calendar = Calendar.getInstance();
        long currentTimestamp = calendar.getTimeInMillis();

        new Handler().postDelayed(() -> {
            if (currentTimestamp - lastRequestTimestamp > 24 * 60 * 60 * 1000) {
                executorService.execute(() -> {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.forismatic.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    ForismaticApi forismaticApi = retrofit.create(ForismaticApi.class);
                    Call<ForismaticQuote> call = forismaticApi.getQuote("getQuote", "json", null, "ru");
                    call.enqueue(new Callback<ForismaticQuote>() {
                        @Override
                        public void onResponse(@NonNull Call<ForismaticQuote> call, @NonNull Response<ForismaticQuote> response) {
                            if (response.isSuccessful()) {
                                ForismaticQuote forismaticResponse = response.body();
                                if (forismaticResponse != null) {
                                    sharedPreferencesViewModel.setString("quote_text", forismaticResponse.getQuoteText());
                                    sharedPreferencesViewModel.setString("quote_author", forismaticResponse.getQuoteAuthor());
                                    sharedPreferencesViewModel.setLong("last_request_timestamp", currentTimestamp);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ForismaticQuote> call, @NonNull Throwable t) {
                        }
                    });
                });
            }

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent;
            if (currentUser != null) {
                intent = new Intent(this, MainActivity.class);
            } else {
                intent = new Intent(this, LoginActivity.class);
            }
            this.startActivity(intent);
            this.finish();

            quoteViewModel.removeOutdatedQuotes();
        }, 1000);
    }
}