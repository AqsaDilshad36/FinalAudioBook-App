package com.example.finalaudiobook.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.finalaudiobook.Adapter.OnBoardingAdapter;
import com.example.finalaudiobook.R;

public class OnBoardingActivity extends AppCompatActivity {



    LinearLayout onBoardingIndicators;
    Button nextBtn, skipBtn;
    ViewPager2 onBoardingViewPager;
    OnBoardingAdapter onBoardingAdapter;
    String prevStarted = "yes";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_on_boarding);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.nextBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(OnBoardingActivity.this,LoginActivity.class));
            }
        });

        findViewById(R.id.skipBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OnBoardingActivity.this,ErrorActivity.class));
            }
        });
    }
}