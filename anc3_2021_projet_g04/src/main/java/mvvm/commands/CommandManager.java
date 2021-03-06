package mvvm.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private List<Command> history = new ArrayList<Command>();
    private int posInList = 0;

    public void addCommand(Command c){
        if(posInList < history.size()){
            removeNextCommandInList();
        }
        history.add(c);
        ++posInList;
    }

    private void removeNextCommandInList(){
        for(int i = history.size() -1; i >= posInList; --i){
            history.remove(i);
        }
    }

    public void execute(){
        if(posInList < history.size()) {
            Command c = history.get(posInList);
            c.execute();
            ++posInList;
        }
    }

    public void unexecute(){
        if(posInList > 0){
            Command c = history.get(posInList -1);
            c.unexecute();
            --posInList;
        }
    }

}
