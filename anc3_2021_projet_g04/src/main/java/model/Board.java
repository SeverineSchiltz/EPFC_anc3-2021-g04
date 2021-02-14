package model;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Board {
    private String title;
    private final ObservableList<Column> listColumns = FXCollections.observableArrayList();

    public Board(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void addColumn(Column c){
        listColumns.add(c);
    }

    public SimpleListProperty<Column> getColumns() {
        return new SimpleListProperty<>(listColumns);
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
        listColumns.add(new Column("Column" + (listColumns.size()+1) , this));
    }

    public void deleteColumn(Column c){
        listColumns.remove(c);
    }

}
