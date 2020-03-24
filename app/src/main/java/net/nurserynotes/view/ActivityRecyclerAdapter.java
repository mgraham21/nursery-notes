package net.nurserynotes.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import net.nurserynotes.R;
import net.nurserynotes.model.ActivityContent;

public class ActivityRecyclerAdapter extends RecyclerView.Adapter<Holder> {

  private final Context context;
  private final List<ActivityContent> activityContent;
  private final OnActivityClickListener listener;

  public ActivityRecyclerAdapter(Context context, List<ActivityContent> activityContent,
      OnActivityClickListener listener) {
    this.context = context;
    this.activityContent = activityContent;
    this.listener;
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
  View root = LayoutInflater.from(context).inflate(R.layout.item_content_activity, parent, false);
  return new Holder(root);
  }

  @Override
  public void onBindViewHolder (@NonNull Holder holder, int position) {
    holder.bind(position, activityContent.get(position));
  }

  @Override
  public int getItemCount() {
    return activityContent.size();
  }

  class Holder extends RecyclerView.ViewHolder {

    private final View clickView;
    private final TextView activityDetails;

    public Holder(View root) {
      super(root);
      clickView = root.findViewById(R.id.click_view);
      activityDetails = root.findViewById(R.id.activity_details);
    }

    public void bind(int position, ActivityContent activityContent) {
      activityDetails.setText(context.getString(R.string.activity_format, activityDetails.getText()));
      clickView.setOnClickListener((v) ->
          listener.onActivityClick(getAdapterPosition(), activityContent));
    }

  }

  @FunctionalInterface
  public interface OnActivityClickListener {

    void onActivityClick(int position, ActivityContent activityContent);

  }

}
