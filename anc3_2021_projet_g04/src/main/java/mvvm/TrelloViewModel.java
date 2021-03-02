package mvvm;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Trello;
import mvvm.commands.CommandManager;

public class TrelloViewModel {
    private Trello trello;
    private CommandManager cmdManager;
    private final StringProperty title = new SimpleStringProperty("");

    public TrelloViewModel(Trello trello, CommandManager cmdManager){
        this.trello = trello;
        this.cmdManager = cmdManager;
        title.setValue(this.trello.getTitle());
    }

    public StringProperty getTrelloTitleProperty() {
        return title;
    }

    public StringProperty getBoardTitleProperty() {
        return trello.getBoardTitle();
    }

    public BoardViewModel getBoardVM(){
        return new BoardViewModel(this.trello.getBoard(), cmdManager);
    }

    public void executeCommand(){
        cmdManager.execute();
    }

    public void unexecuteCommand(){
        cmdManager.unexecute();
    }

}
