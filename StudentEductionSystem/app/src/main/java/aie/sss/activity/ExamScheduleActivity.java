package aie.sss.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import aie.sss.R;
import aie.sss.activity.ui.ExamSchedule.ExamsListAdapter;
import aie.sss.observers.ListExamsDataModel;
import aie.sss.server.ExamScheduleDownload;
import aie.sss.user.SessionManager;

public class ExamScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView list = findViewById(R.id.list);
        TextView text = findViewById(R.id.termText);
        int level = new SessionManager(this).getLevel();
        String term = level == 1 ? "first" : "second";
        text.setText(String.format(getString(R.string.exam_sch_text), term));

        ExamsListAdapter adapter = new ExamsListAdapter(this);
        list.setAdapter(adapter);
        ListExamsDataModel model = ViewModelProviders.of(this).get(ListExamsDataModel.class);
        model.getCurrent().observe(this, adapter::setArrayList);
        new ExamScheduleDownload(model, level).start();

    }
}
