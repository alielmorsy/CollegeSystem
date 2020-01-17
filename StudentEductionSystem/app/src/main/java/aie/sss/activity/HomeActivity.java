package aie.sss.activity;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import aie.sss.R;
import aie.sss.Util.Constants;
import aie.sss.database.subjects.ViewModel;
import aie.sss.user.SessionManager;

import static aie.sss.Util.UIUtil.setupDraw;


public class HomeActivity extends AppCompatActivity {
    private SessionManager manager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI();
        //startService(new Intent(this, NotificationService.class).setAction("start"));
        manager = new SessionManager(this);
    }

    private void setupUI() {
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_notifications).build();
        BadgeDrawable drawable = navView.getOrCreateBadge(R.id.navigation_notifications);
        drawable.setNumber(5);
        drawable.setBackgroundColor(getColor(R.color.colorAccent));

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE}, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ViewModel model = ViewModelProviders.of(this).get(ViewModel.class);
        model.getAll().observe(this, subjects -> {
            Constants.allSubjects = subjects;
            setupDraw(this, toolbar);
        });
    }


}
