package com.example.finalaudiobook.Screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalaudiobook.MainActivity;
import com.example.finalaudiobook.R;

public class SettingsActivity extends AppCompatActivity {

    Button logoutBtn;
    TextView profileName;
    LinearLayout adminOptions;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        logoutBtn = findViewById(R.id.logoutBtn);
        profileName =findViewById(R.id.profileName);
        adminOptions =findViewById(R.id.adminOptions);
        sharedPreferences = getSharedPreferences("myData",MODE_PRIVATE);
        editor = sharedPreferences.edit();


        MainActivity.checkStatus(SettingsActivity.this);
         profileName.setText(MainActivity.getName());
         if(MainActivity.getRole().equals("admin")){
         adminOptions.setVisibility(View.VISIBLE);
         }


        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsActivity.super.onBackPressed();
            }
        });

        findViewById(R.id.subscriptionBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, SubscriptionActivity.class));
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              editor.clear();
              editor.commit();
              startActivity(new Intent(SettingsActivity.this,LoginActivity.class));
            }
        });
    }
}