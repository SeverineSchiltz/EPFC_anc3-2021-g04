package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Board {
    private final StringProperty title;
    private final ObservableList<Column> columns = FXCollections.observableArrayList();
    private int id;

    Board(int id, String title){
        this.id = id;
        this.title= new SimpleStringProperty(title);
    }

    Board(Board board) {
        this(board.getId(), board.title.getValue());
        for (Column column : board.getColumns()) {
            columns.add(new Column(column, this));
        }
    }

    public int getId(){
        return id;
    }

    public void setID(int id){
        this.id = id;
    }

    public void restore(BoardMemento m){
        Board b = m.getCopiedBoard();
        this.title.setValue(b.getTitle().getValue());
        int numNew = b.getNumberOfColumn();
        int numthis = this.getNumberOfColumn();
        int minNum = Math.min(numNew, numthis);
        for(int i = 0; i < minNum; ++i){
            columns.get(i).restore(b.getColumns().get(i));
        }
        if(numNew < numthis){
            for(int i = numthis-1; i >= minNum; --i){
                columns.remove(i);
            }
        }else if(numNew > numthis){
            for(int i = minNum; i < numNew; ++i){
                columns.add(new Column(b.getColumns().get(i), this)); // cette ligne est problematique car elle crée une nouvelle référence ce qui pose problème dans les commandes (sans memento) qui se basent sur les références
            }
        }
        DAOBoard.getInstance().save(b);
    }

    // To keep this method in case we add id for iteration 3
    //Cette méthode pose problème car elle redonne de nouvelles références aux colonnes et donc pose problème dans les commandes...
//    public void restore(Board board) {
//        // board memento va garder une copie du board donc il (board qui est lié/bindé à la vue)
//        // se rechangera en fonction des param qu'on lui donnera
//        changeTitle(board.title.getValue());
//        columns.clear();
//        for (Column column : board.getColumns()) {
//            addColumn(new Column(column, this));
//            // new column car sinon, on reprend des col avec un autre board comme référence
//        }
//    }

    public StringProperty getTitle(){
        return title;
    }

    public void addColumn(Column c){
        columns.add(c);
    }

    public ObservableList<Column> getColumns() {
        return FXCollections.unmodifiableObservableList(columns);
    }

    public void changeColumnPosition(Column c, int pos){
        int curPos = columns.indexOf(c);
        if(curPos+pos >=0 && curPos+pos< columns.size()){
            Column temp = columns.set(curPos+pos, c);
            columns.set(curPos, temp);
            DAOColumn.getInstance().update(temp);
            DAOColumn.getInstance().update(c);
        }
    }

    public Column addColumn() {
        Column c = Column.createNewDefaultColumn(this);
        addColumn(c);
        return c;
    }

    public Column createNewColumn() {
        return Column.createNewDefaultColumn(this);
    }

    public void deleteColumn(Column c){
        int pos = c.getPosition();
        columns.remove(c);
        DAOColumn.getInstance().delete(c);
        DAOBoard.getInstance().updateColumnsAsFromPosition(this, pos, -1);
    }

    public void deleteColumn(int index){
        columns.remove(index);
    }

    public int getNumberOfColumn(){
        return columns.size();
    }

    public int getColumnPosition(Column c){
        return columns.indexOf(c);
    }

    public Column getColumnAtPosition(int index){
        return columns.get(index);
    }

    public void addColumnAtPosition(Column c, int pos){
        columns.add(null);
        for(int i = columns.size()-1; i> pos; --i){
            columns.set(i, columns.get(i-1));
        }
        columns.set(pos, c);
        c.setBoard(this);
        DAOBoard.getInstance().updateColumnsAsFromPosition(this, pos, 1);
        int newID = DAOColumn.getInstance().addWithCards(c);
        c.setID(newID);
    }

    public void changeTitle(String newTitle){
        title.setValue(newTitle);
        DAOBoard.getInstance().update(this);
    }

    @Override
    public String toString() {
        return this.title.getValue();
    }



}
