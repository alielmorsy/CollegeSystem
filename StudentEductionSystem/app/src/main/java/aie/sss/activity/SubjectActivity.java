package aie.sss.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import aie.sss.R;
import aie.sss.Util.UIUtil;
import aie.sss.activity.ui.Subject.MainFragment;

public class SubjectActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String subjectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        toolbar = findViewById(R.id.toolbar);

        Intent intent = getIntent();
         subjectName = intent.getStringExtra("subject");


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(subjectName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        UIUtil.setupDraw(this,toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.SubjectFrame, new MainFragment(subjectName,toolbar));
        transaction.commit();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }

    @Override
    public void onBackPressed() {

        FragmentManager manager = getSupportFragmentManager();
        int count = manager.getBackStackEntryCount();
        if (count > 1) {
            manager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
