package net.nurserynotes.viewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import io.reactivex.disposables.CompositeDisposable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.nurserynotes.model.ActivityContent;
import net.nurserynotes.model.entity.Activity;
import net.nurserynotes.model.entity.Child;
import net.nurserynotes.model.entity.Record;
import net.nurserynotes.model.repository.ActivityRepository;
import net.nurserynotes.model.repository.ChildRepository;
import net.nurserynotes.model.repository.RecordRepository;

public class MainViewModel extends AndroidViewModel implements LifecycleObserver {

  private ActivityRepository activityRepository;
  private ChildRepository childRepository;
  private RecordRepository recordRepository;
  private MutableLiveData<Activity> activity;
  private MutableLiveData<Record> record;
  private MutableLiveData<Child> child;
  private MutableLiveData<List<ActivityContent>> contents;
  private MutableLiveData<Throwable> throwable;
  private final MutableLiveData<Set<String>> permissions;
  private final CompositeDisposable pending;

  public MainViewModel(@NonNull Application application) {
    super (application);
    activityRepository = new ActivityRepository();
    recordRepository = new RecordRepository();
    childRepository = new ChildRepository();
    activity = new MutableLiveData<>();
    record = new MutableLiveData<>();
    child = new MutableLiveData<>();
    permissions = new MutableLiveData<>(new HashSet<>());
    contents = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
  }

  public LiveData<Activity> getActivity() {
    return activity;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public LiveData<List<Activity>> getAll() {
    throwable.setValue(null);
    return activityRepository.getAll();
  }

  public LiveData<List<ActivityContent>> getActivityContents() {
    return contents;
  }

  public void grantPermission(String permission) {
    Set<String> permissions = this.permissions.getValue();
    if (permissions.add(permission)) {
      this.permissions.setValue(permissions);
    }
  }

  public void revokePermission(String permission) {
    Set<String> permissions = this.permissions.getValue();
    if (permissions.remove(permission)) {
      this.permissions.setValue(permissions);
    }
  }

 /* public void setActivityDate (Date date) {
    throwable.setValue(null);
    pending.add(
        ActivityRepository.get(date)
            .subscribe(
                activity::postValue,
                throwable::postValue
            )
    );
  } */

  public void setActivityId(long id) {
    throwable.setValue(null);
    activityRepository.getId(id)
        .subscribe(
            activity::postValue,
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
 /* public void refreshContents() {
    GoogleSignInRepository.getInstance().refresh()
        .addOnSuccessListener((account) -> {
          pending.add(
              activityRepository.getAllContent(account.getIdToken())
                  .subscribe(
                      contents::postValue,
                      throwable::postValue
                  )
          );
        })
        .addOnFailureListener(throwable::postValue);
  }
*/

  @SuppressWarnings("unused")
  @OnLifecycleEvent(Event.ON_STOP)
  private void disposePending() {
    pending.clear();
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
