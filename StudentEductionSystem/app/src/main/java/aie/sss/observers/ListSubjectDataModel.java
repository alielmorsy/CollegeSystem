package aie.sss.observers;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;

import java.util.ArrayList;

import aie.sss.models.Assignments;

public class ListSubjectDataModel extends AndroidViewModel {
    private MediatorLiveData<ArrayList<Assignments>> current;
    public ListSubjectDataModel(@NonNull Application application) {
        super(application);
    }

    public MediatorLiveData<ArrayList<Assignments>> getCurrent() {
        if(current==null){
            current=new MediatorLiveData<>();

        }
        return current;
    }
}
