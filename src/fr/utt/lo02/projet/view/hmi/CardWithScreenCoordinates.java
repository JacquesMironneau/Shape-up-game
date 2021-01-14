package fr.utt.lo02.projet.view.hmi;

import fr.utt.lo02.projet.model.board.Card;

import java.util.Objects;

/**
 * Represent a card and its screen coordinates
 */
public class CardWithScreenCoordinates
{

    /**
     * Screen card abscissa
     */
    private int x;

    /**
     * Screen card ordinate
     */
    private int y;

    /**
     * the card
     */
    private Card card;


    public CardWithScreenCoordinates(int x, int y, Card card) {
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
        CardWithScreenCoordinates cardImage = (CardWithScreenCoordinates) o;
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