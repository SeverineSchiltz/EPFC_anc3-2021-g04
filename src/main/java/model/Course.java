package model;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;


public class Course implements Comparable<Course>{
    private static final int MAX_STUDENTS_PER_COURSE = 5;
    private String name;
    private Set<Student> students = new TreeSet<>();

    public Course(String name, Set<Student> students){
        this.name = name;
        this.students = students;
    }

    public String getName(){
        return this.name;
    }

    public Course(String name){
        this.name=name;
    } //doit-être gardé?

    public Set<Student> getStudents(){
        return (Set<Student>) Collections.unmodifiableSet(students);
    }

    public boolean addStudent(Student student){
        if (!this.isFull()){
            students.add(student);
            return true;
        }
        return false;
    }

    public boolean isFull(){
        return this.students.size() >= MAX_STUDENTS_PER_COURSE;
    }

    public void removeStudent(Student student){
        students.remove(student);

    }

    @Override
    public int compareTo(Course o) {
        return this.name.compareToIgnoreCase(o.name);
    }

    public boolean contains(Student s){
        return true;
    }

    public boolean isFollowedBy(Student s) {
        return students.contains(s);
    }

    @Override
    public boolean equals(Object obj) {
        boolean res = false;
        if (obj instanceof Course) {
            Course other = (Course) obj;
            if (other.name.equals(this.name)) {
                res = true;
            }
        }
        return res;
    }
}
