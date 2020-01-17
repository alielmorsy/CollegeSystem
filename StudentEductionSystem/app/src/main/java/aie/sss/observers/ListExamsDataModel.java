package aie.sss.observers;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;

import java.util.ArrayList;

import aie.sss.models.ExamSubject;

public class ListExamsDataModel extends AndroidViewModel {
    private MediatorLiveData<ArrayList<ExamSubject>> current;
    public ListExamsDataModel(@NonNull Application application) {
        super(application);
    }

    public MediatorLiveData<ArrayList<ExamSubject>> getCurrent() {
        if(current==null){
            current=new MediatorLiveData<>();
        }
        return current;
    }
}
