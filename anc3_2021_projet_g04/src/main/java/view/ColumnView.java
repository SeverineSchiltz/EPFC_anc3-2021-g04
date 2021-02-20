package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mvvm.ColumnViewModel;
import model.*;

import java.util.Optional;

public class ColumnView extends VBox {

    private final ColumnViewModel cvm;
    private final BorderPane bp = new BorderPane();
    private final Button btLeft = new Button();
    private final Button btRight = new Button();
    private EditableLabel title;
    private final ListView<Card> cards = new ListView<>();

    public ColumnView(ColumnViewModel viewModel){
        this.cvm = viewModel;
        config();
        configDataBindings();
        customizeCards();
        configButtons();
        setID();
    }

    private void config(){
        title = new EditableLabel(cvm.getColumnTitleProperty());
        title.setAlignment(Pos.CENTER);
        this.bp.setLeft(btLeft);
        this.bp.setRight(btRight);
        this.bp.setCenter(title);
        bp.setAlignment(btLeft, Pos.CENTER);
        bp.setAlignment(btRight, Pos.CENTER);
        this.getChildren().addAll(bp, cards);
    }

    private void configButtons(){
        this.btLeft.setOnAction(e -> cvm.changePosition(-1));
        this.btRight.setOnAction(e -> cvm.changePosition(1));
    }

    private void setID(){
        this.setId("column");
        this.cards.setId("column_font");
        this.bp.setId("hb_column");
        this.title.setId("columnTitle");
        this.btLeft.setId("bt_column_left");
        this.btRight.setId("bt_column_right");
    }

    private void configDataBindings() {
        cards.itemsProperty().bind(cvm.cardsProperty());
        btLeft.disableProperty().bind(cvm.isFirstInBoardProperty());
        btRight.disableProperty().bind(cvm.isLastInBoardProperty());
    }

    public ColumnView(Column column){
        this(new ColumnViewModel(column));
    }

    private void customizeCards() {
        cards.setCellFactory(lv -> {
            ListCell<Card> cell = new ListCell<>() {
                @Override
                protected void updateItem(Card card, boolean b) {
                    super.updateItem(card, b);
                    CardView cardView = null;
                    if (card != null) {
                        cardView = new CardView(card);
                    }
                    setGraphic(cardView);
                }
            };
            cell.setOnMouseClicked(e -> {
                if(cell.isEmpty() && e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2){
                    cvm.addCard();
                }
            });
            return cell;
        });

        cards.setOnMouseClicked(e -> {
            if (cards.getItems().isEmpty() && e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2 ) {
                cvm.addCard();
            }
        });

        // This is to delete a column
        ContextMenu contextMenu = new ContextMenu();
        MenuItem sup = new MenuItem("Supprimer");
        sup.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Confirmation");
            alert.setContentText("Are you sure to delete column \"" + title.getTitle() + "\" ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                cvm.delete();
            }
        });
        contextMenu.getItems().addAll(sup);
        bp.setOnContextMenuRequested(contextMenuEvent ->
                contextMenu.show(bp, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY()));

    }
}
