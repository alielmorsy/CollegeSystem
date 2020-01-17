package aie.sss.server;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import aie.sss.Util.Constants;
import aie.sss.database.files.FilesDatabaseUtil;
import aie.sss.models.Files;

public class ListFilesTask extends AsyncTask<String, Files, Void> {
    private Context context;
    private StringBuilder builder;

    public ListFilesTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            builder = new StringBuilder();
            String name=strings[0].replace(" ","%20");
            URL url = new URL(String.format(Constants.url + "/EductionSystem/getList.php?what=files&subjectName=%s",name));
            InputStream inputStream = url.openConnection().getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
            JSONArray array = new JSONArray(builder.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Files files = new Files();
                files.setDownloaded(false);
                files.setName(object.getString("fileName"));
                files.setSize(object.getString("fileSize"));
                files.setDate(object.getString("time"));
                files.setType(object.getString("type"));
                publishProgress(files);
                Log.d("files","once");
            }
        } catch (IOException | JSONException e) {
            Log.d("c", builder.toString());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Files... values) {
        super.onProgressUpdate(values);
        FilesDatabaseUtil f=new FilesDatabaseUtil(context);
        if(f.get(values[0].getName())==null){
            f.insert(values[0]);
        }


    }
}
