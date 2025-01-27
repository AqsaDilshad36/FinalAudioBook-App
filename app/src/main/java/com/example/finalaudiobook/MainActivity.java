package com.example.finalaudiobook;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalaudiobook.Screens.Splash_Screen_Activity;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    static String UID = "";
      static String name,email,image,role,created_on;
      public static DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("myData", MODE_PRIVATE);
        editor = sharedPreferences.edit();


        if (db == null) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            db = FirebaseDatabase.getInstance().getReference();
        }

        if(!sharedPreferences.getString("UID","").equals("")){
            UID = sharedPreferences.getString("UID","").toString();
            db.child("Users").child(UID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        name = snapshot.child("name").getValue().toString();
                        email = snapshot.child("email").getValue().toString();
                        image = snapshot.child("image").getValue().toString();
                        role = snapshot.child("role").getValue().toString();
                        created_on = snapshot.child("created_on").getValue().toString();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        Intent intent=new Intent(MainActivity.this, Splash_Screen_Activity.class);
        startActivity(intent);
        finish();
    }
//    Internet OR Data Connection Method
    public static boolean connectionCheck(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo dataConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifiConn != null && wifiConn.isConnected()) || (dataConn != null && dataConn.isConnected())) {
            return true;
        } else {
            Dialog loaddialog = new Dialog(context);
            loaddialog.setContentView(R.layout.dialog_connection_error);
            loaddialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            loaddialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            loaddialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            loaddialog.getWindow().setGravity(Gravity.CENTER);
            loaddialog.setCancelable(false);
            loaddialog.setCanceledOnTouchOutside(false);
            loaddialog.show();
            TextView messageTextViewTwo = loaddialog.findViewById(R.id.msgDialog);
            messageTextViewTwo.setText("Please Connect To The Internet To Proceed Further!!!");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loaddialog.dismiss();
                }
            }, 4000);
            return false;
        }
    }

        public static void checkStatus(Context context){
         db.child("Users").child(UID).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
              if(snapshot.exists()){
                  Dialog dialog = new Dialog(context);
                  dialog.setContentView(R.layout.dialog_error);
                  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                  dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                  dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                  dialog.getWindow().setGravity(Gravity.CENTER);
                  dialog.setCanceledOnTouchOutside(false);
                  dialog.setCancelable(false);
                  TextView msg = dialog.findViewById(R.id.msgDialog);
                  msg.setText("Your Account Is Suspended By Admin");

                  String status =snapshot.child("status").getValue().toString();
                  if(status.equals("0")){
                    dialog.show();
                  }else if(status.equals("1")){
                 dialog.dismiss();
                  }
              }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });
        }

//        User Getter

       public static String getName(){return name;}
    public static String getEmail(){return email;}
    public static String getImage(){return image;}
    public static String getRole(){return role;}
    public static String getCreated_On(){return created_on;}


}