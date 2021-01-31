package view;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import mvvm.ColumnViewModel;
import model.*;

public class ColumnView extends VBox {

    private final ColumnViewModel cvm;
    private EditableLabel title;
    private final ListView<Card> cards = new ListView<>();

    public ColumnView(ColumnViewModel viewModel){
        this.cvm = viewModel;
        configDataBindings();
        this.getChildren().addAll(title.getLabel(), cards);
        customizeCards();
        setID();
    }

    private void setID(){
        this.setId("column");
        this.title.getLabel().setId("columnTitle");
    }

    private void configDataBindings() {
        title = new EditableLabel(cvm.getColumnTitleProperty());
        cards.itemsProperty().bind(cvm.cardsProperty());
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
