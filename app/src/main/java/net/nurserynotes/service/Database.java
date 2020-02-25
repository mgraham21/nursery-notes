package net.nurserynotes.service;

import android.app.Application;
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

@androidx.room.Database(
    entities = {Activity.class, Child.class, Record.class},
    version = 1,
    exportSchema = true
)
@TypeConverters({Converters.class})
public abstract class Database extends RoomDatabase {

  private static final String DB_NAME = "db";

  private static Application context;

  public static void setContext(Application context) { Database.context = context; }

  public static Database getInstance() { return  InstanceHolder.INSTANCE; }

  public abstract ActivityDao getActivityDao();

  public abstract ChildDao getChildDao();

  public abstract RecordDao getRecordDao();

  private static class InstanceHolder {

    private static final Database INSTANCE = Room.databaseBuilder(
        context, Database.class, DB_NAME)
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
