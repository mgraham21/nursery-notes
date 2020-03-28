package net.nurserynotes.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import net.nurserynotes.R;
import net.nurserynotes.model.entity.Record;
import net.nurserynotes.view.ContextMenuRecyclerView;
import net.nurserynotes.view.RecordRecyclerAdapter;
import net.nurserynotes.viewModel.MainViewModel;

public class RecentActivitiesFragment extends Fragment {

  private RecyclerView activitiesList;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_recent_activities, container, false);
    activitiesList = root.findViewById(R.id.activities_list);
    FloatingActionButton addActivity = root.findViewById(R.id.add_fab);
    addActivity.setOnClickListener((v) ->
        RecordEditFragment.newInstance(getChildFragmentManager(), 0));
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
    viewModel.getRecords().observe(getViewLifecycleOwner(), (records) -> {
      RecordRecyclerAdapter adapter =
          new RecordRecyclerAdapter(getContext(), records, (position, record) ->
              RecordEditFragment.newInstance(getChildFragmentManager(), record.getId()));
      activitiesList.setAdapter(adapter);
    });
  }


}
