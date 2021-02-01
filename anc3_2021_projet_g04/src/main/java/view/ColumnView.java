package view;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mvvm.ColumnViewModel;
import model.*;

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
    }

}
