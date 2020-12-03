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
import model.Course;
import model.Student;
import model.School;

import java.util.*;

/**
 * JavaFX App
 */
public class App extends Application {

    public static void main(String[] args) {
        initData();
        launch();
    }

    //Initialisation données
    private static void initData() {
        Student delphine = new Student("Delphine");
        Student caroline = new Student("Caroline");
        Student eddy = new Student("Eddy");
        Student mohamed = new Student("Mohamed");
        Student bernard = new Student("Bernard");
        Student amelie = new Student("Amélie");

        Set<Student> students = new TreeSet<>();
        students.add(delphine);
        students.add(caroline);
        students.add(eddy);
        students.add(mohamed);
        students.add(bernard);
        students.add(amelie);
        School.setStudents(students);

        students = new TreeSet<>();
        students.add(delphine);
        students.add(amelie);
        students.add(bernard);
        Course anc3 = new Course("ANC3", students);

        students = new TreeSet<>();
        students.add(mohamed);
        students.add(caroline);
        students.add(delphine);
        students.add(bernard);
        students.add(eddy);
        Course pro2 = new Course("PRO2", students);

        students = new TreeSet<>();
        students.add(amelie);
        students.add(eddy);
        students.add(caroline);
        Course prwb = new Course("PRWB", students);

        Set<Course> subscriptions = new TreeSet<>();
        subscriptions.add(anc3);
        subscriptions.add(pro2);
        subscriptions.add(prwb);
        School.setSubscriptions(subscriptions);

        subscriptions = new TreeSet<>();
        subscriptions.add(anc3);
        subscriptions.add(pro2);
        delphine.setSubscriptions(subscriptions);

        subscriptions = new TreeSet<>();
        subscriptions.add(pro2);
        subscriptions.add(prwb);
        caroline.setSubscriptions(subscriptions);

        subscriptions = new TreeSet<>();
        subscriptions.add(pro2);
        subscriptions.add(prwb);
        eddy.setSubscriptions(subscriptions);

        subscriptions = new TreeSet<>();
        subscriptions.add(pro2);
        mohamed.setSubscriptions(subscriptions);

        subscriptions = new TreeSet<>();
        subscriptions.add(anc3);
        subscriptions.add(pro2);
        bernard.setSubscriptions(subscriptions);

        subscriptions = new TreeSet<>();
        subscriptions.add(anc3);
        subscriptions.add(prwb);
        amelie.setSubscriptions(subscriptions);

    }

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
        lvCourses.getItems().addAll(School.getSubscriptionsName());
        makeCbStudents();
    }

    private void makeCbStudents() {
        cbStudents.getItems().clear();
        cbStudents.getItems().addAll(School.getStudentName());
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
        String cs = getSelectedCourse(), ss = getSelectedStudent();
        if (cs == null || ss == null) {
            return true;
        } else {
            Course c = School.findCourse(cs);
            Student s = School.findStudent(ss);
            if (c == null || s == null || c.isCourseComplete() || s.isStudentComplete()) {
                return true;
            }
            return c.isFollowBy(s);
        }
    }

    private boolean btNewStudentHasToBeDisabled() {
        String cs = getSelectedCourse();
        if (getSelectedCourse() == null
                || tfNewStudent.getText().isEmpty()
                || tfNewStudent.getText().isBlank()) {
            return true;
        } else {
            Course c = School.findCourse(cs);
            if (c == null || c.isCourseComplete()) {
                return true;
            }
            Student s = School.findStudent(tfNewStudent.getText());
            if(s != null){
                return School.getStudents().contains(s);
            }
            return false;
        }
    }


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
            Course c = School.findCourse(getSelectedCourse());
            Student s = School.findStudent(getSelectedStudent());
            c.addStudent(s);
            s.addCourse(c);
            updateCourseStudents();
        });
        btUnsubscribe.setOnAction(e -> {
            Course c = School.findCourse(getSelectedCourse());
            Student s = School.findStudent(getSelectedCourseStudent());
            c.removeStudent(s);
            s.removeCourse(c);
            updateCourseStudents();
        });
        btNewStudent.setOnAction(e -> createStudentAndAddToCourse());
    }

    private void createStudentAndAddToCourse() {
        Student s = new Student(tfNewStudent.getText());
        School.getStudents().add(s);
        makeCbStudents();
        Course c = School.findCourse(getSelectedCourse());
        c.addStudent(s);
        s.addCourse(c);
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
        String cs = getSelectedCourse();
        Course c = School.findCourse(cs);
        if (c != null) {
            lvStudents.getItems().addAll(c.getStudentsName());
        }
    }

}