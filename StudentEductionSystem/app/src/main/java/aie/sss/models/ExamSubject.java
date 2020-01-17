package aie.sss.models;

public class ExamSubject extends Subject {
    private String date,startTime,endTime,day;
    public ExamSubject(String name, String doctorName ,String date, String startTime, String endTime,  String day) {
        super(name, "",doctorName,0);
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }

    public String getDate() {
        return date;
    }


    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDay() {
        return day;
    }
}
