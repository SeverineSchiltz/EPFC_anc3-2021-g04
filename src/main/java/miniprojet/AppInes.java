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
import model.School;
import model.Text;

import java.util.*;

/**
 * JavaFX App
 */

public class AppInes extends Application {

    public static void main(String[] args) {
        //initData();
        launch();
    }

    //Initialisation données
    private static final Map<String, Set<String>> subscriptions = new TreeMap<>();
    private static final Set<String> students = new TreeSet<>();
    // on ajoute cet attribut
    private final School school;

    // maintenant
    public AppInes() {
        school = new School();
        //lvLines.getItems().setAll(text.getLines());
        //lbNbLines.setText("Nombre de lignes : " + text.nbLines());
    }

    // Règles métiers
    private static final int MAX_STUDENTS_PER_COURSE = 5;
    private static final int MAX_COURSES_PER_STUDENT = 2;

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
        lvCourses.getItems().addAll(subscriptions.keySet());
        makeCbStudents();
    }

    private void makeCbStudents() {
        cbStudents.getItems().clear();
        cbStudents.getItems().addAll(students);
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
                || school.isCourseComplete(c)
                || school.course.isStudentComplete(s))
            return true;
        return subscriptions.get(c).contains(s);
    }

    private boolean btNewStudentHasToBeDisabled() {
        if (getSelectedCourse() == null
                || tfNewStudent.getText().isEmpty()
                || tfNewStudent.getText().isBlank()
                || isCourseComplete(c)) return true;
        return students.contains(tfNewStudent.getText());
    }

//    private boolean isCourseComplete() {
//        return subscriptions.get(getSelectedCourse()).size() >= MAX_STUDENTS_PER_COURSE;
//    }
//
//    private boolean isStudentComplete(String student) {
//        int nbcours = 0;
//        for (Set<String> studentsOfCourse : subscriptions.values()) {
//            if (studentsOfCourse.contains(student)) ++nbcours;
//        }
//        return nbcours >= MAX_COURSES_PER_STUDENT;
//    }

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
            subscriptions.get(getSelectedCourse()).add(getSelectedStudent());
            updateCourseStudents();
        });
        btUnsubscribe.setOnAction(e -> {
            subscriptions.get(getSelectedCourse()).remove(getSelectedCourseStudent());
            updateCourseStudents();
        });
        btNewStudent.setOnAction(e -> createStudentAndAddToCourse());
    }

    private void createStudentAndAddToCourse() {
        String student = tfNewStudent.getText();
        students.add(student);
        makeCbStudents();
        subscriptions.get(getSelectedCourse()).add(student);
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
            lvStudents.getItems().addAll(subscriptions.get(c));
        }
    }

}
