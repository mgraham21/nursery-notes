package net.nurserynotes.controller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import java.util.Calendar;


public class DateTimePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

  private static final String CALENDAR_KEY = "calendar";
  private static final String MODE_KEY = "mode";
  private static final String TAG_KEY = "tag";

  private Mode mode;
  private Calendar calendar;
  private int tag;
  private OnChangeListener listener;

  public static DateTimePickerFragment createInstance(Mode mode, Calendar calendar, int tag) {
    DateTimePickerFragment fragment = new DateTimePickerFragment();
    Bundle args = new Bundle();
    args.putSerializable(MODE_KEY, mode);
    args.putSerializable(CALENDAR_KEY, calendar);
    args.putInt(TAG_KEY, tag);
    fragment.setArguments(args);
    return fragment;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    Dialog dialog;
    readArguments();
    setupListener();
    if (mode == Mode.DATE) {
      dialog = new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR),
          calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    } else {
      dialog = new TimePickerDialog(getActivity(), this, calendar.get(Calendar.HOUR_OF_DAY),
          calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(getActivity()));
    }
    return dialog;
  }

  @Override
  public final void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    Calendar updateValue = Calendar.getInstance();
    updateValue.setTimeInMillis(calendar.getTimeInMillis());
    updateValue.set(Calendar.YEAR, year);
    updateValue.set(Calendar.MONTH, month);
    updateValue.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    listener.onChange(updateValue, tag);
  }

  @Override
  public final void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    Calendar updateValue = Calendar.getInstance();
    updateValue.setTimeInMillis(calendar.getTimeInMillis());
    updateValue.set(Calendar.HOUR_OF_DAY, hourOfDay);
    updateValue.set(Calendar.MINUTE, minute);
    listener.onChange(updateValue, tag);
  }

  private void readArguments() {
    Bundle args = getArguments();
    if (args == null
        || !args.containsKey(MODE_KEY)
        || (mode = (Mode) args.getSerializable(MODE_KEY)) == null)  {
      mode = Mode.DATE;
    }
    if (args == null
        ||!args.containsKey(CALENDAR_KEY)
        || (calendar = (Calendar) args.getSerializable(CALENDAR_KEY)) == null) {
      calendar = Calendar.getInstance();
    }
    if (args != null) {
      tag = args.getInt(TAG_KEY, 0);
    }
  }

  private void setupListener() {
    Fragment parentFragment = getParentFragment();
    FragmentActivity hostActivity = getActivity();
    if (parentFragment instanceof OnChangeListener) {
      listener = (OnChangeListener) parentFragment;
    } else if (hostActivity instanceof OnChangeListener) {
      listener = (OnChangeListener) hostActivity;
    } else {
      listener = new OnChangeListener() {
        @Override
        public void onChange(Calendar calendar, int tag) {}
      };
    }
  }


  public enum Mode {
    DATE, TIME
  }


  public interface OnChangeListener {

    void onChange(Calendar calendar, int tag);

  }

}
