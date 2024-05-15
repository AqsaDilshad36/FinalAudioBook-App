package com.example.finalaudiobook.Screens;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalaudiobook.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText nameInput,emailInput,pwdInput,cpwdInput;

    Button registerBtn;

    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        nameInput.findViewById(R.id.nameInput);
        emailInput.findViewById(R.id.emailInput);
        pwdInput.findViewById(R.id.pwd);
        cpwdInput.findViewById(R.id.cpwd);

        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                nameValidation();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailValidation();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pwdInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pwdValidation();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cpwdInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cpwdValidation();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


     findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {RegisterActivity.super.onBackPressed();

         }
     });

    }


    public boolean nameValidation(){
        String input=nameInput.getText().toString().trim();
        String regex = "^[a-zA-Z\\s]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if(input.equals("")){
            nameInput.setError("Name is Required!!!");
            return false;
        }else if(input.length() <3){
            nameInput.setError("Name at leas 3 Characters!!! ");
            return false;
        }else if(!matcher.matches()){
            nameInput.setError("Only text are allowed!!!");
            return false;
        }else{
            nameInput.setError(null);
            return true;
        }
    }

    public boolean emailValidation(){
        String input=emailInput.getText().toString().trim();

        if(input.equals("")){
            emailInput.setError("Email Address is Required!!!");
            return false;
        }else if((!Patterns.EMAIL_ADDRESS.matcher(input).matches())){
            emailInput.setError("Enter Valid Email Address!!!");
            return false;
        }else{
            emailInput.setError(null);
            return true;
        }
    }

    public boolean pwdValidation(){
        String input=nameInput.getText().toString().trim();

        if(input.equals("")){
            pwdInput.setError("Password is Required!!!");
            return false;
        }else if(input.length() < 8){
            pwdInput.setError("Password at leas  Characters!!! ");
            return false;
        }else{
            pwdInput.setError(null);
            return true;
        }
    }

    public boolean cpwdValidation(){
        String input=nameInput.getText().toString().trim();
        String pwd=pwdInput.getText().toString().trim();
        if(input.equals("")){
            cpwdInput.setError(" Confirm Password is Required!!!");
            return false;
        }else if(input.length() < 8) {
            cpwdInput.setError("Confirm Password at leas  Characters!!! ");
            return false;
        }else if(!input.equals(pwd)){
            cpwdInput.setError("Confirm Password is not matched!!!");
            return false;

        }else{
            cpwdInput.setError(null);
            return true;
        }
    }

}