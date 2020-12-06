package model;

import java.util.Set;
import java.util.TreeSet;

public class School {

    //Initialisation données

    private final Student student;
    private final Course course;

    public School() {
        student = new Student();
        course = new Course();
    }

    public Set<String> listOfStudents() {
        return this.student.getStudents();
    }

    public Set<String> listOfCourses() {
        return this.course.getSubscriptions().keySet();
    }

    public Set<String> listOfStudentsByCourse(String c) {
        return this.course.getSubscriptions().get(c);
    }


}
