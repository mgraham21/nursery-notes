package net.nurserynotes.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.nurserynotes.R;
import net.nurserynotes.model.Content;
import net.nurserynotes.model.entity.Activity;
import net.nurserynotes.model.entity.Child;
import net.nurserynotes.model.entity.Record;
import net.nurserynotes.view.ContentRecyclerAdapter.ContentHolder;

public class ContentRecyclerAdapter extends RecyclerView.Adapter<ContentHolder> {

  private final Context context;
  private final List<Content> contents;
  // Map of Content subclasses to Integer layout resource IDs.
  private final Map<Class<? extends Content>, Integer> layouts;
  // Map of Integer layout resource IDs to ContentHolder subclasses.
  private final Map<Integer, Class<? extends ContentHolder>> holders;

  @SuppressLint("UseSparseArrays")
  public ContentRecyclerAdapter(Context context, List<Content> contents) {
    this.context = context;
    this.contents = contents;
    layouts = new HashMap<>();
    holders = new HashMap<>();
    layouts.put(Activity.class, R.layout.item_record);
    layouts.put(Child.class, R.layout.item_content_child);
    layouts.put(Record.class, R.layout.item_content_record);
    holders.put(R.layout.item_record, ActivityHolder.class);
    holders.put(R.layout.item_content_child, ChildHolder.class);
    holders.put(R.layout.item_content_record, RecordHolder.class);
  }

  @Override
  public int getItemViewType(int position) {
    return layouts.get(contents.get(position).getClass());
  }

  @NonNull
  @Override
  public ContentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    try {
      Class<? extends ContentHolder> holderClass = holders.get(viewType);
      Constructor<? extends  ContentHolder> constructor =
          holderClass.getDeclaredConstructor(ContentRecyclerAdapter.class, View.class);
      View root = LayoutInflater.from(context).inflate(viewType, parent, false);
      return constructor.newInstance(this, root);
    } catch (NoSuchMethodException
        | IllegalAccessException
        | InstantiationException
        | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void onBindViewHolder(@NonNull ContentHolder holder, int position) {
    holder.bind(contents.get(position));
  }

  @Override
  public int getItemCount() {
    return contents.size();
  }

  abstract static class ContentHolder extends ViewHolder {

    public ContentHolder(@NonNull View itemView) {
      super(itemView);
    }

    public abstract void bind(Content content);

  }


  private class ActivityHolder extends ContentHolder {

    private final TextView activityText;

    public ActivityHolder(@NonNull View itemView) {
      super(itemView);
      activityText = itemView.findViewById(R.id.notes);
    }

    @Override
    public void bind(Content content) {
      Activity activity = (Activity) content;
      activityText.setText(context.getString(R.string.activity_format, activity.getText()));
    }

  }

  private class ChildHolder extends ContentHolder {

    private final TextView childName;

    public ChildHolder(@NonNull View itemView) {
      super(itemView);
      childName = itemView.findViewById(R.id.child_name);
    }

    @Override
    public void bind(Content content) {
      Child child = (Child) content;
      String name = child.getFirstName();
      childName.setText((name != null) ? name : context.getString(R.string.word_child));
    }

  }

  private class RecordHolder extends ContentHolder {

    private final TextView recordText;

    public RecordHolder(@NonNull View itemView) {
      super(itemView);
      recordText = itemView.findViewById(R.id.notes);
    }

    @Override
    public void bind(Content content) {
      Record record = (Record) content;
      recordText.setText(context.getString(R.string.activity_format, record.getNotes()));
    }

  }

}
