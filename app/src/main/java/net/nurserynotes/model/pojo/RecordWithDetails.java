package net.nurserynotes.model.pojo;

import androidx.room.Entity;
import androidx.room.Relation;
import net.nurserynotes.model.entity.Activity;
import net.nurserynotes.model.entity.Child;
import net.nurserynotes.model.entity.Record;

public class RecordWithDetails extends Record {

  @Relation(entity = Child.class, parentColumn = "child_id", entityColumn = "child_id")
  private Child child;

  @Relation(entity = Activity.class, parentColumn = "activity_id", entityColumn = "activity_id")
  private Activity activity;

  public Child getChild() {
    return child;
  }

  public void setChild(Child child) {
    this.child = child;
  }

  public Activity getActivity() {
    return activity;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }
}
