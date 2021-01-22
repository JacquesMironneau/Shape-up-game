package fr.utt.lo02.projet.model.board;

import fr.utt.lo02.projet.model.board.visitor.IBoardVisitor;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the game board in which card can be added to a given position
 *
 * @author Baptiste, Jacques
 * @see Coordinates
 * It is abstract because a board exists in different shape
 */
public abstract class AbstractBoard implements Cloneable
{
    /**
     * Represents the placed card of the board
     */
    protected Map<Coordinates, Card> placedCards;

    /**
     * Initializes the placed card to an empty hashmap.
     */
    public AbstractBoard()
    {
        this.placedCards = new HashMap<>();
    }

    /**
     * Visitor accepts implementation in order to proceed score calculation for a round and a player.
     *
     * @param board       the visitor
     * @param victoryCard The victory card associated with the score
     */
    public abstract int accept(IBoardVisitor board, Card victoryCard);

    /**
     * Check if the card is adjacent to an existing card on the board.
     *
     * @param coordinates the coordinates of the new card
     * @return if a card is adjacent to an existing card.
     */
    public abstract boolean isCardAdjacent(Coordinates coordinates);

    /**
     * Check if the given coordinates are in the board layout
     * Returns false if a card exists at the given position
     *
     * @param coordinates the coordinates of the new card
     * @return if coordinates are in the board layout and the given position is not occupied by a card.
     */
    public abstract boolean isCardInTheLayout(Coordinates coordinates);

    /**
     * This method have to be called after the "isCardAdjacent" and/or "isCardInTheLayout" methods.
     *
     * @param coordinates the position where the card will be placed
     * @param newCard     the card which we be placed
     */
    public void addCard(Coordinates coordinates, Card newCard)
    {
        this.placedCards.put(coordinates, newCard);
    }

    /**
     * Removes a given coordinate and card from the board
     *
     * @param oldCoordinates the position of the card to remove
     * @param oldCard        the card to remove
     */
    public void removeCard(Coordinates oldCoordinates, Card oldCard)
    {
        this.placedCards.remove(oldCoordinates, oldCard);
    }


    /**
     * Displays every card of board in a text based format
     */
    public abstract void display();

    /**
     * Returns a map containing every card that have been placed on the board
     *
     * @return placed card
     */
    public Map<Coordinates, Card> getPlacedCards()
    {
        return placedCards;
    }

    /**
     *
     * @return a clean copy of the AbstractBoard with a copy of the placed cards
     * @throws CloneNotSupportedException if board can not be cloned
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        AbstractBoard o;
        o = (AbstractBoard) super.clone();
        o.placedCards = (Map<Coordinates, Card>) ((HashMap) placedCards).clone();

        return o;
    }
}
