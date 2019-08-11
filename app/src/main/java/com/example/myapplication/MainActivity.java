package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void clickButton(View v) {
        Intent myIntent = new Intent(getApplicationContext(), InputActivity.class);
        startActivity(myIntent);
    }

    public void clickButton2(View v) {
        Intent myIntent = new Intent(getApplicationContext(), ViewActivity.class);
        startActivity(myIntent);
    }

    public void clickButton3(View v) {
        Intent myIntent = new Intent(getApplicationContext(), ViewActivity.class);
        startActivity(myIntent);
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("アプリを終了しますか");
        builder.setNegativeButton("いいえ",null);
        builder.setPositiveButton("終了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        builder.show();
    }
}
