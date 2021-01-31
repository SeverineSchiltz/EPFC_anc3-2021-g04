package mvvm;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Card;

public class CardViewModel {
    private final Card card;
    private final StringProperty title = new SimpleStringProperty("");

    public CardViewModel(Card card){
        this.card = card;
        title.setValue(this.card.getTitle());
    }

    public StringProperty getCardTitleProperty(){
        return title;
    }
}
