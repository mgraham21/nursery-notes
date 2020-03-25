package net.nurserynotes.controller;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import net.nurserynotes.R;
import net.nurserynotes.controller.DateTimePickerFragment.Mode;
import net.nurserynotes.model.ActivityContent;
import net.nurserynotes.model.entity.Activity;
import net.nurserynotes.model.entity.Child;
import net.nurserynotes.model.entity.Record;
import net.nurserynotes.viewModel.MainViewModel;

public class RecordEditFragment extends DialogFragment implements DateTimePickerFragment.OnChangeListener {

  private static final String ID_KEY = "id";
  private static final int START_TAG = 1;
  private static final int END_TAG = 2;

  private long id;
  private View root;
  private MainViewModel viewModel;
  private EditText notes;
  private EditText startDate;
  private EditText startTime;
  private EditText endTime;
  private AutoCompleteTextView activityName;
  private AutoCompleteTextView childName;
  private List<Activity> activities;
  private List<Child> children;
  private Record record;
  private Calendar start;
  private Calendar end;
  private DateFormat dateFormat;
  private DateFormat timeFormat;


  public static RecordEditFragment newInstance(long id) {
    RecordEditFragment fragment = new RecordEditFragment();
    Bundle args = new Bundle();
    args.putSerializable(ID_KEY, id);
    fragment.setArguments(args);
    return fragment;
  }

  @SuppressLint("InflateParams")
  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    if (getArguments() != null) {
      id = (long) getArguments().getSerializable(ID_KEY);
    } else {
      id = Long.parseLong(null);
    }
    root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_record_edit, null, false);
    notes = root.findViewById(R.id.notes);
    activityName = root.findViewById(R.id.activity_name);
    childName = root.findViewById(R.id.child_name);
    startDate = root.findViewById(R.id.start_date);
    startTime = root.findViewById(R.id.start_time);
    endTime = root.findViewById(R.id.end_time);
    dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
    timeFormat = android.text.format.DateFormat.getTimeFormat(getContext());
    startDate.setOnClickListener((v) -> {
      DateTimePickerFragment fragment = DateTimePickerFragment.createInstance(Mode.DATE, start, START_TAG);
      fragment.show(getChildFragmentManager(), fragment.getClass().getName());
    });
    startTime.setOnClickListener((v) -> {
      DateTimePickerFragment fragment = DateTimePickerFragment.createInstance(Mode.TIME, start, START_TAG);
      fragment.show(getChildFragmentManager(), fragment.getClass().getName());
    });
    endTime.setOnClickListener((v) -> {
      DateTimePickerFragment fragment = DateTimePickerFragment.createInstance(Mode.TIME, end, END_TAG);
      fragment.show(getChildFragmentManager(), fragment.getClass().getName());
    });
    //noinspection ConstantConditions
    return new Builder(getContext())
        .setIcon(R.drawable.ic_create)
        .setTitle(R.string.activity_details)
        .setView(root)
        .setPositiveButton(android.R.string.ok, (dlg, which) -> save())
        .setNegativeButton(android.R.string.cancel, (dlg, which) -> {})
        .create();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    viewModel.getActivities().observe(getViewLifecycleOwner(), (activities) -> {
      this.activities = activities;
      ArrayAdapter<Activity> adapter =
          new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, activities);
      activityName.setAdapter(adapter);
    });
    viewModel.getChildren().observe(getViewLifecycleOwner(), (children) -> {
      this.children = children;
      ArrayAdapter<Child> adapter =
          new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, children);
      childName.setAdapter(adapter);
    });
    if (id != 0) {
      viewModel.getRecord().observe(getViewLifecycleOwner(), (record) -> {
        //TODO Set the values of activity name, child name, notes, start, end based on contents of record.
        updateDates();
      });
      viewModel.setActivityId(id);
    } else {
      record = new Record();
      start = Calendar.getInstance();
      end = Calendar.getInstance();
      updateDates();
    }
  }

  private void save() {
    Activity activity = null;
    for (Activity a : activities) {
      if (activityName.getText().toString().trim().equalsIgnoreCase(a.toString())) {
        record.setActivityId(a.getId());
        break;
      }
    }
    for (Child c : children) {
      if (childName.getText().toString().trim().equalsIgnoreCase(c.toString())) {
        record.setChildId(c.getId());
        break;
      }
    }
    record.setStart(start.getTime());
    record.setEnd(end.getTime());
    record.setNotes(notes.getText().toString().trim());
    viewModel.save(record);
 }

  @Override
  public void onChange(Calendar calendar, int tag) {
    if (tag == START_TAG) {
      start = calendar;
    } else {
      end = calendar;
    }
    updateDates();
  }

  private void updateDates() {
    startDate.setText(dateFormat.format(start.getTime()));
    startTime.setText(timeFormat.format(start.getTime()));
    endTime.setText(timeFormat.format(end.getTime()));
  }
}
