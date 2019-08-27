package com.example.alarmapk;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;

import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;


import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    Button settingButton, addAlarm;

    LinearLayout scrolllista;



    //onClick method from main to setting acitivity//
    public void settingActivity(View view) {
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);
    }

    //onClick method from main to addAlarm activity//
    public void addAlarmActivity(View view) {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");

    }
    //NIJE IMPLEMENTIRANO
    public void switchButton(Switch view){
        if(view.isChecked()){}
        else ;
    }

    //ADD ALARM ON LAYOUT
    public void addAlarmOnMainPageUI(Calendar c){
        final Switch textView = new Switch(this);
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        textView.setText(timeText);
        textView.setTextSize(30.0f);
        textView.setChecked(true);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                switchButton(textView);
            }
        });
        scrolllista.addView(textView);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingButton = findViewById(R.id.settingsButton);
        addAlarm = findViewById(R.id.settingsButton);
        scrolllista=findViewById(R.id.linearlay);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, i);
        c.set(Calendar.MINUTE, i1);
        c.set(Calendar.SECOND, 0);
        startAlarm(c);
        //ADD ALARM ON MAIN PAGE
        addAlarmOnMainPageUI(c);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);  // If time in past :D date tomorow
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }








}
