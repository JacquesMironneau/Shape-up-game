package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;

public class PlaceRequest
{

	private Coordinates coordinates;

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

	@Override
	public String toString()
	{
		return "PlaceRequest{" +
				"coordinates=" + coordinates +
				", card=" + card +
				'}';
	}
}
