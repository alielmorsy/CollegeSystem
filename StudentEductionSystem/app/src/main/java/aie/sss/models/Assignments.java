package aie.sss.models;

import aie.sss.models.DataSubjects;

public class Assignments extends DataSubjects {
    private boolean solved, closed;
    private String time;

    public Assignments(String title, String time, String date,boolean solved, boolean closed) {
        super(title, date);
        this.closed = closed;
        this.solved = solved;
        this.time=time;
    }

    public boolean isSolved() {
        return solved;
    }

    public boolean isClosed() {
        return closed;
    }

    public String getTime() {
        return time;
    }
}
