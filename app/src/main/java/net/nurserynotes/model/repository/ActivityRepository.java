package net.nurserynotes.model.repository;

import android.app.Application;
import android.provider.ContactsContract.CommonDataKinds.Note;
import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import net.nurserynotes.model.dao.ActivityDao;
import net.nurserynotes.model.entity.Activity;
import net.nurserynotes.service.NurseryNotesDatabase;

public class ActivityRepository {

  private static final int NETWORK_THREAD_COUNT = 10;

  private final NurseryNotesDatabase database;

  private static Application context;
  private final ActivityDao dao;

  public ActivityRepository() {
    if (context == null) {
      throw new IllegalStateException();
    }
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

  public LiveData<List<Activity>> getAll() {
    return dao.select();
  }

  public Single<Activity> get(long id) {
    return dao.select(id)
        .subscribeOn(Schedulers.io());
  }

  public static void setContext(Application context) {
    ActivityRepository.context = context;
  }

  public static ActivityRepository getInstance() {
    return InstanceHolder.INSTANCE;
  }

  private static class InstanceHolder {
    private static final ActivityRepository INSTANCE = new ActivityRepository();
  }

}
