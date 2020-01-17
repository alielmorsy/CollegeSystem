package aie.sss.server;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Locale;
import java.util.TimerTask;

import aie.sss.R;
import aie.sss.Util.Constants;
import aie.sss.database.notification.DatabaseUtil;
import aie.sss.database.notification.Notification;

public class NotificationTask extends TimerTask {
    private StringBuilder builder;
    private Context context;
    private int[] data;

    public NotificationTask(Context context, int[] data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public void run() {
        builder = new StringBuilder();
        NotificationManager manager = context.getSystemService(NotificationManager.class);
        NotificationCompat.Builder n;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("AIE", "", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
            n = new NotificationCompat.Builder(context, channel.getId());
        } else {
            n = new NotificationCompat.Builder(context, "AIE");
        }


        try {

            DatabaseUtil database = new DatabaseUtil(context);
            URL url = new URL(String.format(Locale.getDefault(), "%s/EductionSystem/notification.php?level=%d&userID=%d", Constants.url, data[0], data[1]));
            InputStream stream = url.openConnection().getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            n.setSmallIcon(R.drawable.ic_launcher_background);
            n.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            n.setVibrate(new long[]{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0});
            JSONArray array = new JSONArray(builder.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                if (database.getByTimeAndTitle(object.getString("created_at"), object.getString("title")) == null) {
                    Notification notification = new Notification(object.getString("title"), object.getString("type"), object.getString("message"), object.getString("created_at"));
                    n.setContentTitle(notification.getTitle());
                    n.setContentText(notification.getMessage());
                    n.setSubText(notification.getType());
                    NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle(n);
                    style.setBigContentTitle(notification.getTitle());
                    style.setSummaryText(notification.getMessage());
                    n.setStyle(style);
                    if (manager != null) {
                        manager.notify(i, n.build());
                    }
                    database.insert(notification);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d("readLine", builder.toString());
        }
    }
}
