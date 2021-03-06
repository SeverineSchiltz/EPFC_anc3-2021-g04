package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Board {
    private StringProperty title;
    private final ObservableList<Column> listColumns = FXCollections.observableArrayList();

    Board(String title){
        this.title= new SimpleStringProperty(title);
    }

    public StringProperty getTitle(){
        return this.title;
    }

    public void addColumn(Column c){
        listColumns.add(c);
    }

    public ObservableList<Column> getColumns() {
        return FXCollections.unmodifiableObservableList(listColumns);
    }

    public void changeColumnPosition(Column c, int pos){
        int curPos = listColumns.indexOf(c);
        if(curPos+pos >=0 && curPos+pos< listColumns.size()){
            Column temp = listColumns.set(curPos+pos, c);
            listColumns.set(curPos, temp);
        }
    }

    public boolean isColumnFirst(Column c){
        return listColumns.indexOf(c) == 0;
    }

    public boolean isColumnLast(Column c){
        return listColumns.indexOf(c) == listColumns.size()-1;
    }

    public void addColumn(){
        addColumn(new Column("Column", this));
    }

    public void deleteColumn(Column c){
        listColumns.remove(c);
    }

    public void deleteColumn(int index){
        listColumns.remove(index);
    }

    public int getNumberOfColumn(){
        return listColumns.size();
    }

    public int getColumnPosition(Column c){
        return listColumns.indexOf(c);
    }

    public void addColumnAtPosition(Column c, int pos){
        listColumns.add(null);
        for(int i = listColumns.size()-1; i> pos; --i){
            listColumns.set(i, listColumns.get(i-1));
        }
        listColumns.set(pos, c);
    }

}
