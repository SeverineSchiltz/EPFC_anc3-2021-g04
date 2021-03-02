package view;

import javafx.geometry.Orientation;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import model.*;
import mvvm.BoardViewModel;

public class BoardView extends ListView<Column> {

    private BoardViewModel bvm;

    public BoardView(BoardViewModel viewModel) {
        this.bvm = viewModel;
        customizeThis();
        configDataBindings();
        config();
    }

    private void config() {
        //préférence pour le mettre ici plutôt que dans le css
        this.setOrientation(Orientation.HORIZONTAL);
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
                        columnView = new ColumnView(column, bvm.getCmdManager());
                    }
                    setGraphic(columnView);
                }
            };
            cell.setOnMouseClicked(e -> {
                if (cell.isEmpty() && e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                    bvm.addColumn();
                }
            });
            return cell;
        });
        this.setOnMouseClicked(e -> {
            if (this.getItems().isEmpty() && e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2 ) {
                bvm.addColumn();
            }
        });

        this.setId("board");
    }



}
