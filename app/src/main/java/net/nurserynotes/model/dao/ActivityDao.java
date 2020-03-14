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
import net.nurserynotes.model.entity.Activity;

@Dao
public interface ActivityDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<Long> insert(Activity activity);

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<List<Long>> insert(Collection<Activity> activities);

  @Update
  Single<Integer> update(Activity activity);

  @Delete
  Single<Integer> delete(Activity... activities);

  @Query("SELECT * FROM Activity ORDER BY name")
  LiveData<List<Activity>> select();

  @Query("SELECT * FROM Activity WHERE activity_id = :id")
  Single<Activity> select(long id);
}
