package aie.sss.database.files;

import android.content.Context;


import androidx.lifecycle.LiveData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import aie.sss.Util.Constants;
import aie.sss.models.Files;

public class FilesDatabaseUtil {

    private Database database;
    public FilesDatabaseUtil(Context context){

        database=Database.getINSTANCE(context);
           }
    public LiveData<List<Files>> getAll(){
        return database.getDoa().all();
    }
    public void insert(Files file){
        Constants.excutor.execute(()-> database.getDoa().insert(file));
    }
    public void delete(Files file){
        Constants.excutor.execute(()->database.getDoa().delete(file));
    }
    public  void deleteAll(){
        Constants.excutor.execute(()->database.getDoa().deleteAll());
    }
    public Files get(String name){
        return database.getDoa().get(name);
    }

    public void updateDownloaded(String name){
        Constants.excutor.execute(()->database.getDoa().UPDATEDownload(name));
    }
}
