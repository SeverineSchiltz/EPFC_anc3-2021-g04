package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Column {
    private String title;
    private final List<Card> listCards = new ArrayList<>();

    public Column(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }

    public void addCard(Card c){
        this.listCards.add(c);
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(listCards);
    }
}
