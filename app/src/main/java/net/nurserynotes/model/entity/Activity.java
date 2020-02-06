package net.nurserynotes.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Activity {

  @ColumnInfo(name = "activity_id")
  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo(name = "name", collate = ColumnInfo.NOCASE)
  private String name;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
