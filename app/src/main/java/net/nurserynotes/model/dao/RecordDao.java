package net.nurserynotes.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;
import net.nurserynotes.model.entity.Record;
import net.nurserynotes.model.pojo.RecordWithDetails;

@Dao
public interface RecordDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<Long> insert(Record record);

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<List<Long>> insert(Collection<Record> records);

  @Update
  Single<Integer> update(Record... records);

  @Delete
  Single<Integer> delete(Record... records);

  @Query("SELECT * FROM Record ORDER BY child_id")
  LiveData<List<Record>> select();

  @Query("SELECT * FROM Record WHERE record_id = :id")
  LiveData<Record> select(long id);

  @Query("SELECT * FROM Record WHERE child_id = :id ORDER BY start DESC")
  LiveData<List<Record>> selectForChild(long id);

  @Query("SELECT * FROM Record WHERE activity_id = :id ORDER BY start DESC")
  LiveData<List<Record>> selectForActivity(long id);

  @Transaction
  @Query("SELECT * FROM Record ORDER BY child_id")
  LiveData<List<RecordWithDetails>> selectWithDetails();

  @Transaction
  @Query("SELECT * FROM Record WHERE record_id = :id")
  LiveData<RecordWithDetails> selectWithDetails(long id);

  @Transaction
  @Query("SELECT * FROM Record WHERE child_id = :id ORDER BY start DESC")
  LiveData<List<RecordWithDetails>> selectForChildWithDetails(long id);

  @Transaction
  @Query("SELECT * FROM Record WHERE activity_id = :id ORDER BY start DESC")
  LiveData<List<RecordWithDetails>> selectForActivityWithDetails(long id);

}
