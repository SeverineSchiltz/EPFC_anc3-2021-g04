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

public class Student {

    //Initialisation données
    private static final Set<String> students = new TreeSet<>();

    public Student() {
        initData(); // initialise les entrées
    }

    private void initData() {
        addStudent("Delphine");
        addStudent("Caroline");
        addStudent("Eddy");
        addStudent("Mohamed");
        addStudent("Bernard");
        addStudent("Amélie");
    }

    // getter qui retourne la liste "lines" avec 1 él intéressant
    public Set<String> getStudents() {
        return Collections.unmodifiableSet(students);
        // ce n'est que pour afficher donc vue de la liste non modifiable
    }

    // taille du set
    public int nbStudents() {
        return getStudents().size();
    }

    // retourne un student stocké àpd une position
    // pas possible car pas set n'est pas trié !
//    public String textLine(int numLineSelected) {
//        return students.get(numLineSelected);
//    }

    public boolean addStudent(String student) {
        return getStudents().add(student);
    }

    public String existingStudent(String student) {
        if(getStudents().contains(student))
            return student;
        else
            return null;
    }

}
