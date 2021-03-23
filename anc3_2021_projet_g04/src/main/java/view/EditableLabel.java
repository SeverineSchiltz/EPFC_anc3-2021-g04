package view;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import mvvm.TitleManagement;

public class EditableLabel extends VBox {

    private final TextField tfTitle = new TextField();
    private final Label lbTitle = new Label();
    private final TitleManagement tm;
    private boolean isLabelVisible = true;

    public EditableLabel(StringProperty title, TitleManagement tm) {
        customizeThis(title);
        this.tm = tm;
        this.lbTitle.requestFocus();
        this.setId("editableLabel");
        this.lbTitle.setId("lbtitle");
        this.tfTitle.setId("tftitle");
    }

    public void customizeThis(StringProperty str) {
        this.lbTitle.textProperty().bind(str);
        tfTitle.setText(str.getValue());

        this.getChildren().add(lbTitle);

        lbTitle.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() ==2) {
                setOnExitLbTitle();
            }
        });
        tfTitle.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                setOnExitTfTitle();
            }
        });
        tfTitle.setOnMouseExited(e -> {
            setOnExitTfTitle();
        });

        lbTitle.setWrapText(true);
    }

    private void setOnExitTfTitle(){
        if(!isLabelVisible){
            // keep it at the bof code and not the end bc of "remove" method (calling setOnMouseExited calling setOnKeyPressed)
            isLabelVisible = true;
            tm.changeTitle(tfTitle.getText());
            this.getChildren().remove(tfTitle);
            this.getChildren().add(lbTitle);
        }
    }

    private void setOnExitLbTitle(){
        tfTitle.setText(lbTitle.getText());
        this.getChildren().remove(lbTitle);
        this.getChildren().add(tfTitle);
        tfTitle.requestFocus();
        tfTitle.selectAll();
        isLabelVisible = false;
    }

    public String getTitle(){
        return lbTitle.getText();
    }

}
