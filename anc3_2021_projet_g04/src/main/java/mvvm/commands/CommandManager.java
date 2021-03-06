package mvvm.commands;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

public class CommandManager {

    private ObservableList<Command> history = FXCollections.observableArrayList();
    private int posInList = 0;
    private IntegerProperty posProp = new SimpleIntegerProperty(0);
    private StringProperty strLastCommand = new SimpleStringProperty("Annuler");
    private StringProperty strNextCommand = new SimpleStringProperty("Refaire");

    private boolean isExecutable(){
        return posInList < history.size();
    }

    private boolean isUnExecutable(){
        return posInList > 0;
    }

    public void setNewString(){
        if(posInList > 0) {
            strLastCommand.setValue("Annuler " + history.get(posInList - 1).toString());
        }else{
            strLastCommand.setValue("Annuler ");
        }
        if(posInList < history.size()) {
            strNextCommand.setValue("Refaire " + history.get(posInList).toString());
        }else{
            strNextCommand.setValue("Refaire ");
        }
    }

    public void addCommand(Command c){
        if(isExecutable()){
            removeNextCommandsInList();
        }
        history.add(c);
        ++posInList;
        posProp.setValue(posInList);
        setNewString();
    }

    private void removeNextCommandsInList(){
        for(int i = history.size() -1; i >= posInList; --i){
            history.remove(i);
        }
    }

    public void execute(){
        if(isExecutable()) {
            Command c = history.get(posInList);
            c.execute();
            ++posInList;
            posProp.setValue(posInList);
            setNewString();
        }
    }

    public void unexecute(){
        if(isUnExecutable()){
            Command c = history.get(posInList -1);
            c.unexecute();
            --posInList;
            posProp.setValue(posInList);
            setNewString();
        }
    }

    public BooleanBinding isUndoNotPossible(){
        return posProp.lessThanOrEqualTo(0);
    }

    public BooleanBinding isRedoNotPossible(){
        return posProp.greaterThanOrEqualTo(new SimpleListProperty<>(history).sizeProperty());
    }

    public StringProperty getTextLastCommand(){
        return strLastCommand;
    }

    public StringProperty getTextNextCommand(){
        return strNextCommand;
    }

}
