package net.nurserynotes.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import net.nurserynotes.R;
import net.nurserynotes.model.entity.Activity;
import net.nurserynotes.model.entity.Child;
import net.nurserynotes.model.entity.Record;

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
    layouts.put(Activity.class, R.layout.item_content_activity);
    layouts.put(Child.class, R.layout.item_content_child);
    layouts.put(Record.class, R.layout.item_content_record);
    holders.put(R.layout.item_content_activity, ActivityHolder.class);
    holders.put(R.layout.item_content_child, ChildHolder.class);
    holders.put(R.layout.item_content_record, RecordHolder.class);
  }

  @Override
  public int getItemViewType(int position) {
    return layouts.get(contents.get(position).getClass());
  }

  @NonNull
  @Override
  public ContentHolder onCreateViewHolder(@NonNull ViewGroup, int viewType) {
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
  public void onBindHolder(@NonNull ContentHolder holder, int position) {
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

  private class



}
