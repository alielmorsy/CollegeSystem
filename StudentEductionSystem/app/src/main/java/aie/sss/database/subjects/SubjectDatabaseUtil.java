package aie.sss.database.subjects;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import aie.sss.Util.Constants;
import aie.sss.models.Subject;

public class SubjectDatabaseUtil {
    private ControlDatabase database;

    public SubjectDatabaseUtil(Context context) {
        database = ControlDatabase.getINSTANCE(context);
    }

    public LiveData<List<Subject>> getAll() {

        return database.getDoa().all();
    }

    public void insert(Subject subject) {
        Constants.excutor.execute(() -> database.getDoa().insert(subject));
    }

    public Subject GetByName(String name) {
        return database.getDoa().getbyName(name);
    }
}
