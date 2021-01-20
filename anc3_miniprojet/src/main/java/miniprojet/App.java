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
import model.Student;

/**
 * JavaFX App
 */
public class App extends Application {

    // Référence vers le modèle (façade)
    private final School school = new School();

    public static void main(String[] args) {
        launch();
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
    private final ListView<Course> lvCourses = new ListView<>();
    private final ListView<Student> lvStudents = new ListView<>();

    private final Label lbCourses = new Label("Cours"),
            lbStudents = new Label("Etudiants"),
            lbNewSubscription = new Label("Nouvelle inscription");
    private final ComboBox<Student> cbStudents = new ComboBox<>();
    private final Button btSubscribe = new Button("Inscrire"),
            btNewStudent = new Button("Ajouter et inscrire"),
            btUnsubscribe = new Button("Désinscrire étudiant");
    private final TextField tfNewStudent = new TextField();

    // Etat de la vue
    private Course getSelectedCourse() {
        return lvCourses.getSelectionModel().getSelectedItem();
    }

    private Student getSelectedCourseStudent() {
        return lvStudents.getSelectionModel().getSelectedItem();
    }

    private Student getSelectedStudent() {
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
        lvCourses.getItems().addAll(school.getCourses());
        makeCbStudents();
    }

    private void makeCbStudents() {
        cbStudents.getItems().clear();
        cbStudents.getItems().addAll(school.getStudents());
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
        Course c = getSelectedCourse();
        Student s = getSelectedStudent();
        return (c == null || s == null || !school.studentCanBeAdded(s, c));
    }

    private boolean btNewStudentHasToBeDisabled() {
        Course c = getSelectedCourse();
        if (c == null
                || tfNewStudent.getText().isEmpty()
                || tfNewStudent.getText().isBlank()
                || school.isCourseComplete(c)) return true;
        return school.existsStudent(new Student(tfNewStudent.getText()));
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
            if (school.addStudentToCourse(getSelectedStudent(), getSelectedCourse()))
                updateCourseStudents();
        });
        btUnsubscribe.setOnAction(e -> {
            if (school.removeStudentFromCourse(getSelectedCourseStudent(), getSelectedCourse()))
                updateCourseStudents();
        });
        btNewStudent.setOnAction(e -> createStudentAndAddToCourse());
    }

    private void createStudentAndAddToCourse() {
        Student student = new Student(tfNewStudent.getText());
        if (school.createStudentAndAddToCourse(student, getSelectedCourse())) {
            makeCbStudents();
            lvStudentsUpdate();
            tfNewStudent.clear();
            configDisabledButtons();
        }
    }

    private void configTextField() {
        tfNewStudent.setOnKeyTyped(keyEvent ->
                btNewStudent.setDisable(btNewStudentHasToBeDisabled()));
        tfNewStudent.setOnAction(ae -> {
                createStudentAndAddToCourse();
        });
    }

    private void lvStudentsUpdate() {
        lvStudents.getItems().clear();
        Course c = getSelectedCourse();
        if (c != null) {
            lvStudents.getItems().addAll(c.getStudents());
        }
    }

}