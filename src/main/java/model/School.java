package model;

import java.util.Set;
import java.util.TreeSet;

public class School {
    private static Set<Course> subscriptions = new TreeSet<>();
    private static Set<Student> students = new TreeSet<>();

    public static Set<String> getSubscriptionsName() {
        Set<String> subName = new TreeSet<>();
        for (Course s : subscriptions) {
            subName.add(s.toString());
        }
        return subName;
    }

    public static Set<String> getStudentName() {
        Set<String> stuName = new TreeSet<>();
        for (Student s : students) {
            stuName.add(s.toString());
        }
        return stuName;
    }


    public static void setSubscriptions(Set<Course> subscriptions) {
        School.subscriptions = subscriptions;
    }

    public static Set<Student> getStudents() {
        return students;
    }

    public static void setStudents(Set<Student> students) {
        School.students = students;
    }

    public static Student findStudent(String name) {
        for (Student s : students) {
            if (s.toString().equals(name)) {
                return s;
            }
        }
        return null;
    }

    public static Course findCourse(String name) {
        for (Course c : subscriptions) {
            if (c.toString().equals(name)) {
                return c;
            }
        }
        return null;
    }

}
