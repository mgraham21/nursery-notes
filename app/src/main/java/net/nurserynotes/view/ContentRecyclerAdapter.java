package net.nurserynotes.view;

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
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.nurserynotes.R;
import net.nurserynotes.model.entity.Activity;
import net.nurserynotes.model.Content;

public class ContentRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

  private final Context context;
  private final List<Content> contents;
  private final Map<Class<? extends Content>, Integer> layouts;
  private final Map<Integer, Class<? extends ViewHolder>> holders;

  public ContentRecyclerAdapter(Context context, List<Content> contents) {
    this.context = context;
    this.contents = contents;
    layouts = new HashMap<>();
    holders = new HashMap<>();
    layouts.put(Activity.class, R.layout.item_content_activity);
    holders.put(R.layout.item_content_activity, ActivityHolder.class);
  }

  @Override
  public int getItemViewType(int position) {
    return layouts.get(contents.get(position).getClass());
  }
  
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
    try {
      Class<? extends ViewHolder> holderClass = holders.get(viewType);
      Constructor<? extends ViewHolder> constructor = holderClass.getConstructor(View.class);
      View root = LayoutInflater.from(context).inflate(viewType, parent, false);
      return constructor.newInstance(root);
    } catch (NoSuchMethodException
        | IllegalAccessException
        | InstantiationException
        | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void onBindViewHolder (@NonNull ViewHolder holder, int position) {
    try {
      Class<? extends ViewHolder> holderClass = holder.getClass();
      Method binder = holderClass.getMethod("bind", int.class, Content.class);
      binder.invoke(holder, position, contents.get(position));
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int getItemCount() {
    return contents.size();
  }

  private static class ActivityHolder extends ViewHolder {

    private final TextView activityDetails;

    public ActivityHolder(@NonNull View itemView) {
      super(itemView);
      activityDetails = itemView.findViewById(R.id.activity_details);
    }

    public void bind(int position, Content activity) {
      activityDetails.setText(((Activity) activity).getText());
    }

  }

}
