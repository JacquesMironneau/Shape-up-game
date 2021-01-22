package fr.utt.lo02.projet.view.hmi;

import fr.utt.lo02.projet.model.board.Card;

import java.util.Objects;

/**
 * Represent a card and its screen coordinates
 * Coordinates are represented in the java.swing way (e.g. the top left is (0,0))
 *
 * @see Card
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

    /**
     * Returns the card screen abscissa
     * @return the card screen abscissa
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the card screen abscissa
     * @param x the new screen abscissa
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the card screen ordinate
     * @return the card screen ordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the card screen ordinate
     * @param y the new screen ordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns the card of the object
     * @return the current card
     */
    public Card getCard() {
        return card;
    }

    /**
     * Sets the card of the object
     * @param card the card
     */
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
