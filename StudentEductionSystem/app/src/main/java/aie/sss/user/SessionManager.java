package aie.sss.user;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Objects;

public class SessionManager {
    private SharedPreferences manager;

    public SessionManager(Context context) {
        manager = context.getSharedPreferences("SessionFile", Context.MODE_PRIVATE);
    }

    public void Login(int id, String password, String name, int level, float totalGpa, String imageName) {
        manager.edit().putInt("ID", id).putString("password", password).putString("Name", name).putInt("level", level).putFloat("gpa", totalGpa).putString("imageName", imageName).apply();
    }

    public boolean CheckLogin() {

        return manager.getInt("ID", -1) != -1;
    }

    public boolean Logout() {
        return manager.edit().clear().commit();
    }

    public String getName() {
        return manager.getString("Name", "no");
    }

    public int getID() {
        return manager.getInt("ID", 0);
    }

    public String getPassword() {
        return manager.getString("password", "no");
    }

    public int getLevel() {
        return manager.getInt("level", 0);
    }

    public float getGpa() {
        return manager.getFloat("gpa", 0);
    }

    public String getImageName() {
        return manager.getString("imageName", "none");
    }

    public boolean checkAll(User user) {
        return user.getLevel() == getLevel() && user.getGpa() == getGpa() && Objects.equals(user.getImageName(), getImageName());
    }
}
