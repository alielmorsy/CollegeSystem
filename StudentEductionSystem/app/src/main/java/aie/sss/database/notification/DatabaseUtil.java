package aie.sss.database.notification;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import aie.sss.Util.Constants;

public class DatabaseUtil {
    private BigDatabase database;

    public DatabaseUtil(Context c) {
        database=BigDatabase.getInstance(c);

    }
    public void insert(Notification n){

        Constants.excutor.execute(()->database.notificationDoa().insert(n));
    }
    public LiveData<List<Notification>> all(){
        return database.notificationDoa().all();
    }

    public Notification getByTimeAndTitle(String time,String title){
        return database.notificationDoa().getByTime(time,title);
    }
}

