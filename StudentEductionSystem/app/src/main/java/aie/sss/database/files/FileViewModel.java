package aie.sss.database.files;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import aie.sss.models.Files;

public class FileViewModel extends AndroidViewModel {
    private LiveData<List<Files>>  files;
    public FileViewModel(@NonNull Application application) {
        super(application);
        files=new FilesDatabaseUtil(application).getAll();
    }

    public LiveData<List<Files>> getFiles() {
        return files;
    }
}
