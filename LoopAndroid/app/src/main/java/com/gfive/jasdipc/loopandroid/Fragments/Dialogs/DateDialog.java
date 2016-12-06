package com.gfive.jasdipc.loopandroid.Fragments.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.gfive.jasdipc.loopandroid.Helpers.FormatHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

/**
 * Created by JasdipC on 2016-10-19.
 */

public class DateDialog extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {

    private TextView datePickerTV;

    @SuppressLint("ValidFragment")
    public DateDialog(View view) {
        datePickerTV = (TextView) view;
    }

    public Dialog onCreateDialog(Bundle savedInstance) {
        final Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, day, month, year);

        //TODO: get rid of that stupid double header
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        return datePickerDialog;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String readableDate = FormatHelper.getReadableDate(year + "-" + ++monthOfYear + "-" + dayOfMonth);
        datePickerTV.setText(readableDate);
    }
}
