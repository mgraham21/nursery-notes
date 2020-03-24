package net.nurserynotes.controller;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.UUID;
import net.nurserynotes.R;
import net.nurserynotes.view.ActivityRecyclerAdapter;
import net.nurserynotes.viewModel.MainViewModel;

public class RecentActivitiesFragment extends Fragment {

  private RecyclerView activitiesList;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_recent_activities, container, false);
    activitiesList = root.findViewById(R.id.activities_list);
    FloatingActionButton addActivity = root.findViewById(R.id.add_fab);
    addActivity.setOnClickListener((v) -> editActivity(null));
    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupViewModel();
  }

  private void setupViewModel() {
    @SuppressWarnings("ConstantConditions")
    MainViewModel viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    viewModel.getActivityContents().observe(getViewLifecycleOwner(), (activityContents) -> {
      ActivityRecyclerAdapter adapter = new ActivityRecyclerAdapter(getContext(),
          activityContents, (position, activityContent) -> editActivity(activityContent.getId()));
      activitiesList.setAdapter(adapter);
    });
  }

  private void editActivity(UUID activityId) {
    Log.d(getClass().getName(), String.valueOf(activityId));
    ActivityEditFragment fragment = ActivityEditFragment.newInstance(activityId);
    fragment.show(getParentFragmentManager(), fragment.getClass().getName());
  }

}
