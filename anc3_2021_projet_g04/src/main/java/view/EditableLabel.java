package view;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

public class EditableLabel extends VBox {

    private final TextField tfTitle = new TextField();
    private final Label lbTitle = new Label();

    public EditableLabel(StringProperty title) {
        customizeThis(title);
        this.lbTitle.requestFocus();
        this.setId("editableLabel");
        this.lbTitle.setId("lbtitle");
        this.tfTitle.setId("tftitle");
    }

    public void customizeThis(StringProperty str) {
        this.lbTitle.textProperty().bind(str);
        tfTitle.textProperty().bindBidirectional(str);
        this.getChildren().add(lbTitle);

        lbTitle.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() ==2) {
                labelFocus(false);
            }
        });
        tfTitle.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                labelFocus(true);
            }
        });
        tfTitle.setOnMouseExited(e -> {
            labelFocus(true);
        });

        lbTitle.setWrapText(true);
    }

    private void labelFocus(boolean lbVisible){
        if(lbVisible){
            this.getChildren().remove(tfTitle);
            this.getChildren().add(lbTitle);
        }else{
            this.getChildren().remove(lbTitle);
            this.getChildren().add(tfTitle);
            tfTitle.requestFocus();
            tfTitle.selectAll();
        }

    }

    public String getTitle(){
        return lbTitle.getText();
    }

}
