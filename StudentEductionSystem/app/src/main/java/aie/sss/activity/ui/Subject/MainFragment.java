package aie.sss.activity.ui.Subject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import aie.sss.R;

public class MainFragment extends Fragment {
    private View[] v;
    private String name;

    public MainFragment(String name, View... v) {
        this.v = v;
        this.name=name;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_subject2, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CardView assignment = view.findViewById(R.id.assignmentCard);
        CardView quiz = view.findViewById(R.id.quizCard);
        CardView files = view.findViewById(R.id.filesCard);
        CardView chat = view.findViewById(R.id.chatCard);
        assignment.setPressed(true);
        quiz.setPressed(true);
        files.setPressed(true);
        chat.setPressed(true);
        Toolbar toolbar = (Toolbar) v[0];
        assignment.setOnClickListener((v) -> goFragment(new ListFragment(name, "assignment", toolbar)));
        quiz.setOnClickListener((v) -> goFragment(new ListFragment(name,"quiz", toolbar)));
        files.setOnClickListener((v) -> goFragment(new ListFragment(name,"file", toolbar)));
        chat.setOnClickListener((v) -> {
        });

    }

    private void goFragment(Fragment fragment) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = null;
        if (manager != null) {
            transaction = manager.beginTransaction();
            transaction.add(R.id.SubjectFrame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            throw new RuntimeException("There are a big stack please try again later");
        }

    }
}
