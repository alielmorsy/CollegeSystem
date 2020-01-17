package aie.sss.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Files")
public class Files {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String date;
    @ColumnInfo
    private String type;
    @ColumnInfo
    private String size;

    @ColumnInfo
    private boolean downloaded;

    @Ignore
    public Files() {

    }

    public Files(String name, String date, String size, String type, boolean downloaded) {
        this.name = name;
        this.date = date;
        this.size = size;
        this.type = type;
        this.downloaded = downloaded;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDownloaded() {
        return downloaded;
    }

    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }
}
