package aie.sss.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import aie.sss.models.Subject;

public class Constants {
    public static final ExecutorService excutor = Executors.newFixedThreadPool(4);
    public static final String url="http://192.168.43.108";

    public static List<Subject> allSubjects;

    public static class Levels {
        public static final String one = "Level one first term";
        public static final String one_second = "Level one second term";
        public static final String two = "Level one first term";
        public static final String two_second = "Level two second term";
        public static final String third="Level three first term";
        public static final String third_second="Level three second term";
        public static final String fourth="Level four first term";
        public static final String fourth_second="Level four second term";
    }
}
