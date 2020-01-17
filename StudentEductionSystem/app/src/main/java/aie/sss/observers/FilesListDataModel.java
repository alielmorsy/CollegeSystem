package aie.sss.observers;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;

public class FilesListDataModel extends AndroidViewModel {
    private MediatorLiveData data;
    public FilesListDataModel(@NonNull Application application) {
        super(application);
    }

    public MediatorLiveData getData() {
        if(data==null){
            data=new MediatorLiveData();
        }
        return data;
    }
}
