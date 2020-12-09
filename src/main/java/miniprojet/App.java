package miniprojet;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

import java.util.*;

import static model.School.*;

/**
 * JavaFX App
 */
public class App extends Application {

    public static void main(String[] args) {
        initData();
        launch();
    }

    //Initialisation données

    //private static final Map<String, Set<String>> subscriptions = new TreeMap<>();
    //private static final Set<String> students = new TreeSet<>();

    private static final School school = new School();

    private static void initData() {
        Student delphine = new Student("Delphine"); //permet de réutiliser les students après
        Student caroline = new Student("Caroline");
        Student eddy = new Student("Eddy");
        Student mohamed = new Student("Mohamed");
        Student bernard = new Student("Bernard");
        Student amelie = new Student("Amélie");

        school.addStudent(delphine);
        school.addStudent(caroline);
        school.addStudent(eddy);
        school.addStudent(mohamed);
        school.addStudent(bernard);
        school.addStudent(amelie);

        /*students.add("Delphine"); //reliquat à effacer
        students.add("Caroline");
        students.add("Eddy");
        students.add("Mohamed");
        students.add("Bernard");
        students.add("Amélie");*/

        Set<Student> anc3_subscriptions = new TreeSet<>(),
                prwb_subscriptions = new TreeSet<>(),
                pro2_subscriptions = new TreeSet<>();
        anc3_subscriptions.add(delphine);
        anc3_subscriptions.add(amelie);
        anc3_subscriptions.add(bernard);

        pro2_subscriptions.add(mohamed);
        pro2_subscriptions.add(caroline);
        pro2_subscriptions.add(delphine);
        pro2_subscriptions.add(bernard);
        pro2_subscriptions.add(eddy);

        prwb_subscriptions.add(amelie);
        prwb_subscriptions.add(eddy);
        prwb_subscriptions.add(caroline);

        school.addCourse(new Course("ANC3",anc3_subscriptions));
        school.addCourse(new Course("PRWB",prwb_subscriptions));
        school.addCourse(new Course("PRO2",pro2_subscriptions));

        /*subscriptions.put("PRO2", pro2_subscriptions);    //reliquat à effacer
        subscriptions.put("PRWB", prwb_subscriptions);
        subscriptions.put("ANC3", anc3_subscriptions);*/
    }

    // Règles métiers
    /*private static final int MAX_STUDENTS_PER_COURSE = 5;  //reliquat à effacer
    private static final int MAX_COURSES_PER_STUDENT = 2;*/


    // Utilitaires graphiques
    private static final double SPACING = 10;

    private static void spacing(Pane pane) {
        if (pane instanceof HBox) {
            HBox b = (HBox) pane;
            b.setSpacing(SPACING);
        } else if (pane instanceof VBox) {
            VBox b = (VBox) pane;
            b.setSpacing(SPACING);
        }
    }

    private static void padding(Pane pane) {
        pane.setPadding(new Insets(SPACING));
    }

    private static void paddingAndSpacing(Pane pane) {
        padding(pane);
        spacing(pane);
    }

    // Composants graphiques
    private final HBox hbMainPanel = new HBox(),
            hbNewSubscription = new HBox(),
            hbNewStudent = new HBox();
    private final VBox vbCourses = new VBox(),
            vbStudents = new VBox(),
            vbSubscriptions = new VBox();
    private final BorderPane bpSubscribe = new BorderPane();
    private final ListView<String> lvCourses = new ListView<>();
    private final ListView<String> lvStudents = new ListView<>();

    private final Label lbCourses = new Label("Cours"),
            lbStudents = new Label("Etudiants"),
            lbNewSubscription = new Label("Nouvelle inscription");
    private final ComboBox<String> cbStudents = new ComboBox<>();
    private final Button btSubscribe = new Button("Inscrire"),
            btNewStudent = new Button("Ajouter et inscrire"),
            btUnsubscribe = new Button("Désinscrire étudiant");
    private final TextField tfNewStudent = new TextField();

    // Etat de la vue
    private String getSelectedCourse() {
        return lvCourses.getSelectionModel().getSelectedItem();
    }

    private String getSelectedCourseStudent() {
        return lvStudents.getSelectionModel().getSelectedItem();
    }

    private String getSelectedStudent() {
        return cbStudents.getSelectionModel().getSelectedItem();
    }

    // Lancement de l'application
    @Override
    public void start(Stage primaryStage) {
        configModel();
        configComponents();
        Scene scene = new Scene(hbMainPanel);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestion des inscriptions");
        primaryStage.show();
    }

    private void configModel() {
        //lvCourses.getItems().addAll(subscriptions.keySet());
        lvCourses.getItems().addAll(school.getClassesKeys());// subscriptions.keySet());
        makeCbStudents();
    }

    private void makeCbStudents() {
        cbStudents.getItems().clear();
        cbStudents.getItems().addAll(getStudentsNames());
    }

    private void configComponents() {
        makeComponentsHierarchy();
        componentsDecoration();
        configHandlers();
        configDisabledButtons();
    }

    private void configDisabledButtons() {
        btSubscribe.setDisable(btSubscribeHasToBeDisabled());
        btUnsubscribe.setDisable(getSelectedCourseStudent() == null);
        btNewStudent.setDisable(btNewStudentHasToBeDisabled());
    }

    private boolean btSubscribeHasToBeDisabled() {
        String c = getSelectedCourse(), s = getSelectedStudent();
        if (c == null || s == null
                //|| isCourseComplete()
                || isClassComplete(c)
                //|| isStudentComplete(s)
                || isStudentComplete(s))
            return true;
        //return subscriptions.get(c).contains(s);
        return studentAttendsClass(c,s);
    }

    private boolean btNewStudentHasToBeDisabled() {
        if (getSelectedCourse() == null
                || tfNewStudent.getText().isEmpty()
                || tfNewStudent.getText().isBlank()
                //|| isCourseComplete()
                || isClassComplete(getSelectedCourse()))
            return true;
        //return students.contains(tfNewStudent.getText());
        return isStudent(tfNewStudent.getText());
    }

    /*private boolean isCourseComplete() {  => Course.isFull
        return subscriptions.get(getSelectedCourse()).size() >= MAX_STUDENTS_PER_COURSE;
    }*/

    /*private boolean isStudentComplete(String student) { => Student.isFull
        int nbcours = 0;
        for (Set<String> studentsOfCourse : subscriptions.values()) {
            if (studentsOfCourse.contains(student)) ++nbcours;
        }
        return nbcours >= MAX_COURSES_PER_STUDENT;
    }*/

    private void makeComponentsHierarchy() {
        hbMainPanel.getChildren().addAll(vbCourses, vbStudents, bpSubscribe);
        vbCourses.getChildren().addAll(lbCourses, lvCourses);
        vbStudents.getChildren().addAll(lbStudents, lvStudents);
        hbNewSubscription.getChildren().addAll(cbStudents, btSubscribe);
        hbNewStudent.getChildren().addAll(tfNewStudent, btNewStudent);
        vbSubscriptions.getChildren().addAll(lbNewSubscription, hbNewSubscription, hbNewStudent);
        bpSubscribe.setTop(vbSubscriptions);
        bpSubscribe.setBottom(btUnsubscribe);
    }

    private void componentsDecoration() {
        paddingAndSpacing(hbMainPanel);
        paddingAndSpacing(vbCourses);
        paddingAndSpacing(vbStudents);
        padding(bpSubscribe);
        spacing(vbSubscriptions);
        spacing(hbNewSubscription);
        spacing(hbNewStudent);
    }

    private void configHandlers() {
        configSelectionHandlers();
        configActions();
        configTextField();
    }

    private void configSelectionHandlers() {
        lvCourses.getSelectionModel().selectedIndexProperty()
                .addListener(o -> updateCourseStudents());
        lvStudents.getSelectionModel().selectedIndexProperty()
                .addListener(o -> btUnsubscribe.setDisable(getSelectedCourseStudent() == null));
        cbStudents.getSelectionModel().selectedIndexProperty()
                .addListener(o -> configDisabledButtons());
    }

    private void updateCourseStudents() {
        lvStudentsUpdate();
        configDisabledButtons();
    }

    private void configActions() {
        btSubscribe.setOnAction(e -> {
            //subscriptions.get(getSelectedCourse()).add(getSelectedStudent());
            addStudentToclass(getSelectedStudent(),getSelectedCourse());
            updateCourseStudents();
        });
        btUnsubscribe.setOnAction(e -> {
            //subscriptions.get(getSelectedCourse()).remove(getSelectedCourseStudent());
            removeStudentFromClass(getSelectedCourseStudent(),getSelectedCourse());// /!\getSelectedCourseStudent
            updateCourseStudents();
        });
        btNewStudent.setOnAction(e -> createStudentAndAddToCourse());
    }

    private void createStudentAndAddToCourse() {
        //String student = tfNewStudent.getText();
        //students.add(student);
        Student student = new Student (tfNewStudent.getText());
        school.addStudent(student);
        makeCbStudents();
        //subscriptions.get(getSelectedCourse()).add(student);
        addStudentToclass(tfNewStudent.getText(),getSelectedCourse());
        updateCourseStudents();
        tfNewStudent.clear();
    }

    private void configTextField() {
        tfNewStudent.setOnKeyTyped(keyEvent ->
                btNewStudent.setDisable(btNewStudentHasToBeDisabled()));
        tfNewStudent.setOnAction(ae -> {
            if (!btNewStudent.isDisable())
                createStudentAndAddToCourse();
        });
    }

    private void lvStudentsUpdate() {
        lvStudents.getItems().clear();
        String c = getSelectedCourse();
        if (c != null) {
            //lvStudents.getItems().addAll(subscriptions.get(c)); //renvoie un Set<String>
            //                        school.getStudentsFromClass(c)
            lvStudents.getItems().addAll(getStudentsFromClass(c));
        }
    }

}