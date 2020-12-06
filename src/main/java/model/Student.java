package model;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/*
Créer des classes qui représentent votre modèle métier, dans lesquelles vous placez
- les données métier,
- la gestions de ces données et
- les règles métier associées.
 */

import java.util.Set;
import java.util.TreeSet;

public class Student implements Comparable<Student> {
    private String name;
    private Set<Course> subscriptions = new TreeSet<>();
    private static final int MAX_COURSES_PER_STUDENT = 2;

    public Student(String name, Set<Course> subscriptions) {
        this.name = name;
        this.subscriptions = subscriptions;
    }

    public Student(String name) {
        this.name = name;
    }

    public void setSubscriptions(Set<Course> subscriptions) {
        if (subscriptions.size() <= MAX_COURSES_PER_STUDENT) {
            this.subscriptions = subscriptions;
        } else {
            throw new RuntimeException("L'étudiant " + this.name + " ne peut pas avoir plus de 2 cours");
        }
    }

    public void addCourse(Course c) {
        if (!isStudentComplete()) {
            subscriptions.add(c);
        }
    }

    public void removeCourse(Course c) {
        subscriptions.remove(c);
    }

    public boolean isStudentComplete() {
        return isStudentComplete(subscriptions);
    }

    public static boolean isStudentComplete(Set<Course> subscriptions) {
        return subscriptions.size() >= MAX_COURSES_PER_STUDENT;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        boolean isEq = false;
        if (o instanceof Student) {
            Student other = (Student) o;
            if (other.name.equals(this.name)) {
                isEq = true;
            }
        }
        return isEq;
    }

    @Override
    public int compareTo(Student o) {
        return this.name.compareToIgnoreCase(o.name);
    }
}
