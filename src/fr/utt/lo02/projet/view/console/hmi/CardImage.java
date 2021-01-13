package fr.utt.lo02.projet.view.console.hmi;

import fr.utt.lo02.projet.model.board.Card;

import java.util.Objects;

public class CardImage {

    private int x;

    private int y;

    private Card card;


    public CardImage(int x, int y, Card card) {
        this.x = x;
        this.y = y;
        this.card = card;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardImage cardImage = (CardImage) o;
        return x == cardImage.x &&
                y == cardImage.y &&
                Objects.equals(card, cardImage.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, card);
    }

    @Override
    public String toString() {
        return "CardImage{" +
                "x=" + x +
                ", y=" + y +
                ", card=" + card +
                '}';
    }
}
