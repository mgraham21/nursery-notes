package net.nurserynotes.service;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import java.util.Date;
import net.nurserynotes.model.entity.Activity;
import net.nurserynotes.model.entity.Child;
import net.nurserynotes.model.entity.Record;
import net.nurserynotes.service.NurseryNotesDatabase.Converters;

@Database(
    entities = {Activity.class, Child.class, Record.class},
    version = 1,
    exportSchema = true
)
@TypeConverters({Converters.class})
public abstract class NurseryNotesDatabase extends RoomDatabase {


  public static class Converters {

    @TypeConverter
    public static Long fromDate(Date date) {
      return (date != null) ? date.getTime() : null;
    }

    @TypeConverter
    public static Date fromLong(Long value) {
      return (value != null) ? new Date(value) : null;
    }

  }

}
