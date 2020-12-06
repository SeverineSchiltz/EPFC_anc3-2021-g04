package model;


import java.util.*;

/*
Créer des classes qui représentent votre modèle métier, dans lesquelles vous placez
- les données métier,
- la gestions de ces données et
- les règles métier associées.
 */

public class Course {

    // Règles métiers
    private static final int MAX_STUDENTS_PER_COURSE = 5;
    private static final int MAX_COURSES_PER_STUDENT = 2;

    //Initialisation données
    private static final Map<String, Set<String>> subscriptions = new TreeMap<>();


    public Course() {
        initData(); // initialise les entrées
    }

    private void initData() {
        Set<String> anc3_subscriptions = new TreeSet<>(),
                prwb_subscriptions = new TreeSet<>(),
                pro2_subscriptions = new TreeSet<>();

        addStudent("anc3_subscriptions", "Delphine");
        addStudent("anc3_subscriptions", "Amélie");
        addStudent("anc3_subscriptions", "Bernard");

        addStudent("pro2_subscriptions", "Mohamed");
        addStudent("pro2_subscriptions", "Caroline");
        addStudent("pro2_subscriptions", "Delphine");
        addStudent("pro2_subscriptions", "Bernard");
        addStudent("pro2_subscriptions", "Eddy");

        addStudent("prwb_subscriptions", "Amélie");
        addStudent("prwb_subscriptions", "Eddy");
        addStudent("prwb_subscriptions", "Caroline");

        subscriptions.put("PRO2", pro2_subscriptions);
        subscriptions.put("PRWB", prwb_subscriptions);
        subscriptions.put("ANC3", anc3_subscriptions);

//        anc3_subscriptions.add("Delphine");
//        anc3_subscriptions.add("Amélie");
//        anc3_subscriptions.add("Bernard");
//
//        pro2_subscriptions.add("Mohamed");
//        pro2_subscriptions.add("Caroline");
//        pro2_subscriptions.add("Delphine");
//        pro2_subscriptions.add("Bernard");
//        pro2_subscriptions.add("Eddy");
//
//        prwb_subscriptions.add("Amélie");
//        prwb_subscriptions.add("Eddy");
//        prwb_subscriptions.add("Caroline");


    }

    // getter qui retourne la map
    public Map<String, Set<String>> getSubscriptions() {
        return Collections.unmodifiableMap(subscriptions);
    }

    public boolean addStudent(String course, String student) {
        if (acceptableStudent(course, student)) {
            getSubscriptions().get(course).add(student);
            return true;
        }
        return false;
    }

    // règles métiers

    private boolean acceptableStudent(String course, String student) {
        return (!isCourseComplete(course) && !isStudentComplete(student));
    }

    public int nbStudentsByCourse(Map<String, Set<String>> map, String codeCourse) {
        if (map.containsKey(codeCourse))
            return map.get(codeCourse).size();
        else
            return 0;
    }

    public boolean isCourseComplete(String codeCourse) {
        return nbStudentsByCourse(getSubscriptions(), codeCourse) >= MAX_STUDENTS_PER_COURSE;
    }

    public boolean isStudentComplete(String student) {
        int nbcours = 0;
        for (Set<String> studentsOfCourse : getSubscriptions().values()) {
            if (studentsOfCourse.contains(student)) ++nbcours;
        }
        return nbcours >= MAX_COURSES_PER_STUDENT;
    }

}
