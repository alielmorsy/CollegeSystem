package aie.sss.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Objects;
import java.util.Timer;

import aie.sss.server.NotificationTask;
import aie.sss.user.SessionManager;

public class NotificationService extends Service {
    private SessionManager manager;
    private Timer timer;

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = new SessionManager(this);
        timer = new Timer();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Objects.equals(intent.getAction(), "start")) {
         timer.schedule(new NotificationTask(this, new int[]{manager.getLevel(), manager.getID()}), 4000, 3000);

        }
        return START_NOT_STICKY;
    }
}
