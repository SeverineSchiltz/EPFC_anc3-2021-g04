package mvvm.commands;

import model.Card;

//TODO : à refaire avec position!
public class CardChangeTitle implements Command {

    private Card card;
    private String oldTitle;
    private String newTitle;

    public CardChangeTitle(Card card, String newTitle) {
        this.card = card;
        oldTitle = card.toString();
        this.newTitle = newTitle;
    }

    @Override
    public void execute() {
        card.changeTitle(newTitle);
    }

    @Override
    public void unexecute() {
        card.changeTitle(oldTitle);
    }

    @Override
    public String toString() {
        return "Changement de titre de la carte \"" + oldTitle + "\" vers \"" + newTitle + "\"";
    }

}
