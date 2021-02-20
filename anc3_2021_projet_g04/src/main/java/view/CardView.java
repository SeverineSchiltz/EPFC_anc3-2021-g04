package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import model.Card;
import mvvm.CardViewModel;

import java.util.Optional;

public class CardView extends BorderPane {

    private final CardViewModel cardvm;
    private EditableLabel title;
    private final Button btUp = new Button();
    private final Button btRight = new Button();
    private final Button btDown = new Button();
    private final Button btLeft = new Button();

    public CardView(CardViewModel cardViewModel) {
        this.cardvm = cardViewModel;
        title = new EditableLabel(cardvm.getCardTitleProperty());
        config();
        configBindings();
        configButtons();
        setID();
    }

    public CardView(Card card){
        this(new CardViewModel(card));
    }

    private void config(){
        this.title = new EditableLabel(cardvm.getCardTitleProperty());
        this.setCenter(title);
        title.setAlignment(Pos.CENTER);
        this.setAlignment(btUp, Pos.CENTER);
        this.setAlignment(btDown, Pos.CENTER);
        this.setAlignment(btRight, Pos.CENTER);
        this.setAlignment(btLeft, Pos.CENTER);

        this.setTop(btUp);
        this.setRight(btRight);
        this.setBottom(btDown);
        this.setLeft(btLeft);

    }

    private void configBindings() {
        configDisabledBindings();
    }

    private void configDisabledBindings() {
        // if first col, no btLeft
        btLeft.disableProperty().bind(cardvm.isInFirstColumnProperty());
        // if last col, no btRight
        btRight.disableProperty().bind(cardvm.isInLastColumnProperty());
        // if first card in a column, no btUp
        btUp.disableProperty().bind(cardvm.isFirstInColumnProperty());
        // if last card in a column, no btDown
        btDown.disableProperty().bind(cardvm.isLastInColumnProperty());
    }

    /*
    4 options:
    (1) up      posCard: -1     posColumn:  0
    (2) right   posCard:  0     posColumn: +1
    (3) down    posCard: +1     posColumn:  0
    (4) left    posCard:  0     posColumn: -1
     */
    private void configButtons(){
        this.btUp.setOnAction(e -> cardvm.changePosition(-1, 0));
        this.btRight.setOnAction(e -> cardvm.changePosition(0, 1));
        this.btDown.setOnAction(e -> cardvm.changePosition(1, 0));
        this.btLeft.setOnAction(e -> cardvm.changePosition(0, -1));

        // This is to delete a card
        ContextMenu contextMenu = new ContextMenu();
        MenuItem sup = new MenuItem("Supprimer");
        sup.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Delete");
            Alert alertDeleteConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            alertDeleteConfirm.setTitle("Confirmation");
            alertDeleteConfirm.setHeaderText("Confirmation");
            alertDeleteConfirm.setContentText("Are you sure to delete card \"" + title.getTitle() + "\" ?");
            Optional<ButtonType> result = alertDeleteConfirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                cardvm.removeCard();
            }
        });
        contextMenu.getItems().addAll(sup);
        this.setOnContextMenuRequested(contextMenuEvent ->
                contextMenu.show(this, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY()));
    }

    private void setID(){
        this.setId("card");
        this.title.setId("cardTitle");
        this.btUp.setId("bt_card_up");
        this.btRight.setId("bt_card_right");
        this.btDown.setId("bt_card_down");
        this.btLeft.setId("bt_card_left");
    }


}
