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
import net.nurserynotes.model.entity.Child;

@Dao
public interface ChildDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<Long> insert(Child child);

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<List> insert(Collection<Child> children);

  @Update
  Single<Integer> delete(Child child);

  @Delete
  Single<Integer> delete(Child... children);

  @Query("SELECT * FROM Child ORDER BY last_name, first_name")
  LiveData<List<Child>> select();

}
