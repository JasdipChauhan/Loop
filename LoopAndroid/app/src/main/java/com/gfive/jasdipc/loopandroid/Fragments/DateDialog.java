package com.gfive.jasdipc.loopandroid.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by JasdipC on 2016-10-19.
 */

public class DateDialog extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {

    private TextView datePickerTV;

    public DateDialog(View view) {
        datePickerTV = (TextView) view;
    }

    public Dialog onCreateDialog(Bundle savedInstance) {
        final Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, day, month, year);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        datePickerTV.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
    }
}
