package fr.utt.lo02.projet.board;

import fr.utt.lo02.projet.board.visitor.IBoardVisitor;

import java.util.HashMap;
import java.util.Map;

/**
 * Represent the game board in which card can be added to a given position (Cf Coordinates class)
 * It is abstract because a board exists in different shape
 *
 * @author Baptiste, Jacques
 */
public abstract class AbstractBoard
{
	/**
	 * Represent the placed card of the board
	 */
	public  Map<Coordinates, Card> placedCards;

	/**
	 * Initialize the placed card to an empty hashmap.
	 */
	public AbstractBoard()
	{
		this.placedCards = new HashMap<Coordinates, Card>();
	}

	/**
	 * Visitor accept implementation in order to proceed score calculation for a round.
	 *
	 * @param board       the visitor
	 * @param victoryCard The victory card associated with the score
	 */
	public abstract void accept(IBoardVisitor board, Card victoryCard);

	/**
	 * Check if the card is adjacent to an existing card on the board.
	 *
	 * @param x abscissa
	 * @param y ordinate
	 * @return if a card is adjacent to an existing card.
	 */
	public abstract boolean isCardAdjacent(int x, int y);

	/**
	 * Check if the given coordinates are in the board layout
	 * Return false if a card exists at the given position
	 *
	 * @param x abscissa
	 * @param y ordinate
	 * @return if coordinates are in the board layout and the given position is not occupied by a card.
	 */
	public abstract boolean isCardInTheLayout(int x, int y);

	/**
	 * This method have to be called after the "isCardAdjacent" and/or "isCardInTheLayout" methods.
	 *
	 * @param newCard An existing card
	 * @param x       abscissa
	 * @param y       ordinate
	 */
	public void addCard(Card newCard, int x, int y)
	{
		this.placedCards.put(new Coordinates(x, y), newCard);
	}

	/**
	 * Return the card that have been placed on the board
	 *
	 * @return placed card
	 */
	public Map<Coordinates, Card> getPlacedCards()
	{
		return placedCards;
	}
}
