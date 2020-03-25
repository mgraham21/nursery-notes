package net.nurserynotes.model.repository;

import android.app.Application;
import android.content.Context;
import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import net.nurserynotes.model.dao.ChildDao;
import net.nurserynotes.model.entity.Child;
import net.nurserynotes.service.NurseryNotesDatabase;

public class ChildRepository {

  private static final int NETWORK_THREAD_COUNT = 10;

  private final NurseryNotesDatabase database;

  private final Context context;
  private final ChildDao dao;

  public ChildRepository(Context context) {
    this.context = context;
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

  public Completable remove(Child child) {
    return Completable.fromSingle(
        dao.delete(child)
          .subscribeOn(Schedulers.io())
    );
  }

  public LiveData<List<Child>> getAll() {
    return dao.select();
  }

  public LiveData<Child> get(long id) {
    return dao.select(id);
  }

}
