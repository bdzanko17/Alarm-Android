package com.example.alarmapk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button settingButton,addAlarm;

    //onClick method from main to setting acitivity//
    public void settingActivity(View view){
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);
    }
    //onClick method from main to addAlarm activity//
    public void addAlarmActivity(View view){
        Intent intent = new Intent(MainActivity.this, Alarm.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingButton=findViewById(R.id.settingsButton);
        addAlarm=findViewById(R.id.settingsButton);


    }
}
