package mvvm.commands;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommandManager {

    private final ObservableList<Command> commands = FXCollections.observableArrayList();
    private final IntegerProperty posInList = new SimpleIntegerProperty(0);
    private final StringProperty strLastCommand = new SimpleStringProperty("Annuler");
    private final StringProperty strNextCommand = new SimpleStringProperty("Refaire");

    private boolean isExecutable(){
        return posInList.getValue() < commands.size();
    }

    private boolean isUnExecutable(){
        return posInList.getValue() > 0;
    }

    public void setNewString(){
        if(posInList.getValue() > 0) {
            strLastCommand.setValue("Annuler " + commands.get(posInList.getValue() - 1).toString());
        }else{
            strLastCommand.setValue("Annuler ");
        }
        if(posInList.getValue() < commands.size()) {
            strNextCommand.setValue("Refaire " + commands.get(posInList.getValue()).toString());
        }else{
            strNextCommand.setValue("Refaire ");
        }
    }

    public void addCommand(Command c){
        if(isExecutable()){
            removeNextCommandsInList();
        }
        commands.add(c);
        posInList.setValue(posInList.getValue() + 1);
        setNewString();
    }

    private void removeNextCommandsInList(){
        for(int i = commands.size() -1; i >= posInList.getValue(); --i){
            commands.remove(i);
        }
    }

    public void execute(){
        if(posInList.getValue() > 0) {
            Command c = commands.get(posInList.getValue() - 1); // look for the last cmd added in the list
            c.execute();
        }
    }

    public void unexecute(){
        if(isUnExecutable()){
            Command c = commands.get(posInList.getValue() -1);
            c.unexecute();
            posInList.setValue(posInList.getValue() - 1);
            setNewString();
        }
    }

    public void reexecute(){
        if(isExecutable()) {
            Command c = commands.get(posInList.getValue());
            c.execute();
            posInList.setValue(posInList.getValue() + 1);
            setNewString();
        }
    }

    public BooleanBinding isUndoNotPossible(){
        return posInList.lessThanOrEqualTo(0);
    }

    public BooleanBinding isRedoNotPossible(){
        return posInList.greaterThanOrEqualTo(new SimpleListProperty<>(commands).sizeProperty());
    }

    public StringProperty getTextLastCommand(){
        return strLastCommand;
    }

    public StringProperty getTextNextCommand(){
        return strNextCommand;
    }

}
