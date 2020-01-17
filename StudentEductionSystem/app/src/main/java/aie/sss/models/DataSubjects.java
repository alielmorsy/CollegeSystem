package aie.sss.models;

public abstract class DataSubjects {
    private String name, date;

    public DataSubjects(String name, String date) {
        this.name = name;
        this.date = date;
    }

    protected DataSubjects() {
    }

    public String getName() {
        return name;
    }
    public String getDate(){
        return date;
    }
}
