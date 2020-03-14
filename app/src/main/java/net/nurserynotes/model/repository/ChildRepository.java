package net.nurserynotes.model.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import net.nurserynotes.model.dao.ChildDao;
import net.nurserynotes.model.entity.Child;
import net.nurserynotes.service.NurseryNotesDatabase;

public class ChildRepository {

  private static final int NETWORK_THREAD_COUNT = 10;

  private final NurseryNotesDatabase database;

  private static Application context;
  private static ChildDao dao;

  public ChildRepository() {
    if (context == null) {
      throw new IllegalStateException();
    }
    database = NurseryNotesDatabase.getInstance();
    dao = database.getChildDao();
  }

  public Completable save(Child child) {
    if (child.getId() == 0) {
      return Completable.fromSingle(
          dao.insert(child)
            .subscribeOn(Schedulers.io())
      );
    } else {
      return Completable.fromSingle(
          dao.update(child)
            .subscribeOn(Schedulers.io())
      );
    }
  }

  public LiveData<List<Child>> getAll() {
    return dao.select();
  }

  public Single<Child> get(long id) {
    return dao.select(id)
        .subscribeOn(Schedulers.io());
  }

  public static void setContext(Application context) {
    ChildRepository.context = context;
  }

  public static ChildRepository getInstance() {
    return InstanceHolder.INSTANCE;
  }

  private static class InstanceHolder {
    private static final ChildRepository INSTANCE = new ChildRepository();
  }


}
