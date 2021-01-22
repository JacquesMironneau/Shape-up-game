package fr.utt.lo02.projet.model.player;

import fr.utt.lo02.projet.model.board.Card;
import fr.utt.lo02.projet.model.board.Coordinates;

/**
 * Represents a place request from a player.
 * @author Baptiste, Jacques
 *
 */
public class PlaceRequest
{

	/**
	 * Destination coordinates of the card to place.
	 */
	private Coordinates coordinates;

	/**
	 * Card to place.
	 */
	private Card card;

	/**
	 * Request's constructor. Sets up parameters.
	 * @param coordinates the coordinates where to place the card.
	 * @param card the card to place.
	 */
	public PlaceRequest(Coordinates coordinates, Card card)
	{
		this.coordinates = coordinates;
		this.card = card;
	}

	/**
	 * Used to get the coordinates where to place the card.
	 * @return the coordinates where to place the card.
	 */
	public Coordinates getCoordinates()
	{
		return coordinates;
	}

	/**
	 * Used to get the card to place.
	 * @return the card to place.
	 */
	public Card getCard()
	{
		return card;
	}

	/**
	 * Print in the console the place request.
	 */
	@Override
	public String toString()
	{
		return "PlaceRequest{" +
				"coordinates=" + coordinates +
				", card=" + card +
				'}';
	}
}
