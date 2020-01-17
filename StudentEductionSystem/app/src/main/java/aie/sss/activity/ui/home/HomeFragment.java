package aie.sss.activity.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import aie.sss.R;
import aie.sss.activity.AbsentActivity;
import aie.sss.activity.ExamScheduleActivity;
import aie.sss.activity.ResultActivity;


public class HomeFragment extends Fragment {

    private CardView exam, results, absent;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        exam = root.findViewById(R.id.exam);
        results = root.findViewById(R.id.results);
        absent = root.findViewById(R.id.absent);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        exam.setOnClickListener((v)->startActivity(new Intent(getContext(), ExamScheduleActivity.class)));
        absent.setOnClickListener((v) -> startActivity(new Intent(getContext(), AbsentActivity.class)));
        results.setOnClickListener((v) -> startActivity(new Intent(getContext(), ResultActivity.class)));
    }
}