package view;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import model.Card;
import mvvm.CardViewModel;

public class CardView extends BorderPane {

    private final CardViewModel cardvm;
    private final EditableLabel title;

    public CardView(Card card){
        this.cardvm = new CardViewModel(card);
        title = new EditableLabel(cardvm.getCardTitleProperty());
        config();
        setID();
    }

    private void config(){
        this.setCenter(title.getLabel());
        //ajouter les images de flèches sur les boutons:
//        this.setTop(new Button("t"));
//        this.setRight(new Button("r"));
//        this.setLeft(new Button("l"));
//        this.setBottom(new Button("b"));
    }

    private void setID(){
        this.setId("card");
        this.title.getLabel().setId("cardTitle");
    }


}
