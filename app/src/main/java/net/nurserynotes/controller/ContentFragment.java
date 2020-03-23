package net.nurserynotes.controller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import net.nurserynotes.R;
import com.google.android.gms.plus.PlusOneButton;
import net.nurserynotes.view.ContentRecyclerAdapter;
import net.nurserynotes.viewModel.MainViewModel;

public class ContentFragment extends Fragment {

  private RecyclerView contentList;
  private MainViewModel viewModel;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_content, container, false);
    contentList = root.findViewById(R.id.content_list);
    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    viewModel.getContents().observe(getViewLifecycleOwner(), (contents) -> {
      ContentRecyclerAdapter adapter = new ContentRecyclerAdapter(getContext(), contents);
      contentList.setAdapter(adapter);
    });
    /*viewModel.refreshContents();*/
  }


}
