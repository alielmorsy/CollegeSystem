package aie.sss.server;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import aie.sss.Util.Constants;
import aie.sss.database.files.FilesDatabaseUtil;
import aie.sss.models.Files;
import aie.sss.view.ProgressImageView;

@SuppressWarnings("StaticFieldLeak")
public class DownloadTask extends AsyncTask<Files, Float, Files> {
    private int downloading = 0;
    private ProgressImageView loading;
    private Context context;
    private Handler handler;
    private File file;

    public DownloadTask(ProgressImageView loading, Context context) {
        this.loading = loading;
        this.context = context;
        handler = new Handler(context.getMainLooper());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Files doInBackground(Files... files) {
        try {
            file = new File(Environment.getExternalStorageDirectory(), files[0].getName());

            boolean a = file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);
            URL link = new URL(Constants.url + "/EductionSystem/download.php?fileName=" + files[0].getName().replace(" ","%20"));
            InputStream inputStream = link.openConnection().getInputStream();
            byte[] b = new byte[1024];
            int count;
            int fileSize = inputStream.available();
            handler.post(() -> loading.showProgress());
            while ((count = inputStream.read(b)) > 0) {
                downloading += count;
                if ((downloading - count) == 0) {
                    String s = "<br />\n" +
                            "<b>Warning</b>: ";
                    String t = new String(b, 0, count);
                    if (s.equals(t)) {
                        return null;

                    }
                }
                fos.write(b, 0, count);
                if (inputStream.available() != 0) {
                    publishProgress((downloading * 360f) / fileSize);
                } else {
                    publishProgress(360f);
                }

            }

            return files[0];
        } catch (IOException e) {
            handler.post((() -> loading.failure()));
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Float... values) {
        super.onProgressUpdate(values);
        handler.post(() -> loading.setProgress(values[0]));
    }

    @Override
    protected void onPostExecute(Files files) {
        super.onPostExecute(files);
        if (files == null) {
            loading.failure();
        } else {
            loading.success();
            files.setDownloaded(true);
            new FilesDatabaseUtil(context).updateDownloaded(files.getName());
        }
    }
}