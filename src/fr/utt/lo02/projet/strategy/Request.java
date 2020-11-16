package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;

public class Request
{

	private Coordinates coordinates;

	private Card card;

	public Request(Coordinates coordinates, Card card)
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
}
