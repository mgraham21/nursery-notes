package net.nurserynotes.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DateFormat;
import java.util.List;
import net.nurserynotes.R;
import net.nurserynotes.model.ActivityContent;
import net.nurserynotes.model.entity.Record;
import net.nurserynotes.model.pojo.RecordWithDetails;
import net.nurserynotes.view.RecordRecyclerAdapter.Holder;

public class RecordRecyclerAdapter extends RecyclerView.Adapter<Holder> {

  private final Context context;
  private final List<RecordWithDetails> records;
  private final OnActivityClickListener listener;
  private final DateFormat dateFormat;
  private final DateFormat timeFormat;

  public RecordRecyclerAdapter(Context context, List<RecordWithDetails> records,
      OnActivityClickListener listener) {
    this.context = context;
    this.records = records;
    this.listener = listener;
    dateFormat = android.text.format.DateFormat.getDateFormat(context);
    timeFormat = android.text.format.DateFormat.getTimeFormat(context);
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
  View root = LayoutInflater.from(context).inflate(R.layout.item_record, parent, false);
  return new Holder(root);
  }

  @Override
  public void onBindViewHolder (@NonNull Holder holder, int position) {
    holder.bind(position, records.get(position));
  }

  @Override
  public int getItemCount() {
    return records.size();
  }

  class Holder extends RecyclerView.ViewHolder {

    private final View clickView;
    private final TextView activity;
    private final TextView timeSpan;
    private final TextView notes;
    private final TextView child;

    public Holder(View root) {
      super(root);
      clickView = root.findViewById(R.id.click_view);
      activity = root.findViewById(R.id.activity);
      timeSpan = root.findViewById(R.id.time_span);
      notes = root.findViewById(R.id.notes);
      child = root.findViewById(R.id.child);
    }

    public void bind(int position, RecordWithDetails record) {
      activity.setText(record.getActivity().getName());
      child.setText(record.getChild().getFirstName());
      timeSpan.setText(context.getString(R.string.record_span_format,
          dateFormat.format(record.getStart()), timeFormat.format(record.getStart()),
          timeFormat.format(record.getEnd())));
      notes.setText(record.getNotes());
      clickView.setOnClickListener((v) ->
          listener.onActivityClick(getAdapterPosition(), record));
    }

  }

  @FunctionalInterface
  public interface OnActivityClickListener {

    void onActivityClick(int position, Record record);

  }

}
