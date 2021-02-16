package view;

import javafx.event.EventHandler;
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
        this.bp.setLeft(btLeft);
        this.bp.setRight(btRight);
        this.bp.setCenter(title.getLabel());
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
        this.title.getLabel().setId("columnTitle");
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
        cards.setCellFactory(view -> new ListCell<>() {
            @Override
            protected void updateItem(Card card, boolean b) {
                super.updateItem(card, b);
                CardView cardView = null;
                if (card != null) {
                    cardView = new CardView(card);
                }
                setGraphic(cardView);
            }
        });

        // Add a card after a double click on the mouse (left button)
        cards.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2) {
                    //System.out.println("testAddCard");
                    cvm.addCard();
                }
            }
        });

        title.getLabel().setTooltip(new Tooltip("Supprimer"));
        title.getLabel().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Confirmation");
                    alert.setContentText("Are you ok to delete this?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        cvm.delete();
                    }
                }
            }
        });
    }

}
