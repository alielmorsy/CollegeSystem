package aie.sss.Util;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import aie.sss.database.subjects.SubjectDatabaseUtil;
import aie.sss.models.Files;
import aie.sss.models.Subject;
import aie.sss.server.CommunicateWithServer;
import aie.sss.user.SessionManager;

public class Util {
    public static Files JSONTOFILE(String json) {
        return null;
    }

    public static String toLevel(int level) {
        switch (level) {
            case 0:
                return Constants.Levels.one;
            case 1:
                return Constants.Levels.one_second;
            case 2:
                return Constants.Levels.two;
            case 3:
                return Constants.Levels.two_second;
            case 4:
                return Constants.Levels.third;
            case 5:
                return Constants.Levels.third_second;
            case 6:
                return Constants.Levels.fourth;
            case 7:
                return Constants.Levels.fourth_second;
            default:
                return "Error in server contact with server administration";
        }
    }

    public static boolean Subject(AppCompatActivity context) {
        String url = String.format(Locale.getDefault(), Constants.url + "/EductionSystem/getList.php?level=%d&what=SubjectList", new SessionManager(context).getLevel());
        CommunicateWithServer task = new CommunicateWithServer();
        task.execute(url);
        SubjectDatabaseUtil util = new SubjectDatabaseUtil(context);
        try {
            String message = task.get();
            if (message != null) {
                Log.d("message", message);

                JSONArray array = new JSONArray(message);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    Subject subject = new Subject(object.getString("name"), object.getString("code"), object.getString("doctor_id"), Integer.parseInt(object.getString("hours")));
                    if (util.GetByName(subject.getSubjectName()) == null)
                        util.insert(subject);


                }
                return true;
            }
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean CheckConnection() {
        Log.d("try Connect", "done");
        try {
            URL url = new URL(Constants.url + "/EductionSystem/actions.php?action=check");
            Log.d("try Connect", "done");
            url.openConnection().connect();

            return true;
        } catch (IOException e) {
            //   Toast.makeText(this, "Can't connect to server", Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
