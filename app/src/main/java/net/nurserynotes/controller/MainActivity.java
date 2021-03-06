package net.nurserynotes.controller;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import net.nurserynotes.R;
import net.nurserynotes.viewModel.MainViewModel;
import net.nurserynotes.service.GoogleSignInRepository;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements PermissionsFragment.OnAcknowledgeListener {

  private static final int EXTERNAL_STORAGE_REQUEST_CODE = 1000;

  private MainViewModel viewModel;
  private NavController navController;
  private BottomNavigationView navigator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setupNavigation();
    setupViewModel();
    checkPermissions();
    BottomNavigationView navView = findViewById(R.id.nav_view);
    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
        R.id.navigation_recent, R.id.navigation_list_of_children, R.id.navigation_list_of_activities)
        .build();
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    NavigationUI.setupWithNavController(navView, navController);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.main_options, menu);
    return true;
  }

  @SuppressWarnings("SwitchStatementWithTooFewBranches")
  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled = true;
    switch (item.getItemId()) {
      case R.id.sign_out:
        GoogleSignInRepository.getInstance().signOut()
            .addOnCompleteListener((task) -> {
              Intent intent = new Intent(this, LoginActivity.class);
              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
              startActivity(intent);
            });
        break;
      case R.id.settings:
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        break;
      default:
        handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode == EXTERNAL_STORAGE_REQUEST_CODE) {
      for (int i = 0; i < permissions.length; i++) {
        String permission = permissions[i];
        int result = grantResults[i];
        if (result == PackageManager.PERMISSION_GRANTED) {
          viewModel.grantPermission(permission);
        } else {
          viewModel.revokePermission(permission);
        }
      }
    } else {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  @Override
  public void onAcknowledge(String[] permissionsToRequest) {
    ActivityCompat.requestPermissions(this, permissionsToRequest, EXTERNAL_STORAGE_REQUEST_CODE);
  }

  public void showToast(String message) {
    Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
    toast.setGravity(Gravity.BOTTOM, 0,
        getResources().getDimensionPixelOffset(R.dimen.toast_vertical_margin));
    toast.show();
  }

  private void setupViewModel() {
    viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    viewModel.getThrowable().observe(this, (throwable) -> {
      if (throwable != null) {
        showToast("Unable to retrieve Activity. ({throwable.getMessage()}) ");
      }
    });
    getLifecycle().addObserver(viewModel);
  }

  private void setupNavigation() {
   AppBarConfiguration appBarConfiguration =
        new AppBarConfiguration.Builder(
            R.id.navigation_recent, R.id.navigation_list_of_children, R.id.navigation_list_of_activities)
            .build();
    navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    navigator = findViewById(R.id.nav_view);
    NavigationUI.setupWithNavController(navigator, navController);
   }

  private void checkPermissions() {
    String[] permissions = null;
    try {
      PackageInfo info = getPackageManager().getPackageInfo(getPackageName(),
          PackageManager.GET_META_DATA | PackageManager.GET_PERMISSIONS);
      permissions = info.requestedPermissions;
    } catch (NameNotFoundException e) {
      throw new RuntimeException(e);
    }
    List<String> permissionsToRequest = new LinkedList<>();
    List<String> permissionsToExplain = new LinkedList<>();
    for (String permission : permissions) {
      if (ContextCompat.checkSelfPermission(this, permission)
          != PackageManager.PERMISSION_GRANTED) {
        permissionsToRequest.add(permission);
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
          permissionsToExplain.add(permission);
        }
      } else {
        viewModel.grantPermission(permission);
      }
    }
    if (!permissionsToExplain.isEmpty()) {
      explainPermissions(
          permissionsToExplain.toArray(new String[0]), permissionsToRequest.toArray(new String[0]));
    } else if (!permissionsToRequest.isEmpty()) {
      onAcknowledge(permissionsToRequest.toArray(new String[0]));
    }
  }

  private void explainPermissions(String[] permissionsToExplain, String[] permissionsToRequest) {
    PermissionsFragment fragment =
        PermissionsFragment.createInstance(permissionsToExplain, permissionsToRequest);
    fragment.show(getSupportFragmentManager(), fragment.getClass().getName());
  }



}