package aie.sss.database.subjects;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import aie.sss.models.Subject;

public class ViewModel extends AndroidViewModel {
    private SubjectDatabaseUtil util;
    private LiveData<List<Subject>> all;
    public ViewModel(@NonNull Application application) {
        super(application);
        util=new SubjectDatabaseUtil(application);
        all=util.getAll();
    }

    public LiveData<List<Subject>> getAll() {
        return all;
    }
}
