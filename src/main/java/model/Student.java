package model;

public class Student implements Comparable<Student> {
    private static final int MAX_COURSES_PER_STUDENT = 2;
    private String name;
    private int nbCourses;

    public Student(String name){
        this.name = name;
        this.nbCourses = 0;
    }

    /*public int getNbCourses(){
        return nbCourses;
    }*/

    public String getName(){
        return this.name;
    }

    public boolean isFull(){
        return this.nbCourses >= MAX_COURSES_PER_STUDENT;
    }

    public boolean addCourse(){
        boolean res = false;
        if (!this.isFull()){
            this.nbCourses++;
            res = true;
        }
        return res;
    }

    public boolean removeCourse(){
        boolean res = false;
        if (this.nbCourses > 0){
            this.nbCourses--;
            res = true;
        }
        return res;
    }

    @Override
    public int compareTo(Student o) {
        return this.name.compareToIgnoreCase(o.name);
    }

    @Override
    public boolean equals(Object obj) {
        boolean res = false;
        if (obj instanceof Student) {
            Student other = (Student) obj;
            if (other.name.equals(this.name)) {
                res = true;
            }
        }
        return res;
    }
}
