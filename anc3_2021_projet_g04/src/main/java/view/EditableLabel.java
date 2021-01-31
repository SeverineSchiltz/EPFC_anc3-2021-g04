package view;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;

public class EditableLabel{

    private Label title = new Label();

    public EditableLabel(StringProperty title) {
        this.title.textProperty().bindBidirectional(title);
        this.title.setId("title");
    }

    public Label getLabel(){
        return this.title;
    }

}
