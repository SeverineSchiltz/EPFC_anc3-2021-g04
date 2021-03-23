package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mvvm.*;

public class TrelloView extends VBox {

    private final TrelloViewModel tvm;
    private EditableLabel boardTitle;
    private BoardView boardView;
    private MenuBar menuBar;


    public TrelloView(Stage primaryStage, TrelloViewModel viewModel){
        this.tvm = viewModel;
        addComponent();
        config();
        configBindings(primaryStage);
        Scene scene = new Scene(this, 900, 500);
        primaryStage.setScene(scene);
    }

    private void config(){
        this.boardTitle.setId("boardTitle");
        this.boardView.setId("board");
        this.menuBar.setId("menuBar");
        this.getStylesheets().add("/trello.css");
    }

    private void addComponent(){
        this.menuBar = new MenuBar();
        Menu mnFile = new Menu("Fichier");
        Menu mnEdition = new Menu("Edition");
        MenuItem mniNewColumn = new MenuItem("Nouvelle colonne");
        MenuItem mniQuit = new MenuItem("Quitter");
        MenuItem mniUndo = new MenuItem("Annuler");
        MenuItem mniRedo = new MenuItem("Refaire");
        mnFile.getItems().addAll(mniNewColumn, mniQuit);
        mnEdition.getItems().addAll(mniUndo, mniRedo);
        menuBar.getMenus().addAll(mnFile, mnEdition);

        mniQuit.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        mniUndo.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
        mniRedo.setAccelerator(KeyCombination.keyCombination("Ctrl+Y"));

        mniQuit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        mniNewColumn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tvm.getBoardVM().addColumn();
            }
        });

        mniUndo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tvm.unexecuteCommand();
            }
        });

        mniUndo.disableProperty().bind(tvm.getCmdManager().isUndoNotPossible());
        mniRedo.disableProperty().bind(tvm.getCmdManager().isRedoNotPossible());

        mniUndo.textProperty().bind(tvm.getCmdManager().getTextLastCommand());
        mniRedo.textProperty().bind(tvm.getCmdManager().getTextNextCommand());

        mniRedo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tvm.executeCommand();
            }
        });

        boardTitle = new EditableLabel(tvm.getBoardTitleProperty(), tvm);
        boardView = new BoardView(tvm.getBoardVM());
        this.getChildren().addAll(menuBar, boardTitle, boardView);
    }

    private void configBindings(Stage primaryStage) {
        primaryStage.titleProperty().bind(tvm.getTrelloTitleProperty());
    }


}
