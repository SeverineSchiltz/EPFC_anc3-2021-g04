package mvvm.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private List<Command> history = new ArrayList<Command>();
    private int posInList = 0;

    public void addCommand(Command c){
        if(posInList < history.size()){
            //TODO : redimensionner la list afin qu'il n'y ait plus de redo
            history.set(posInList, c);
        }else{
            history.add(c);
        }
        ++posInList;
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
