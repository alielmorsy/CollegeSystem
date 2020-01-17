package aie.sss.server;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MediatorLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import aie.sss.Util.Constants;
import aie.sss.models.Subject;

public class CreditHoursTask extends AsyncTask<Object, Void, Object> {
    private MediatorLiveData<ArrayList<Subject>> liveData;

    public CreditHoursTask(MediatorLiveData<ArrayList<Subject>> liveData) {
        this.liveData = liveData;
    }

    @Override
    protected Object doInBackground(Object... objects) {
        String action = (String) objects[0];
        int id = (int) objects[1];
        String data = null;
        switch (action) {
            case "register":

                data = (String) objects[2];
                data = "action=register&data=" + data + "&id=" + id;
                break;
            case "list":
                data = "action=list&id=" + id+"&level="+objects[2];

        }

        try {
            URL url = new URL(Constants.url + "/EductionSystem/registerHours.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            assert data != null;
            connection.setRequestProperty("Content-Length", String.valueOf(data.length()));
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = new byte[1024];
            int count = connection.getInputStream().read(bytes);
            if (count > 0) {
                return new String(bytes, 0, count);
            }else{
                throw new RuntimeException("Can't Return with -1  in bytes read");
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    @Override
    protected void onPostExecute(Object o) {
        Log.d("adw",String.valueOf(o));
        if (!(o instanceof Boolean)) {
            String result = (String) o;
            if (result.startsWith("[")) {
                try {
                    ArrayList<Subject> subjects = new ArrayList<>();
                    JSONArray array = new JSONArray();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        Subject subject = new Subject(object.getString("subjectName"), object.getString("code"), object.getString("doctorName"), Integer.parseInt(object.getString("hours")));
                        subjects.add(subject);
                        liveData.postValue(subjects);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}