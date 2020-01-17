package aie.sss.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subjects")
public class Subject {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo
    private String subjectName;

    @ColumnInfo
    private String doctorName;

    @ColumnInfo
    private String code;

    @ColumnInfo
    private int hours;

    public Subject(String subjectName, String code, String doctorName, int hours) {
        this.subjectName = subjectName;
        this.doctorName = doctorName;
        this.code = code;

        this.hours = hours;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getHours() {
        return hours;
    }

    public String getCode() {
        return code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
