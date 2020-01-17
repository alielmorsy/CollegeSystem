package aie.sss.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import aie.sss.R;
import aie.sss.activity.ui.result.ResultAdapter;
import aie.sss.models.Result;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Your Result");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RecyclerView list = findViewById(R.id.list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        list.setLayoutManager(manager);

        ResultAdapter adapter = new ResultAdapter(this);
        list.setAdapter(adapter);

        ArrayList<Result> results = new ArrayList<>();
        results.add(new Result("ali", 85.5f, 3, 1));
        results.add(new Result("mohamed", 50.5f, 3, 1));
        results.add(new Result("elmorsy", 94.5f, 3, 1));
        results.add(new Result("ibrahim", 66.5f, 3, 1));
        adapter.setList(results);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
