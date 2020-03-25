package net.nurserynotes.model.repository;

import android.app.Application;
import android.content.Context;
import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import net.nurserynotes.model.dao.ActivityDao;
import net.nurserynotes.model.entity.Activity;
import net.nurserynotes.service.NurseryNotesDatabase;

public class ActivityRepository {

  private final NurseryNotesDatabase database;

  private final Context context;
  private final ActivityDao dao;

  public ActivityRepository(Context context) {
    this.context = context;
    database = NurseryNotesDatabase.getInstance();
    dao = database.getActivityDao();
  }

  public Completable save(Activity activity) {
    if (activity.getId() == 0) {
      return Completable.fromSingle(
          dao.insert(activity)
              .subscribeOn(Schedulers.io())
      );
    } else {
      return Completable.fromSingle(
          dao.update(activity)
              .subscribeOn(Schedulers.io())
      );
    }
  }

  public Completable remove(Activity activity) {
    return Completable.fromSingle(
        dao.delete(activity)
          .subscribeOn(Schedulers.io())
    );
  }

  public LiveData<Activity> get(long id) {
    return dao.select(id);
  }

  /*public Single<Activity> getDate (Date date) {
    ActivityDao dao = database.getActivityDao();
    return  dao.select(date);
  } */

  public LiveData<List<Activity>> getAll() {
    return dao.select();
  }

}
