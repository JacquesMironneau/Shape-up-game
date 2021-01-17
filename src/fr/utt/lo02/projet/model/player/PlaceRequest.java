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

	public PlaceRequest(Coordinates coordinates, Card card)
	{
		this.coordinates = coordinates;
		this.card = card;
	}

	public Coordinates getCoordinates()
	{
		return coordinates;
	}

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
