package com.example.finalaudiobook.Screens;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalaudiobook.MainActivity;
import com.example.finalaudiobook.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    Button submitBtn;
    EditText emailInput;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        emailInput.findViewById(R.id.emailInput);
        submitBtn = findViewById(R.id.submitBtn);
        loader = findViewById(R.id.loader);


        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                emailValidation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });







        findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPasswordActivity.super.onBackPressed();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });



    }
    public boolean emailValidation(){
        String input = emailInput.getText().toString().trim();
        if(input.equals("")){
            emailInput.setError("Email Address is Required!!!");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(input).matches()){
            emailInput.setError("Enter Valid Email Address!!!");
            return false;
        } else {
            emailInput.setError(null);
            return true;
        }
    }

    public void validation() {
        if (MainActivity.connectionCheck(ForgetPasswordActivity.this)) {
            boolean emailErr = false;
            emailErr = emailValidation();
            if ((emailErr) == true) {
                loader.setVisibility(View.VISIBLE);
                submitBtn.setVisibility(View.GONE);
                Dialog dialog = new Dialog(ForgetPasswordActivity.this);
                dialog.setContentView(R.layout.dialog_loading);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.setCancelable(false);
                TextView msg = dialog.findViewById(R.id.msgDialog);
                msg.setText("Loading...");
                dialog.show();

                // Declare Firebase Authentication Object
                FirebaseAuth auth = FirebaseAuth.getInstance();

                // Send Forgot Password Email By Firebase Authentication
                auth.sendPasswordResetEmail(emailInput.getText().toString().trim())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                dialog.dismiss();
                                startActivity(new Intent(ForgetPasswordActivity.this, Email_SentActivity.class));
                                finish();
                            }
                        });
            }
        }
    }
}