package net.nurserynotes.controller;


import android.os.Bundle;
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


public class ListOfActivitiesFragment extends Fragment {

  private MainViewModel viewModel;
  private RecyclerView activitiesList;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_list_of_activities, container, false);
    setupUI(root);
    return root;
  }

  @SuppressWarnings("ConstantConditions")
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupViewModel();
  }

  @SuppressWarnings("ConstantConditions")
  @Override
  public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v,
      @Nullable ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    ContextMenuRecyclerView.ContextMenuInfo info =
        (ContextMenuRecyclerView.ContextMenuInfo) menuInfo;
    getActivity().getMenuInflater().inflate(R.menu.record_context, menu);
    menu.findItem(R.id.edit_record).setOnMenuItemClickListener(item -> {
    RecordEditFragment.newInstance(
        getChildFragmentManager(), ((Record) info.getView().getTag()).getId());
     return true;
    });
    menu.findItem(R.id.delete_record).setOnMenuItemClickListener(item -> {
      viewModel.remove((Record) info.getView().getTag());
    return true;
    });
  }

  private void setupUI(View root) {
    activitiesList = root.findViewById(R.id.activity_list);
    registerForContextMenu(activitiesList);
    FloatingActionButton addActivity = root.findViewById(R.id.add_activity);
    addActivity.setOnClickListener((v) ->
        RecordEditFragment.newInstance(getChildFragmentManager(), 0));
  }

  @SuppressWarnings("ConstantConditions")
  private void setupViewModel() {
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    viewModel.getRecords().observe(getViewLifecycleOwner(), (records) -> {
      RecordRecyclerAdapter adapter =
          new RecordRecyclerAdapter(getContext(), records, (position, record) ->
              RecordEditFragment.newInstance(getChildFragmentManager(), record.getId()));
      activitiesList.setAdapter(adapter);
    });
  }

  }
