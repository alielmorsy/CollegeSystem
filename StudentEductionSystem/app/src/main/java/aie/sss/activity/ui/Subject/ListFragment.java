package aie.sss.activity.ui.Subject;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import aie.sss.R;
import aie.sss.Util.FileUtil;
import aie.sss.Util.onItemClickListener;
import aie.sss.database.files.FileViewModel;
import aie.sss.models.Assignments;
import aie.sss.models.DataSubjects;
import aie.sss.models.Files;
import aie.sss.observers.ListSubjectDataModel;
import aie.sss.server.DownloadTask;
import aie.sss.server.ListFilesTask;

public class ListFragment extends Fragment {
    private String type;
    private Toolbar toolbar;
    private RelativeLayout error_Table;
    private RelativeLayout waiting;

    private  String subjectName;
    public ListFragment(String subjectName,String type, Toolbar toolbar) {
        this.type = type;
        this.toolbar = toolbar;
        toolbar.setTitle(type + "s List");
        this.subjectName=subjectName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView text = view.findViewById(R.id.text);
        text.setText(String.format(getString(R.string.nothing), type + "s"));

        waiting = view.findViewById(R.id.waiting);
        error_Table = view.findViewById(R.id.error_table);

        RecyclerView list = view.findViewById(R.id.list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        list.setLayoutManager(manager);


        switch (type) {
            case "assignment":
                assignmentList(list);
                break;
            case "file":
                FilesList(list);
                break;
            default:
                waiting.setVisibility(View.GONE);
                error_Table.setVisibility(View.VISIBLE);
                text.setText("Sorry this section not ready yet");
        }

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void assignmentList(RecyclerView list) {
        ListSubjectDataModel model = ViewModelProviders.of(this).get(ListSubjectDataModel.class);

        Observer<ArrayList<Assignments>> observer;
        AssignmentsAdapter adapter = new AssignmentsAdapter(getContext());
        list.setAdapter(adapter);
        observer = files -> {
            if (files.size() > 0) {
                error_Table.setVisibility(View.GONE);
                if (waiting.getVisibility() == View.VISIBLE) {
                    waiting.setVisibility(View.GONE);
                }
                if (list.getVisibility() == View.GONE) {
                    list.setVisibility(View.VISIBLE);
                }
                adapter.setList(files);
            } else {
                error_Table.setVisibility(View.VISIBLE);
            }
        };
        list.setAdapter(adapter);
        model.getCurrent().observe(this, observer);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ArrayList<Assignments> a = new ArrayList<>();
                a.add(new Assignments("asdw", "asdwd", "asd", true, true));
                a.add(new Assignments("ali ibrahim hassan elmorsy", "asdwd", "asd", true, false));
                a.add(new Assignments("asd", "asdw", "asd", false, true));
                model.getCurrent().postValue(a);
            }
        };

        new Timer().schedule(task, 5555);
    }

    private void FilesList(RecyclerView list) {
        new ListFilesTask(getActivity()).execute(subjectName);
        FilesAdapter adapter = new FilesAdapter(getContext());
        FileViewModel model = ViewModelProviders.of(this, null).get(FileViewModel.class);
        LiveData<List<Files>> liveData = model.getFiles();
        liveData.observe(getViewLifecycleOwner(), files -> {

            if (files.size() > 0) {
                error_Table.setVisibility(View.GONE);
                if (waiting.getVisibility() == View.VISIBLE) {
                    waiting.setVisibility(View.GONE);
                }
                if (list.getVisibility() == View.GONE) {
                    list.setVisibility(View.VISIBLE);
                }
                adapter.setFiles(files);
            } else {
                error_Table.setVisibility(View.VISIBLE);
                waiting.setVisibility(View.GONE);
            }
        });
        list.setAdapter(adapter);
        adapter.setListener(new onItemClickListener() {
            @Override
            public void onItemClick(DataSubjects subjects, int pos, RecyclerView.ViewHolder holder) {

            }

            @Override
            public void onItemClick(Files subjects, int pos, RecyclerView.ViewHolder holder) {
                FilesAdapter.FileViewHolder Holder = (FilesAdapter.FileViewHolder) holder;
                File file = new File(Environment.getExternalStorageDirectory(), subjects.getName());
                if (!subjects.isDownloaded()) {
                    new DownloadTask(Holder.icon, getActivity()).execute(subjects);
                    Holder.icon.withOffset(1).showLoading().withAutoHide(false).withBorderColor(Color.GREEN);

                } else {
                    FileUtil.openFile(getActivity(),file);
                }
            }
        });
    }
}
