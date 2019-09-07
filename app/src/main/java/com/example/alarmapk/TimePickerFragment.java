package com.example.alarmapk;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {
    @Override
    public void onActivityCreated(Bundle arg0) {

        super.onActivityCreated(arg0);
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;


    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog a = new TimePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK, (TimePickerDialog.OnTimeSetListener) getActivity(), hour, minute, DateFormat.is24HourFormat(getActivity()));


        return a;

    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        MainActivity.getAddAlarm().clearAnimation();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        MainActivity.getAddAlarm().clearAnimation();
    }
}

