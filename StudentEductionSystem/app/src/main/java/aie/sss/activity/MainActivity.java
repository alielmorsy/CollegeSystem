package aie.sss.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

import aie.sss.R;
import aie.sss.Util.Util;
import aie.sss.server.LoginTask;
import aie.sss.user.SessionManager;
import aie.sss.user.User;

public class MainActivity extends AppCompatActivity {
    TextView loadingText;
    private SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new SessionManager(getApplicationContext());

        loadingText = findViewById(R.id.textLoading);
        loadingText.setText("getting Subject List");
    }

    @Override
    protected void onResume() {
        super.onResume();


        new Handler().postAtTime(() -> new Thread(this::next).start(), 1000);

    }

    private void next() {
        boolean check = Util.Subject(this);
        if (check) {

            runOnUiThread(() -> loadingText.setText("get Successfully check Loading Please Wait..."));
        }

        if (manager.CheckLogin()) {
            runOnUiThread(() -> loadingText.setText("Wait for Check Login"));

            if (Util.CheckConnection()) {
                if (!loginAgain()) {
                    startActivity(new Intent(this, LoginActivity.class).putExtra("subject", check));
                    return;
                }
                startActivity(new Intent(this, HomeActivity.class));
                return;
            }
            startActivity(new Intent(this, HomeActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class).putExtra("subject", check));
        }
    }

    private boolean loginAgain() {
        LoginTask task = new LoginTask();
        task.execute(String.valueOf(manager.getID()), manager.getPassword());
        try {
            User result = task.get();
            if (result != null) {

                if (!manager.checkAll(result))
                    manager.Login(result.getUserID(), manager.getPassword(), result.getName(), result.getLevel(), result.getGpa(), result.getImageName());
                startActivity(new Intent(this, HomeActivity.class));
            } else {
                runOnUiThread(() -> Toast.makeText(this, "an error in Server Please contact with administrator", Toast.LENGTH_LONG).show());

            }
            return true;
        } catch (ExecutionException | InterruptedException | NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }
}
