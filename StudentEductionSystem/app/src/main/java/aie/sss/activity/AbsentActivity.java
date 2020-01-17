package aie.sss.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;

import aie.sss.R;
import aie.sss.Util.Constants;
import aie.sss.user.SessionManager;

public class AbsentActivity extends AppCompatActivity {

    private SessionManager manager;
    private QRCodeReaderView reader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absent);
        manager = new SessionManager(this);
        reader = findViewById(R.id.reader);

        reader.setOnQRCodeReadListener((text, points) -> {
            if (text != null && text.length() > 0) {
                reader.setQRDecodingEnabled(false);
                reader.stopCamera();
                handle(text);
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        reader.setBackCamera();
        reader.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        reader.stopCamera();
    }

    private void handle(String text) {
        new Thread(() -> {
            try {
                runOnUiThread(() -> Toast.makeText(this, "Waiting", Toast.LENGTH_SHORT).show());
                String s = Constants.url + "/EductionSystem/students.php?action=absent&code=%s&userID=%d&level=%d";
                s = String.format(Locale.US, s, text, manager.getID(), manager.getLevel());
                URL url = new URL(s);
                InputStream inputStream = url.openStream();
                byte[] b = new byte[256];
                int c = inputStream.read(b);
                String message = new String(b, 0, c);

                switch (message) {
                    case "invalid code":
                        runOnUiThread(() -> Toast.makeText(this, "Invalid QrCode Please try again or check your camera", Toast.LENGTH_LONG).show());
                        runOnUiThread(reader::startCamera);
                        break;
                    case "error":
                        runOnUiThread(() -> Toast.makeText(this, "There ara an error in server please try again after several minutes", Toast.LENGTH_LONG).show());
                        runOnUiThread(reader::startCamera);
                        break;
                    case "can't read qr reader for another level ":
                        runOnUiThread(() -> Toast.makeText(this, "can't read qr reader for another level ", Toast.LENGTH_LONG).show());
                        Thread.sleep(1400);
                        runOnUiThread(this::finish);
                        break;
                    case "already take absent":
                        runOnUiThread(() -> Toast.makeText(this, "You are already use this QrCode to take your absent", Toast.LENGTH_LONG).show());
                        Thread.sleep(1000);
                        runOnUiThread(this::finish);
                        break;
                    default:
                        runOnUiThread(() -> Toast.makeText(this, "the qr code read successfully and register your absent in " + message, Toast.LENGTH_LONG).show());
                        Thread.sleep(1400);
                        runOnUiThread(this::finish);
                }


            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "There are problem in your connection with server check connection and try again", Toast.LENGTH_LONG).show());

                runOnUiThread(this::finish);

            }

        }).start();
    }
}
