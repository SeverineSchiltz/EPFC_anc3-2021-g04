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

    private TrelloViewModel tvm;
    private EditableLabel boardTitle;
    private BoardView boardView;
    public MenuBar menuBar;


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
        // TODO: to check if necessary
        this.menuBar.setId("menuBar");
        this.getStylesheets().add("/trello.css");
    }

    private void addComponent(){
        // create a MenuBar
        this.menuBar = new MenuBar();
        // create Menus (to display on the MenuBar)
        Menu mnFile = new Menu("Fichier");
        Menu mnEdition = new Menu("Edition");
        // create MenuItems (to display on the Menus)
        MenuItem mniNewColumn = new MenuItem("Nouvelle colonne");
        MenuItem mniQuit = new MenuItem("Quitter");
        MenuItem mniUndo = new MenuItem("Annuler");
        MenuItem mniRedo = new MenuItem("Refaire");
        // add MenuItems to the Menus
        mnFile.getItems().addAll(mniNewColumn, mniQuit);
        mnEdition.getItems().addAll(mniUndo, mniRedo);
//        // TODO: only to check so likely to remove
//        mniUndo.setDisable(true);
//        mniRedo.setDisable(true);
        // add Menus to the MenuBar
        menuBar.getMenus().addAll(mnFile, mnEdition);

        // TODO: this is not the right place
        // when user enters "ctrl+x" to close which is the accelerator for "Quitter" MenuItem
        mniQuit.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        // when user enters "ctrl+z" to undo which is the accelerator for "Annuler" MenuItem
        mniUndo.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
        // when user enters "ctrl+y" to redo which is the accelerator for "Refaire" MenuItem
        mniRedo.setAccelerator(KeyCombination.keyCombination("Ctrl+Y"));

        // when user clicks on "Quitter" MenuItem
        mniQuit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        // when user clicks on "Nouvelle colonne" MenuItem
        mniNewColumn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO: this does not respect the Demeter law
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
