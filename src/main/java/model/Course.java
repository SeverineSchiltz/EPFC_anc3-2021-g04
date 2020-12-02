package model;

import java.util.Set;
import java.util.TreeSet;

public class Course implements Comparable<Course> {
    private String name;
    private Set<Student> students = new TreeSet<>();
    private static final int MAX_STUDENTS_PER_COURSE = 5;

    public Course(String name, Set<Student> students) {
        this.name = name;
        this.students = students;
    }

    public void setStudents(Set<Student> students) {
        if (students.size() <= MAX_STUDENTS_PER_COURSE) {
            this.students = students;
        } else {
            throw new RuntimeException("L'étudiant ne peut pas avoir plus de 2 cours");
        }
    }

    public void addStudent(Student s) {
        if (!isCourseComplete()) {
            students.add(s);
        }
    }

    public void removeStudent(Student s) {
        students.remove(s);
    }

    public Set<String> getStudentsName() {
        Set<String> stuName = new TreeSet<>();
        for (Student s : students) {
            stuName.add(s.toString());
        }
        return stuName;
    }

    public boolean isCourseComplete() {
        return isCourseComplete(this.students);
    }

    public static boolean isCourseComplete(Set<Student> students) {
        return students.size() >= MAX_STUDENTS_PER_COURSE;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        boolean isEq = false;
        if (o instanceof Course) {
            Course other = (Course) o;
            if (other.name.equals(this.name)) {
                isEq = true;
            }
        }
        return isEq;
    }

    @Override
    public int compareTo(Course o) {
        return this.name.compareToIgnoreCase(o.name);
    }
}
