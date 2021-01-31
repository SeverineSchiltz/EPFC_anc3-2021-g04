package model;

public class Card {
    private Column column;
    private String title;

    public Card(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

}
