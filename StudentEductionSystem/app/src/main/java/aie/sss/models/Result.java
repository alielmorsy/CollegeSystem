package aie.sss.models;

public class Result {
    private String name;
    private float degree;
    private int hours, level;


    public Result(String name, float degree, int hours, int level) {
        this.name = name;
        this.degree = degree;
        this.hours = hours;
        this.level = level;
    }

    public int getHours() {
        return hours;
    }

    public float getDegree() {
        return degree;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }
}
