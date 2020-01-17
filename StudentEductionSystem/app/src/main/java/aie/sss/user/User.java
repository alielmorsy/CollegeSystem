package aie.sss.user;

public class User {
    private String name,imageName;
    private int age,year,userID,level;
    private float gpa;
    public User(String name, String imageName, int year, int age, int userID,float gpa,int level) {
        this.name = name;
        this.imageName = imageName;
        this.year = year;
        this.age = age;
        this.userID = userID;
        this.level=level;
    }

    public User() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public int getAge() {
        return age;
    }

    public int getUserID() {
        return userID;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
