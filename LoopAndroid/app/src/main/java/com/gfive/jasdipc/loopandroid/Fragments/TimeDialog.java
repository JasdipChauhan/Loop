package com.gfive.jasdipc.loopandroid.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.gfive.jasdipc.loopandroid.Helpers.FormatHelper;

import java.util.Calendar;

/**
 * Created by JasdipC on 2016-10-20.
 */

public class TimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private TextView timeTV;

    public TimeDialog(View view) {
        timeTV = (TextView) view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String readableTime = FormatHelper.getReadableTime(hourOfDay + ":" + minute);
        timeTV.setText(readableTime);
    }


}
