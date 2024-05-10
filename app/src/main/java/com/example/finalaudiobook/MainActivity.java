package com.example.finalaudiobook;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalaudiobook.Screens.Splash_Screen_Activity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Intent intent=new Intent(MainActivity.this, Splash_Screen_Activity.class);
        startActivity(intent);
        finish();
    }
}