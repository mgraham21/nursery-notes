package net.nurserynotes.model.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.PrimitiveIterator;
import net.nurserynotes.model.dao.RecordDao;
import net.nurserynotes.model.entity.Activity;
import net.nurserynotes.model.entity.Record;
import net.nurserynotes.service.NurseryNotesDatabase;

public class RecordRepository {

  private static final int NETWORK_THREAD_COUNT = 10;

  private final NurseryNotesDatabase database;

  private static Application context;
  private final RecordDao dao;

  public RecordRepository() {
    if (context == null) {
      throw new IllegalStateException();
    }
    database = NurseryNotesDatabase.getInstance();
    dao = database.getRecordDao();
  }

  public Completable save(Record record) {
    if (record.getId() == 0) {
      return Completable.fromSingle(
          dao.insert(record)
            .subscribeOn(Schedulers.io())
      );
    } else {
      return Completable.fromSingle(
          dao.update(record)
            .subscribeOn(Schedulers.io())
      );
    }
  }

  public LiveData<List<Record>> getAll() {
    return dao.select();
  }

  public Single<Record> get(long id) {
    return dao.select(id)
        .subscribeOn(Schedulers.io());
  }

  public static void setContext(Application context) {
    RecordRepository.context = context;
  }

  public static RecordRepository getInstance() {
    return InstanceHolder.INSTANCE;
  }

  private static class InstanceHolder {
    private static final RecordRepository INSTANCE = new RecordRepository();
  }

}
