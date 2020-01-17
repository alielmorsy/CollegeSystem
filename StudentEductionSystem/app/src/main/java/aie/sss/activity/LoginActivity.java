package aie.sss.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.concurrent.ExecutionException;

import aie.sss.R;
import aie.sss.Util.Util;
import aie.sss.server.LoginTask;
import aie.sss.user.SessionManager;
import aie.sss.user.User;
import mehdi.sakout.fancybuttons.FancyButton;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar loading;
    private boolean check;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        check = getIntent().getBooleanExtra("check", false);
        final MaterialEditText id = findViewById(R.id.id);
        final MaterialEditText password = findViewById(R.id.password);
        final FancyButton login = findViewById(R.id.login);
        loading = findViewById(R.id.loading);
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (id.getText() != null && password.getText() != null) {
                    login.setEnabled(true);
                    if (loading.getVisibility() == View.VISIBLE) {
                        loading.setVisibility(View.GONE);
                    }
                }
            }
        };

        id.addTextChangedListener(watcher);
        password.addTextChangedListener(watcher);
        login.setOnClickListener((v) -> {
            loading.setVisibility(View.VISIBLE);
            login.setEnabled(false);
            Login(login, id.getText().toString(), password.getText().toString());
        });
    }

    private void Login(FancyButton login, String id, String password) {
        login.setEnabled(false);
        LoginTask task = new LoginTask();
        task.execute(id, password);
        try {
            User result = task.get();

            if (result != null) {
                SessionManager manager = new SessionManager(this);
                manager.Login(result.getUserID(), password, result.getName(), result.getLevel(), result.getGpa(),result.getImageName());
                if (!check) {
                    Util.Subject(this);
                }
                loading.setVisibility(View.GONE);

                startActivity(new Intent(this, HomeActivity.class));
            } else {
                login.setEnabled(true);
                loading.setVisibility(View.GONE);
                Toast.makeText(this, "error in username or password", Toast.LENGTH_LONG).show();
            }

        } catch (ExecutionException | InterruptedException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
