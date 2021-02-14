package view;

import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
        this.setCellFactory(lv -> {
            ListCell<Column> cell = new ListCell<Column>() {
                @Override
                protected void updateItem(Column column, boolean b) {
                    super.updateItem(column, b);
                    ColumnView columnView = null;
                    if (column != null) {
                        columnView = new ColumnView(column);
                    }
                    setGraphic(columnView);
                }
            };
            cell.setOnMouseClicked(e -> {
                if ((cell.isEmpty() || cell.getItem() == null) && e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                    //lv.getItems().remove(null);
                    bvm.addColumn();
                    //lv.getItems().add(null);
                }
            });
            return cell;
        });


        this.setId("board");
    }



}
