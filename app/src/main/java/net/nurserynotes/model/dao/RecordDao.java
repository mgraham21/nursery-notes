package net.nurserynotes.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Single;
import java.util.Collection;
import java.util.List;
import net.nurserynotes.model.entity.Record;

@Dao
public interface RecordDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<Long> insert(Record record);

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<List<Long>> insert(Collection<Record> records);

  @Update
  Single<Integer> update(Record record);

  @Delete
  Single<Integer> delete(Record... records);

  @Query("SELECT * FROM Record ORDER BY child_id")
  LiveData<List<Record>> select();



}
