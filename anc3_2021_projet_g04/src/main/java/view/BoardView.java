package view;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import model.*;
import mvvm.BoardViewModel;

public class BoardView extends ListView<Column> {

    private BoardViewModel bvm;

    public BoardView(BoardViewModel viewModel) {
        this.bvm = viewModel;
        customizeThis();
        configDataBindings();
    }

    private void configDataBindings() {
        this.itemsProperty().bind(bvm.columnsProperty());
    }

    private void customizeThis() {
        this.setCellFactory(view -> new ListCell<>() {
            @Override
            protected void updateItem(Column column, boolean b) {
                super.updateItem(column, b);
                ColumnView columnView = null;
                if (column != null) {
                    columnView = new ColumnView(column);
                }
                setGraphic(columnView);
            }
        });
        this.setId("board");
    }

}
