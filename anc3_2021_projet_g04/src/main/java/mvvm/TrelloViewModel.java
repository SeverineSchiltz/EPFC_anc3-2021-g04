package mvvm;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Trello;
import mvvm.commands.*;

public class TrelloViewModel implements TitleManagement {
    private final Trello trello;
    private final CommandManager cmdManager;
    private final StringProperty title = new SimpleStringProperty("");

    public TrelloViewModel(Trello trello, CommandManager cmdManager) {
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

    public BoardViewModel getBoardVM() {
        return new BoardViewModel(this.trello.getBoard(), cmdManager);
    }

    public void executeCommand() {
        cmdManager.reexecute();
    }

    public void unexecuteCommand() {
        cmdManager.unexecute();
    }

    @Override
    public void changeTitle(String newTitle) {
//        cmdManager.addCommand(new BoardChangeTitle(trello.getBoard(), newTitle));
//        cmdManager.execute();
        cmdManager.addCommand(new BoardChangeTitleWithMemento(trello.getBoard(), newTitle));
        trello.getBoard().changeTitle(newTitle);
    }

    public CommandManager getCmdManager() {
        return cmdManager;
    }

}
