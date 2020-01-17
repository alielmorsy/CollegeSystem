package aie.sss.server;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import aie.sss.models.ExamSubject;
import aie.sss.observers.ListExamsDataModel;

public class ExamScheduleDownload extends Thread {
    private ListExamsDataModel model;
    private int level;


    public ExamScheduleDownload(ListExamsDataModel model, int level) {
        this.model = model;
        this.level=level;
    }

    @Override
    public void run() {

        try {
            URL link = new URL(String.format(Locale.getDefault(), "http://192.168.43.108/EductionSystem/students.php?action=examTable&level=%d", level));
            InputStream inputStream = link.openConnection().getInputStream();
            ArrayList<ExamSubject> arrayList = toArray(inputStream);
            model.getCurrent().postValue(arrayList);
        } catch (IOException | JSONException e) {

            e.printStackTrace();
        }

    }

    private ArrayList<ExamSubject> toArray(InputStream stream) throws IOException, JSONException {
        ArrayList<ExamSubject> arrayList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        StringBuilder bulider = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            bulider.append(line);

        }
        String Json = bulider.toString();
        Log.d("json", Json + " a" + Json.charAt(0));
        if (Json.startsWith("[")) {
            JSONArray array = new JSONArray();
            for (int i = 0; i < array.length(); i++) {
                JSONObject json = array.getJSONObject(i);
                String subjectName = json.getString("subjectName");
                String date = json.getString("date");
                String startTime = json.getString("startTime");
                String endTime = json.getString("endTime");
                String day = json.getString("day");

                ExamSubject subject = new ExamSubject(subjectName, null, date, startTime, endTime, day);

                arrayList.add(subject);
            }
        } else if (Json.startsWith("{")) {
            JSONObject json = new JSONObject(Json);
            String subjectName = json.getString("subjectName");
            String date = json.getString("date");
            String startTime = json.getString("startTime");
            String endTime = json.getString("endTime");
            String day = json.getString("day");

            ExamSubject subject = new ExamSubject(subjectName, null, date, startTime, endTime, day);

            arrayList.add(subject);

        }
        return arrayList;
    }
}
