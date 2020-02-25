package net.nurserynotes.service;

import android.app.Application;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import java.util.Date;
import net.nurserynotes.model.dao.ActivityDao;
import net.nurserynotes.model.dao.ChildDao;
import net.nurserynotes.model.dao.RecordDao;
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

  private static final String DB_NAME = "db";

  private static Application context;

  public static void setContext(Application context) { NurseryNotesDatabase.context = context; }

  public static NurseryNotesDatabase getInstance() { return  InstanceHolder.INSTANCE; }

  public abstract ActivityDao getActivityDao();

  public abstract ChildDao getChildDao();

  public abstract RecordDao getRecordDao();

  private static class InstanceHolder {

    private static final NurseryNotesDatabase INSTANCE = Room.databaseBuilder(
        context, NurseryNotesDatabase.class, DB_NAME)
        .build();
  }

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
