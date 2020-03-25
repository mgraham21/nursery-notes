package net.nurserynotes.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.util.Date;
import net.nurserynotes.model.Content;

@Entity(
    indices = {
        @Index(value = {"last_name", "first_name"}, unique = true)
    }
)
public class Child implements Content {

  @ColumnInfo(name = "child_id")
  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo(name = "last_name", collate = ColumnInfo.NOCASE)
  private String lastName;

  @ColumnInfo(name = "first_name", collate = ColumnInfo.NOCASE)
  private String firstName;

  @ColumnInfo(name = "birth_date", index = true)
  private Date birthDate;

  @NonNull
  @Override
  public String toString() {
    return firstName + " " + lastName;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }
}
