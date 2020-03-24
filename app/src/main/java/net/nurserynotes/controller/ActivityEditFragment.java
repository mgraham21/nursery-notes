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
import java.util.List;
import java.util.UUID;
import net.nurserynotes.R;
import net.nurserynotes.model.ActivityContent;
import net.nurserynotes.model.entity.Activity;
import net.nurserynotes.viewModel.MainViewModel;

public class ActivityEditFragment extends DialogFragment {

  private static final String ID_KEY = "id";

  private long id;
  private View root;
  private MainViewModel viewModel;
  private EditText activityText;
  private AutoCompleteTextView activityName;
  private List<ActivityContent> activityContents;
  private Activity activity;

  public static ActivityEditFragment newInstance(UUID id) {
    ActivityEditFragment fragment = new ActivityEditFragment();
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
    root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_activity_edit, null, false);
    activityText = root.findViewById(R.id.activity_details_text);
    activityName = root.findViewById(R.id.activity_name);
    activityText.setText("");
    activityName.setText("");
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
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    viewModel.getActivityContents().observe(getViewLifecycleOwner(), (activityContents) -> {
      this.activityContents = activityContents;
      ArrayAdapter<ActivityContent> adapter =
          new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, activityContents);
      activityName.setAdapter(adapter);
    });
    if (id != Long.parseLong(null)) {
      viewModel.getActivity().observe(getViewLifecycleOwner(), (activity) -> {
        if (activity != null) {
          this.activity = activity;
          activityText.setText(activity.getText());
        }
      });
      viewModel.setActivityId(id);
    } else {
      activity = new Activity();
    }
  }

  private void save() {
    activity.setText(activityText.getText().toString().trim());
    ActivityContent activityContent = null;
    String name = activityName.getText().toString().trim();
    viewModel.save(activity);
  }
}
