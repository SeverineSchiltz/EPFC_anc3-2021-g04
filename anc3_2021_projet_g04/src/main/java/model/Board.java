package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Board {
    private StringProperty title;
    private final ObservableList<Column> columns = FXCollections.observableArrayList();

    Board(String title){
        this.title= new SimpleStringProperty(title);
    }

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
        }
    }

//    //TODO: à supprimer dès que sera changer dans les cartes
//    public boolean isColumnFirst(Column c){
//        return columns.indexOf(c) == 0;
//    }
//    public boolean isColumnLast(Column c){
//        return columns.indexOf(c) == columns.size()-1;
//    }

    public void addColumn(){
        addColumn(new Column("Column", this));
    }

    public void deleteColumn(Column c){
        columns.remove(c);
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

    public void addColumnAtPosition(Column c, int pos){
        columns.add(null);
        for(int i = columns.size()-1; i> pos; --i){
            columns.set(i, columns.get(i-1));
        }
        columns.set(pos, c);
    }

    public void changeTitle(String newTitle){
        title.setValue(newTitle);
    }

    @Override
    public String toString() {
        return this.title.getValue();
    }



}
