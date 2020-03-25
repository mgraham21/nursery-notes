package net.nurserynotes.service;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
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
        .addCallback(new Callback())
        .build();
  }

  private static class Callback extends RoomDatabase.Callback {

    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
      List<Activity> activities = new LinkedList<>();
      Activity activity = new Activity();
      activity.setName("Diapers");
      activity.setText("");
      activities.add(activity);
      activity = new Activity();
      activity.setName("Sleep");
      activity.setText("");
      activities.add(activity);
      NurseryNotesDatabase.getInstance().getActivityDao().insert(activities)
          .subscribeOn(Schedulers.io())
          .subscribe();
      List<Child> children = new LinkedList<>();
      Child child = new Child();
      child.setFirstName("Cedric");
      child.setLastName("Serna");
      children.add(child);
      NurseryNotesDatabase.getInstance().getChildDao().insert(children)
          .subscribeOn(Schedulers.io())
          .subscribe();
    }

    @Override
    public void onOpen(@NonNull SupportSQLiteDatabase db) {
      super.onOpen(db);
    }
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
