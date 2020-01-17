package aie.sss.database.notification;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;



public class ViewModel extends AndroidViewModel {
    private LiveData<List<Notification>> notifications;
    public ViewModel(@NonNull Application application) {
        super(application);
        DatabaseUtil util=new DatabaseUtil(application);
        //BigDatabase.databaseWriteExecutor.execute(util::all);
        notifications =util.all();

    }

    public LiveData<List<Notification>> getNotifications() {
        return notifications;
    }
}

