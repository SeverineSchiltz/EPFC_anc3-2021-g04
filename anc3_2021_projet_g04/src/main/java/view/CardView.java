package view;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.Card;
import mvvm.CardViewModel;

import java.util.Optional;

public class CardView extends BorderPane {

    private final CardViewModel cardvm;
    private EditableLabel title;
    // TODO: to check if we can remove HBoxes and keep the arrows in the center (maybe in trello.css)
    // 2 HBoxes (up & down) where we store the Buttons (btUp & btDown)
    private final Button btUp = new Button();
    private final HBox hbUp = new HBox(btUp);
    private final Button btRight = new Button();
    private final Button btDown = new Button();
    private final HBox hbDown = new HBox(btDown);
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
        this.setCenter(title.getLabel());
        // set HBoxes in the center
        this.hbUp.setAlignment(Pos.BASELINE_CENTER);
        this.hbDown.setAlignment(Pos.BASELINE_CENTER);
        // set nodes in the border pane
        this.setTop(hbUp);
        this.setRight(btRight);
        this.setBottom(hbDown);
        this.setLeft(btLeft);
        this.getChildren().addAll();
    }

    private void configBindings() {
        //configDataBindings();
        configDisabledBindings();
    }

    // TODO: we may have to remove this method
    private void configDataBindings() {

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

        // Delete a card after a click on the mouse (right button)
        //TODO: check this is the right place
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.SECONDARY && mouseEvent.getClickCount() == 1) {
                    //System.out.println("testRemoveCard");
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Delete");
                    Alert alertDeleteConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                    //alertDeleteConfirm.setTitle("Confirmation"); //not necessary
                    alertDeleteConfirm.setHeaderText("Confirmation");
                    alertDeleteConfirm.setContentText("Are you sure to delete card \"" + title + "\" ?");
                    Optional<ButtonType> result = alertDeleteConfirm.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        cardvm.removeCard();
                    }
                }
            }
        });

    }

    private void setID(){
        this.setId("card");
        this.title.getLabel().setId("cardTitle");
        this.hbUp.setId("hb_card");
        this.hbDown.setId("hb_card");
        this.btUp.setId("bt_card_up");
        this.btRight.setId("bt_card_right");
        this.btDown.setId("bt_card_down");
        this.btLeft.setId("bt_card_left");
    }


}
