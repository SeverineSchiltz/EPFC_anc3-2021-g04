package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Trello;
import mvvm.*;
import view.*;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        TrelloViewModel viewModel = new TrelloViewModel(new Trello());
        TrelloView view = new TrelloView(primaryStage, viewModel);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
