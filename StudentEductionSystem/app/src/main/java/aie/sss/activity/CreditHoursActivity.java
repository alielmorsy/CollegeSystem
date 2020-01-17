package aie.sss.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;

import java.util.ArrayList;

import aie.sss.R;
import aie.sss.activity.ui.CreditHours.DialogAdapter;
import aie.sss.activity.ui.CreditHours.HoursListAdapter;
import aie.sss.database.subjects.ViewModel;
import aie.sss.models.Subject;
import aie.sss.observers.CreditHoursModel;
import aie.sss.server.CreditHoursTask;
import aie.sss.user.SessionManager;

import static aie.sss.Util.UIUtil.setupDraw;

public class CreditHoursActivity extends AppCompatActivity {

    private HoursListAdapter hoursListAdapter;
    private AlertDialog dialog;
    private DialogAdapter adapter;
    private boolean isHalfLoad;
    private int totalHours = 0;

    private MediatorLiveData<ArrayList<Subject>> liveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_hours);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CreditHoursModel model = ViewModelProviders.of(this).get(CreditHoursModel.class);
        liveData = model.getLiveData();
        RecyclerView list = findViewById(R.id.list);
        list.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("motion", "downin" + e.getX() + "" + e.getY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("motion", "movein" + e.getX() + "" + e.getY());
                        break;

                }
                return true;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("motion", "down" + e.getX() + "" + e.getY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("motion", "move" + e.getX() + "" + e.getY());
                        break;

                }
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        list.setLayoutManager(new LinearLayoutManager(this));
        hoursListAdapter = new HoursListAdapter(this);

        list.setAdapter(hoursListAdapter);
        FloatingActionButton fab = findViewById(R.id.add);
        setupDraw(this, toolbar);
        fab.setOnClickListener(v -> {
            if (isHalfLoad && totalHours >= 12) {
                Snackbar.make(v, "you can't add more subjects ", Snackbar.LENGTH_LONG).show();
            } else {
                DialogControl(v);
            }
        });

        SessionManager manager = new SessionManager(this);
        findViewById(R.id.done).setOnClickListener(v -> {
            if (totalHours < 12) {
                Snackbar.make(v, "you can't register only " + totalHours + " hours", Snackbar.LENGTH_LONG).show();
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure to register these subjects");
            builder.setPositiveButton("Sure", (dialog, which) -> {
                Log.d("done", "sure");
                JSONArray array = new JSONArray();
                ArrayList<Subject> arrayList = hoursListAdapter.getSubjects();
                for (Subject subject : arrayList)
                    array.put(subject.getSubjectName());
                String end = array.toString();

                new CreditHoursTask(liveData).execute("register", manager.getID(), end);


            });
            builder.setNegativeButton("NO", ((dialog1, which) -> dialog1.dismiss()));
            builder.create().show();
        });
        new CreditHoursTask(liveData).execute("list", manager.getID(), manager.getLevel());
        isHalfLoad = manager.getGpa() < 2f;
    }

    @Override
    protected void onResume() {
        super.onResume();
        liveData.observe(this, hoursListAdapter::setSubjects);
        setupDialog();
    }


    private void setupDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setBackgroundColor(Color.parseColor("#121212"));

        adapter = new DialogAdapter(this);
        ViewModel model = ViewModelProviders.of(this).get(ViewModel.class);
        model.getAll().observe(this, adapter::setSubjects);
        recyclerView.setAdapter(adapter);
        builder.setView(recyclerView);
        dialog = builder.create();
    }

    private void DialogControl(View View) {
        if (adapter.getItemCount() > 0) {
            adapter.setListener((view, subject, list) -> {
                if (adapter.getItemCount() > 0) {

                    totalHours += subject.getHours();
                    if (totalHours > 12 && isHalfLoad) {
                        Snackbar.make(View, "you can't add this subject because total hours will be more than 12 hour", Snackbar.LENGTH_LONG).show();
                        return;
                    }
                    adapter.remove(subject);
                    hoursListAdapter.add(subject);
                    dialog.dismiss();

                }
            });

            dialog.show();
        } else {
            Snackbar.make(View, "There are no more subjects to add", Snackbar.LENGTH_LONG).show();
        }

    }

}
