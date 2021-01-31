package model;

import java.util.*;

public class Board {
    private String title;
    private final List<Column> listColumns = new ArrayList<>();

    public Board(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void addColumn(Column c){
        listColumns.add(c);
    }

    public List<Column> getColumns() {
        return Collections.unmodifiableList(listColumns);
    }

}
