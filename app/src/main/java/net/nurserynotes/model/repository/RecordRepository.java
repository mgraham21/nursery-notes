package net.nurserynotes.model.repository;

import android.app.Application;
import android.content.Context;
import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import net.nurserynotes.model.dao.RecordDao;
import net.nurserynotes.model.entity.Record;
import net.nurserynotes.model.pojo.RecordWithDetails;
import net.nurserynotes.service.NurseryNotesDatabase;

public class RecordRepository {

  private final NurseryNotesDatabase database;

  private final Context context;
  private final RecordDao dao;

  public RecordRepository(Context context) {
    this.context = context;
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

  public Completable remove(Record record) {
    return Completable.fromSingle(
        dao.delete(record)
          .subscribeOn(Schedulers.io())
    );
  }

  public LiveData<List<Record>> getAll() {
    return dao.select();
  }

  public LiveData<List<Record>> getAllForChild(long childId) {
    return dao.selectForChild(childId);
  }

  public LiveData<List<Record>> getAllForActivity(long activityId) {
    return dao.selectForActivity(activityId);
  }

  public LiveData<Record> get(long id) {
    return dao.select(id);
  }

  public LiveData<List<RecordWithDetails>> getAllWithDetails() {
    return dao.selectWithDetails();
  }

  public LiveData<List<RecordWithDetails>> getAllForChildWithDetails(long childId) {
    return dao.selectForChildWithDetails(childId);
  }

  public LiveData<List<RecordWithDetails>> getAllForActivityWithDetails(long activityId) {
    return dao.selectForActivityWithDetails(activityId);
  }

  public LiveData<RecordWithDetails> getWithDetails(long id) {
    return dao.selectWithDetails(id);
  }

}
