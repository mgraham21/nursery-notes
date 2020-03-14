package net.nurserynotes.viewModel;

import android.app.Application;
import android.provider.ContactsContract.CommonDataKinds.Note;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import net.nurserynotes.model.entity.Activity;
import net.nurserynotes.model.entity.Child;
import net.nurserynotes.model.entity.Record;
import net.nurserynotes.model.repository.ActivityRepository;
import net.nurserynotes.model.repository.ChildRepository;
import net.nurserynotes.model.repository.RecordRepository;

public class MainViewModel extends AndroidViewModel {

  private ActivityRepository activityRepository;
  private ChildRepository childRepository;
  private RecordRepository recordRepository;
  private MutableLiveData<Activity> activityMutableLiveData;
  private MutableLiveData<Record> recordMutableLiveData;
  private MutableLiveData<Child> childMutableLiveData;
  private MutableLiveData<Throwable> throwable;

  public MainViewModel(@NonNull Application application) {
    super (application);
    activityRepository = new ActivityRepository();
    recordRepository = new RecordRepository();
    childRepository = new ChildRepository();
    activityMutableLiveData = new MutableLiveData<>();
    recordMutableLiveData = new MutableLiveData<>();
    childMutableLiveData = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
  }

  public LiveData<List<Activity>> getAll() {
    throwable.setValue(null);
    return activityRepository.getAll();
  }

  public LiveData<Activity> getActivityMutableLiveData() {
    return activityMutableLiveData;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public void setActivityId(long id) {
    throwable.setValue(null);
    activityRepository.get(id)
        .subscribe(
            activityMutableLiveData::postValue,
            throwable::postValue
        );
  }

  public void save(Activity activity) {
    throwable.setValue(null);
    activityRepository.save(activity)
        .subscribe(
            () -> {},
            throwable::postValue
        );
  }

/*  public void remove(Activity activity) {
    throwable.setValue(null);
    activityRepository.remove(activity)
        .subscribe(
            () -> {},
            throwable::postValue
        );
  }
*/

}
