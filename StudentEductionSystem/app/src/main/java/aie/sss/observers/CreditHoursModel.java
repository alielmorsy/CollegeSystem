package aie.sss.observers;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;

import java.util.ArrayList;

import aie.sss.models.Subject;

public class CreditHoursModel extends AndroidViewModel {
    private MediatorLiveData<ArrayList<Subject>> liveData;

    public CreditHoursModel(@NonNull Application application) {
        super(application);
    }

    public MediatorLiveData<ArrayList<Subject>> getLiveData() {
        if (liveData == null) {
            liveData = new MediatorLiveData<>();

        }
        return liveData;
    }


}
