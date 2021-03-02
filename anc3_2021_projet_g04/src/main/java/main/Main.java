package main;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Trello;
import mvvm.*;
import mvvm.commands.CommandManager;
import view.*;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        TrelloViewModel viewModel = new TrelloViewModel(new Trello(), new CommandManager());
        TrelloView view = new TrelloView(primaryStage, viewModel);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
