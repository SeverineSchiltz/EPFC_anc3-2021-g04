package mvvm;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Trello;

public class TrelloViewModel {
    private Trello trello;
    private final StringProperty title = new SimpleStringProperty("");

    public TrelloViewModel(Trello trello){
        this.trello = trello;
        title.setValue(this.trello.getTitle());
    }

    public StringProperty getTrelloTitleProperty() {
        return title;
    }

    public StringProperty getBoardTitleProperty() {
        return new SimpleStringProperty(trello.getBoardTitle());
    }

    public BoardViewModel getBoardVM(){
        //TODO : pas génial, demander comment faire autrement
        return new BoardViewModel(this.trello.getBoard());
    }

}
