package view;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import model.Card;
import mvvm.CardViewModel;
import mvvm.commands.CommandManager;
import java.util.Optional;

public class CardView extends BorderPane {

    private final CardViewModel cardvm;
    private final EditableLabel title;
    private final Button btUp = new Button();
    private final Button btRight = new Button();
    private final Button btDown = new Button();
    private final Button btLeft = new Button();

    public CardView(CardViewModel cardViewModel) {
        this.cardvm = cardViewModel;
        title = new EditableLabel(cardvm.getCardTitleProperty(), cardvm);
        config();
        configBindings();
        configButtons();
        setID();
    }

    public CardView(Card card, CommandManager cmdManager){
        this(new CardViewModel(card, cmdManager));
    }

    private void config(){
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
        btLeft.disableProperty().bind(cardvm.isLeftDisabledProperty());
        btRight.disableProperty().bind(cardvm.isRightDisabledProperty());
        btUp.disableProperty().bind(cardvm.isUpDisabledProperty());
        btDown.disableProperty().bind(cardvm.isDownDisabledProperty());
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
                cardvm.delete();
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
