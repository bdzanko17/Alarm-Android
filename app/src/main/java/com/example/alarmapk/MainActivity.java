package com.example.alarmapk;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK;
import static androidx.fragment.app.DialogFragment.STYLE_NO_TITLE;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    Button settingButton, addAlarm;
    LinearLayout scrolllista;
    ConstraintLayout coni;
    static int idOfAlarm;
    static Context cont;
    HashMap<Integer,Long> mapOfIdAndTimeOfAlarm;



    //onClick method from main to setting acitivity//
    public void settingActivity(View view) {
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);
    }
    //onClick method from main to addAlarm activity//
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")
    public void addAlarmActivity(View view) {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
        
    }

    //SWITCH BUTTON ZA GASNJENJE VEC NAPRAVLJENOG ALARMA
    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void switchButton(Switch view){

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        if(view.isChecked()){

            //GET TIME OF ALARM BY HIS ID
            long timeOfAlarmById=mapOfIdAndTimeOfAlarm.get(view.getId());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),view.getId(), intent,PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,timeOfAlarmById , pendingIntent);

        }
        else {
            PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), view.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(alarmIntent);
            alarmIntent.cancel();

        }
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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view)
            {
                switchButton(textView);
            }
        });
        textView.setId(idOfAlarm);
        scrolllista.addView(textView);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingButton = findViewById(R.id.settingsButton);
        addAlarm = findViewById(R.id.AlarmButton);
        scrolllista=findViewById(R.id.linearlay);
        coni=findViewById(R.id.con);
        mapOfIdAndTimeOfAlarm= new HashMap<>();
        cont= this;

    }

    //GETTING TIME FROM TIMEPICKER
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
        idOfAlarm++;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar c) {
        final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        final Intent intent = new Intent(this, MyBroadcastReceiver.class);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this, idOfAlarm, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);  // If time in past :D date tomorow
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);


        mapOfIdAndTimeOfAlarm.put(idOfAlarm,c.getTimeInMillis());

    }









}
