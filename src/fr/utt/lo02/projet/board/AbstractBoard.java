package fr.utt.lo02.projet.board;

import fr.utt.lo02.projet.GameView;
import fr.utt.lo02.projet.board.visitor.IBoardVisitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	protected Map<Coordinates, Card> placedCards;


	protected Set<GameView> gameViews;
	/**
	 * Initialize the placed card to an empty hashmap.
	 */
	public AbstractBoard()
	{
		this.placedCards = new HashMap<Coordinates, Card>();
		gameViews = new HashSet<GameView>();
	}

	public void addObserver(GameView view)
	{
		this.gameViews.add(view);
	}

	public void removeObserver(GameView view)
	{
		this.gameViews.remove(view);
	}

	/**
	 * Visitor accept implementation in order to proceed score calculation for a round.
	 *
	 * @param board       the visitor
	 * @param victoryCard The victory card associated with the score
	 */
	public abstract int accept(IBoardVisitor board, Card victoryCard);

	/**
	 * Check if the card is adjacent to an existing card on the board.
	 *
	 * @param coordinates
	 * @return if a card is adjacent to an existing card.
	 */
	public abstract boolean isCardAdjacent(Coordinates coordinates);

	/**
	 * Check if the given coordinates are in the board layout
	 * Return false if a card exists at the given position
	 *
	 * @param coordinates
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
	 * Remove a given coordinate and card from the board
	 *
	 * @param oldCoordinates the position of the card to remove
	 * @param oldCard        the card to remove
	 */
	public void removeCard(Coordinates oldCoordinates, Card oldCard)
	{
		this.placedCards.remove(oldCoordinates, oldCard);
	}


	/**
	 * Display every card of board in a text based format
	 */
	public abstract void display();
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
