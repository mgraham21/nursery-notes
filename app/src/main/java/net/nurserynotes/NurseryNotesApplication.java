package net.nurserynotes;

import android.app.Application;
import com.facebook.stetho.Stetho;
import io.reactivex.schedulers.Schedulers;
import net.nurserynotes.model.repository.ActivityRepository;
import net.nurserynotes.model.repository.ChildRepository;
import net.nurserynotes.service.NurseryNotesDatabase;

public class NurseryNotesApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    NurseryNotesDatabase.setContext(this);
      NurseryNotesDatabase.getInstance().getActivityDao().delete()
        .subscribeOn(Schedulers.io())
        .subscribe();
  }

}
