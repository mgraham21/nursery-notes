package net.nurserynotes.controller;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import net.nurserynotes.R;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link DateTimePickerFragment.OnFragmentInteractionListener} interface to handle interaction
 * events. Use the {@link DateTimePickerFragment#newInstance} factory method to create an instance
 * of this fragment.
 */
public class DateTimePickerFragment extends Fragment {

  private static final String CALENDAR_KEY = "calendar";
  private static final String MODE_KEY = "mode";

  private Mode mode;
  private Calendar calendar;
  private OnChangeListener listener;

  public static DateTimePickerFragment createInstance(Mode mode, Calendar calendar) {
    DateTimePickerFragment fragment = new DateTimePickerFragment();
    Bundle args = new Bundle();
    args.putSerializable(MODE_KEY, mode);
    args.putSerializable(CALENDAR_KEY, calendar);
    fragment.setArguments(args);
    return fragment;
  }

  @NonNull
  @Override
  public void onCreateDialog(@Nullable Bundle savedInstanceState) {
    Dialog dialog;
    readArguements();
    setupListener();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_date_time_picker, container, false);
  }

  // TODO: Rename method, update argument and hook method into UI event
  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener) {
      mListener = (OnFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  /**
   * This interface must be implemented by activities that contain this fragment to allow an
   * interaction in this fragment to be communicated to the activity and potentially other fragments
   * contained in that activity.
   * <p>
   * See the Android Training lesson <a href= "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnFragmentInteractionListener {

    // TODO: Update argument type and name
    void onFragmentInteraction(Uri uri);
  }
}
