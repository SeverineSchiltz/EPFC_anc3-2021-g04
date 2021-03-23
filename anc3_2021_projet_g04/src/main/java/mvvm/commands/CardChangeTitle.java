package mvvm.commands;

import model.Card;

public class CardChangeTitle implements Command {

    private final Card card;
    private final String oldTitle;
    private final String newTitle;

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
