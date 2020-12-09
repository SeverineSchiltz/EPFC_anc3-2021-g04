package model;

import java.util.Set;
import java.util.TreeSet;

public class School {

    private static final Set<Course> classes = new TreeSet<>(); // = subscriptions
    private static final Set<Student> students = new TreeSet<>();


    /*public static Set<Student> getStudents() {
        return (Set<Student>) Collections.unmodifiableSet(students);
    }*/

    public static Set<String> getStudentsNames(){
        Set<String> studentsNames = new TreeSet<>();
        for (Student s : students){
            studentsNames.add(s.getName());
        }
        return studentsNames;
    }



    public void addStudent(Student student){
        students.add(student);
    }

    public void addCourse(Course course){
        classes.add(course);
    }

    public Set<String> getClassesKeys(){
        Set<String> classesNames = new TreeSet<>();
        for (Course c : classes){
            classesNames.add(c.getName());
        }
        return classesNames;
    }

    public static boolean studentAttendsClass(String c, String s){
        //return findCourse(c).isFollowedBy(findStudent(s));
        boolean res = false;
        Course cCible = findCourse(c);
        Student sCible = findStudent(s);
        if (cCible != null && sCible != null){
            res = cCible.isFollowedBy(sCible);
        }
        return res;
    }

    private static Course findCourse(String courseName) {
        Course c2 = new Course(courseName);
        for (Course c : classes) {
            if (c.equals(c2)) {
                return c;
            }
        }
        return null;
    }

    private static Student findStudent(String studentName) {
        Student s2 = new Student(studentName);
        for (Student s : students) {
            if (s.equals(s2)) {
                return s;
            }
        }
        return null;
    }

    public static boolean isClassComplete(String c){
        //return findCourse(c).isFull();
        boolean res = false;
        Course cCible = findCourse(c);
        if (cCible != null){
            res = cCible.isFull();
        }
        return res;
    }

    public static boolean isStudentComplete(String s){
        //return findStudent(s).isFull();
        boolean res = false;
        Student sCible = findStudent(s);
        if (sCible != null){
            res = sCible.isFull();
        }
        return res;
    }

    public static void addStudentToclass(String student, String course){
        Course c=findCourse(course);
        Student s=findStudent(student);
        if (c != null && s != null){
            if (!c.isFull() && !s.isFull()) {
                c.addStudent(s);
            }
        }
    }

    public static void removeStudentFromClass(String student, String course){
        Course c=findCourse(course);
        Student s=findStudent(student);
        if (c != null && s != null){
            if (c.isFollowedBy(s)) {
                s.removeCourse();
                c.removeStudent(s);
            }
        }
    }

    public static Set<String> getStudentsFromClass(String course){
        Set<String> studentsNames = new TreeSet<>();
        Course c = findCourse(course);
        if (c != null){
            for (Student s : c.getStudents()) {
                studentsNames.add(s.getName());
            }
        }
        return studentsNames;
    }

    public static boolean isStudent (String s){
        return findStudent(s) != null;
    }
}
