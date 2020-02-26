package net.nurserynotes.model.repository;

import android.app.Application;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import net.nurserynotes.service.NurseryNotesDatabase;

public class ActivityRepository {

  private static final int NETWORK_THREAD_COUNT = 10;

  private final NurseryNotesDatabase database;

  private static Application context;

  private ActivityRepository() {
    if (context == null) {
      throw new IllegalStateException();
    }
    database = NurseryNotesDatabase.getInstance();
    }

  public static void setContext(Application context) {
    ActivityRepository.context = context;
  }

  public static ActivityRepository getInstance() {
    return InstanceHolder.INSTANCE;
  }

private static class InstanceHolder {
    private static final ActivityRepository INSTANCE = new ActivityRepository();
}


}
