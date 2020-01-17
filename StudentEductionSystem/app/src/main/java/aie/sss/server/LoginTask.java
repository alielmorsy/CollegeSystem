package aie.sss.server;

import android.os.AsyncTask;
import android.util.Log;
import android.util.MalformedJsonException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;

import aie.sss.user.User;

public class LoginTask extends AsyncTask<String, Void, User> {
    private StringBuilder builder;

    @Override
    protected User doInBackground(String... strings) {
        try{
            builder=new StringBuilder();
            URL url = new URL(String.format(Locale.getDefault(), "http://192.168.43.108/EductionSystem/students.php?action=login&userID=%s&password=%s", strings[0], strings[1]));
            Log.d("url",url.getPath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            while ((line=reader.readLine())!=null){
                builder.append(line).append('\n');
            }
            JSONObject userD=new JSONObject(builder.toString());
            User user=new User();
            user.setAge( Integer.parseInt(userD.getString("age")));
            user.setName((String) userD.get("name"));
            user.setUserID(Integer.parseInt( userD.getString("userID")));
            user.setImageName((String)userD.get("imageName"));
            user.setGpa(Float.parseFloat(userD.getString("gpa")));
            user.setLevel(Integer.parseInt(userD.getString("level")));
            return user;
        } catch (IOException | JSONException e) {
            Log.e("as",builder.toString()+" a");
            e.printStackTrace();
        }
        return null;
    }


}
