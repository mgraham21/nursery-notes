package net.nurserynotes.model.repository;

import android.app.Application;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import net.nurserynotes.service.NurseryNotesDatabase;

public class ChildRepository {

  private static final int NETWORK_THREAD_COUNT = 10;

  private final NurseryNotesDatabase database;

  private static Application context;

  private ChildRepository() {
    if (context == null) {
      throw new IllegalStateException();
    }
    database = NurseryNotesDatabase.getInstance();
  }

  public static void setContext(Application context) {
    ChildRepository.context = context;
  }

  public static ChildRepository getInstance() {
    return InstanceHolder.INSTANCE;
  }

  private static class InstanceHolder {
    private static final ChildRepository INSTANCE = new ChildRepository();
  }


}
