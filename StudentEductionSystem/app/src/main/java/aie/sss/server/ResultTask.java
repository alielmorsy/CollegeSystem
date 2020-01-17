package aie.sss.server;

import android.app.Activity;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;

import aie.sss.activity.ui.result.ResultAdapter;
import aie.sss.models.Result;

public class ResultTask extends AsyncTask<Integer, Void, ArrayList<Result>> {
    private ResultAdapter adapter;
    private WeakReference<Activity> activity;
    public ResultTask(Activity activity,ResultAdapter adapter) {
        this.adapter = adapter;
        this.activity= new WeakReference<>(activity);
    }

    @Override
    protected ArrayList<Result> doInBackground(Integer... integers) {
        try {
            ArrayList<Result> results = new ArrayList<>();
            URL url = new URL("http://192.168.43.75/students.php?action=getResult&&id=" + integers[0]);
            InputStream stream = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
            JSONArray array = new JSONArray(builder.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String subjectName = object.getString("subjectName");
                float degree = (float) object.getDouble("degree");
                int hours = object.getInt("hours");
                int level = object.getInt("level");
                results.add(new Result(subjectName, degree, hours, level));
            }

            return results;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Result> results) {
        super.onPostExecute(results);
        activity.get().runOnUiThread(()->adapter.setList(results));
    }
}
