package net.nurserynotes.viewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.Transformations;
import io.reactivex.disposables.CompositeDisposable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.nurserynotes.model.ActivityContent;
import net.nurserynotes.model.entity.Activity;
import net.nurserynotes.model.entity.Child;
import net.nurserynotes.model.entity.Record;
import net.nurserynotes.model.pojo.RecordWithDetails;
import net.nurserynotes.model.repository.ActivityRepository;
import net.nurserynotes.model.repository.ChildRepository;
import net.nurserynotes.model.repository.RecordRepository;

public class MainViewModel extends AndroidViewModel implements LifecycleObserver {

  private ActivityRepository activityRepository;
  private ChildRepository childRepository;
  private RecordRepository recordRepository;
  private LiveData<Activity> activity;
  private MutableLiveData<Long> activityId;
  private LiveData<List<RecordWithDetails>> recordsForActivity;
  private LiveData<RecordWithDetails> record;
  private MutableLiveData<Long> recordId;
  private LiveData<Child> child;
  private MutableLiveData<Long> childId;
  private LiveData<List<RecordWithDetails>> recordsForChild;
  private MutableLiveData<Throwable> throwable;
  private final MutableLiveData<Set<String>> permissions;
  private final CompositeDisposable pending;

  public MainViewModel(@NonNull Application application) {
    super (application);
    activityRepository = new ActivityRepository(application);
    recordRepository = new RecordRepository(application);
    childRepository = new ChildRepository(application);
    activityId = new MutableLiveData<>();
    activity = Transformations.switchMap(activityId, (id) -> activityRepository.get(id));
    recordsForActivity = Transformations.switchMap(activityId, (id) -> recordRepository.getAllForActivityWithDetails(id));
    recordId = new MutableLiveData<>();
    record = Transformations.switchMap(recordId, (id) -> recordRepository.getWithDetails(id));
    childId = new MutableLiveData<>();
    child = Transformations.switchMap(childId, (id) -> childRepository.get(id));
    recordsForChild = Transformations.switchMap(childId, (id) -> recordRepository.getAllForChildWithDetails(id));
    permissions = new MutableLiveData<>(new HashSet<>());
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
  }

  public LiveData<List<Activity>> getActivities() {
    return activityRepository.getAll();
  }

  public LiveData<List<Child>> getChildren() {
    return childRepository.getAll();
  }

  public LiveData<List<RecordWithDetails>> getRecords() {
    return recordRepository.getAllWithDetails();
  }

  public LiveData<List<RecordWithDetails>> getRecordsForActivity() {
    return recordsForActivity;
  }

  public LiveData<List<RecordWithDetails>> getRecordsForChild() {
    return recordsForChild;
  }

  public LiveData<Activity> getActivity() {
    return activity;
  }

  public LiveData<RecordWithDetails> getRecord() {
    return record;
  }

  public LiveData<Child> getChild() {
    return child;
  }


  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public LiveData<List<Activity>> getAll() {
    throwable.setValue(null);
    return activityRepository.getAll();
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

  public void setActivityId(long id) {
    this.activityId.setValue(id);
  }

  public void setChildId(long id) {
    this.childId.setValue(id);
  }

  public void setRecordId(long id) {
    this.recordId.setValue(id);
  }

  public void save(Activity activity) {
    throwable.setValue(null);
    activityRepository.save(activity)
        .subscribe(
            () -> {},
            throwable::postValue
        );
  }

  public void save(Child child) {
    throwable.setValue(null);
    childRepository.save(child)
        .subscribe(
            () -> {},
            throwable::postValue
        );
  }

  public void save(Record record) {
    throwable.setValue(null);
    recordRepository.save(record)
        .subscribe(
            () -> {},
            throwable::postValue
        );
  }

  @SuppressWarnings("unused")
  @OnLifecycleEvent(Event.ON_STOP)
  private void disposePending() {
    pending.clear();
  }

  public void remove(Activity activity) {
    throwable.setValue(null);
    activityRepository.remove(activity)
        .subscribe(
            () -> {},
            throwable::postValue
        );
  }

  public void remove(Child child) {
    throwable.setValue(null);
    childRepository.remove(child)
        .subscribe(
            () -> {},
            throwable::postValue
        );
  }

  public void remove(Record record) {
    throwable.setValue(null);
    recordRepository.remove(record)
        .subscribe(
            () -> {},
            throwable::postValue
        );
  }

}
