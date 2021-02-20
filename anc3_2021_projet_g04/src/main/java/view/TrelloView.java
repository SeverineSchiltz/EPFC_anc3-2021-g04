package view;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mvvm.*;

public class TrelloView extends VBox {

    private TrelloViewModel tvm;
    private EditableLabel boardTitle;
    private BoardView boardView;


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
        this.getStylesheets().add("/trello.css");
    }

    private void addComponent(){
        boardTitle = new EditableLabel(tvm.getBoardTitleProperty());
        boardView = new BoardView(tvm.getBoardVM());
        this.getChildren().addAll(boardTitle, boardView);
    }

    private void configBindings(Stage primaryStage) {
        primaryStage.titleProperty().bind(tvm.getTrelloTitleProperty());
    }


}
