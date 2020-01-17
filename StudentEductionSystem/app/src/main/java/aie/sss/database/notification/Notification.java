package aie.sss.database.notification;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notifications")
public class Notification {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "name")
    @NonNull
    private String title;

    @ColumnInfo(name = "type")
    @NonNull
    private String type;

    @ColumnInfo(name = "message")
    @NonNull
    private String message;
    @ColumnInfo(name = "date")
    private String time;

    public Notification(@NonNull String title, @NonNull String type, @NonNull String message, String time) {
        this.time = time;
        this.title = title;
        this.message = message;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

            @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(@NonNull String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
