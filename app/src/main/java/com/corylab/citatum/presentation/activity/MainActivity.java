package com.corylab.citatum.presentation.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.corylab.citatum.R;

/**
 * Главная активити приложения.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}