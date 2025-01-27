package com.example.finalaudiobook.Screens;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalaudiobook.MainActivity;
import com.example.finalaudiobook.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText emailInput, pwdInput;
    Button loginBtn;
    CheckBox rememberMe;
    ProgressBar loader;
    CheckBox checkBox;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailInput);
        pwdInput = findViewById(R.id.pwdInput);
        rememberMe = findViewById(R.id.rememberMe);
        loader = findViewById(R.id.loader);
        loginBtn = findViewById(R.id.loginBtn);
            sharedPreferences=getSharedPreferences("myData",MODE_PRIVATE);
            editor=sharedPreferences.edit();

        if(sharedPreferences.getString("loginStatus","").equals("true")){
            startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
            finish();
        }


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
        pwdInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pwdValidation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        findViewById(R.id.forgetbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));

            }
        });

        findViewById(R.id.registerBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });


    }

    public boolean emailValidation() {
        String input = emailInput.getText().toString().trim();
        if (input.equals("")) {
            emailInput.setError("Email Address is Required!!!");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
            emailInput.setError("Enter Valid Email Address!!!");
            return false;
        } else {
            emailInput.setError(null);
            return true;
        }
    }

    public boolean pwdValidation() {
        String input = pwdInput.getText().toString().trim();
        if (input.equals("")) {
            pwdInput.setError("Password is Required!!!");
            return false;
        } else if (input.length() < 8) {
            pwdInput.setError("Password at least 8 Characters!!!");
            return false;
        } else {
            pwdInput.setError(null);
            return true;
        }
    }

    public void validation() {
        if (MainActivity.connectionCheck(LoginActivity.this)) {
            boolean emailErr = false, pwdErr = false;
            emailErr = emailValidation();
            pwdErr = pwdValidation();
            if ((emailErr && pwdErr) == true) {
                loader.setVisibility(View.VISIBLE);
                loginBtn.setVisibility(View.GONE);
                Dialog dialog = new Dialog(LoginActivity.this);
                dialog.setContentView(R.layout.dialog_loading);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                TextView msg = dialog.findViewById(R.id.msgDialog);
                msg.setText("Loading...");
                dialog.show();

                // Declare Firebase Authentication Object
                FirebaseAuth auth = FirebaseAuth.getInstance();

//                   Default Clear All Credentials And SharedPreferences
                editor.clear();
                editor.commit();
                auth.signOut();






                // Create User into Firebase Authentication
                auth.signInWithEmailAndPassword(emailInput.getText().toString().trim(), pwdInput.getText().toString().trim())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {


                                // Check Status From Firebase Realtime Database by UID

                                // Get UID from Firebase Authentication
                                String UID = auth.getUid();
                                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                                myRef.child("Users").child("" + UID).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            String status = snapshot.child("status").getValue().toString();
                                            if (status.equals("1")) {
                                                dialog.dismiss();
                                                Dialog dialog = new Dialog(LoginActivity.this);
                                                dialog.setContentView(R.layout.dialog_success);
                                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                                dialog.setCanceledOnTouchOutside(false);
                                                dialog.setCancelable(false);
                                                TextView msg = dialog.findViewById(R.id.msgDialog);
                                                msg.setText("Login Successfully!!!");
                                                dialog.show();

//                                                Working of Remember Me
                                                if(rememberMe.isChecked()){
//                                                 If Remember is Checked
                                                    editor.putString("loginStatus","true");
                                                    editor.putString("UID",UID);
                                                    editor.commit();

                                                }else{
//                                                    If Remember is UnChecked
                                                    editor.putString("loginStatus","false");
                                                    editor.putString("UID",UID);
                                                    editor.commit();

                                                }

                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        dialog.dismiss();
                                                        startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
                                                        finish();
                                                    }
                                                }, 4000);
                                            } else if (status.equals("0")) {
                                                dialog.dismiss();
                                                Dialog dialog = new Dialog(LoginActivity.this);
                                                dialog.setContentView(R.layout.dialog_error);
                                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                                dialog.setCanceledOnTouchOutside(false);
                                                dialog.setCancelable(false);
                                                TextView msg = dialog.findViewById(R.id.msgDialog);
                                                msg.setText("Your Account Is Suspended By Admin");
                                                dialog.show();
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        dialog.dismiss();
                                                        loader.setVisibility(View.GONE);
                                                        loginBtn.setVisibility(View.VISIBLE);
                                                    }
                                                }, 4000);
                                            }
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.dismiss();
                                Dialog dialog = new Dialog(LoginActivity.this);
                                dialog.setContentView(R.layout.dialog_error);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.setCancelable(false);
                                TextView msg = dialog.findViewById(R.id.msgDialog);
                                msg.setText("Your Email OR Password Is Wrong!!!");
                                dialog.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.dismiss();
                                        loader.setVisibility(View.GONE);
                                        loginBtn.setVisibility(View.VISIBLE);
                                    }
                                }, 4000);
                            }
                        });

            }

        }
    }
}