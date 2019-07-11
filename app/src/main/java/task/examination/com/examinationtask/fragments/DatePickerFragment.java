package task.examination.com.examinationtask.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Objects;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{
    private Calendar calendar;
    private Calendar maxCalendar;
    private Calendar minCalendar;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    private static final String TAG = DatePickerFragment.class.getSimpleName();

    public void setup(Calendar calendar, DatePickerDialog.OnDateSetListener onDateSetListener){
        this.calendar = calendar;
        this.onDateSetListener = onDateSetListener;
    }

    public void setup(Calendar calendar, Calendar maxCalendar, DatePickerDialog.OnDateSetListener onDateSetListener){
        this.calendar = calendar;
        this.maxCalendar = maxCalendar;
        this.onDateSetListener = onDateSetListener;
    }

    public void setup(Calendar calendar, Calendar maxCalendar, Calendar minCalendar){
        this.calendar = calendar;
        this.maxCalendar = maxCalendar;
        this.minCalendar = minCalendar;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(Objects.requireNonNull(getActivity()),android.R.style.Theme_Holo_Light_Dialog_MinWidth, this, year, month, day);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if(minCalendar!=null){
            dialog.getDatePicker().setMinDate(minCalendar.getTimeInMillis());
        }

        if(maxCalendar != null){
            dialog.getDatePicker().setMaxDate(maxCalendar.getTimeInMillis());
        }

        return dialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        if(onDateSetListener != null) onDateSetListener.onDateSet(view, year, month, day);
        else Log.w(TAG, "onDateSetListener callback не инициализирован.");
    }
}
