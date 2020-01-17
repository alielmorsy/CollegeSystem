package aie.sss.server;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import aie.sss.database.subjects.SubjectDatabaseUtil;

public class CommunicateWithServer extends AsyncTask<String, Void, String> {



    public CommunicateWithServer() {

    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            InputStream stream = url.openConnection().getInputStream();
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }
}
