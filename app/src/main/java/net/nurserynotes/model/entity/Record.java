package net.nurserynotes.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(
    foreignKeys = {
        @ForeignKey(
            entity = Child.class,
            parentColumns = "child_id",
            childColumns = "child_id",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Activity.class,
            parentColumns = "activity_id",
            childColumns = "activity_id",
            onDelete = ForeignKey.CASCADE
        )
    }
)
public class Record {

  @ColumnInfo(name = "record_id")
  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo(name = "child_id")
  private long childId;

  @ColumnInfo(name = "activity_id")
  private long activityId;

  @ColumnInfo(name = "start", index = true)
  private Date start;

  @ColumnInfo(name = "end", index = true)
  private Date end;

  @ColumnInfo(name = "notes", index = true)
  private String notes;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getChildId() {
    return childId;
  }

  public void setChildId(long childId) {
    this.childId = childId;
  }

  public long getActivityId() {
    return activityId;
  }

  public void setActivityId(long activityId) {
    this.activityId = activityId;
  }

  public Date getStart() {
    return start;
  }

  public void setStart(Date start) {
    this.start = start;
  }

  public Date getEnd() {
    return end;
  }

  public void setEnd(Date end) {
    this.end = end;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
}
