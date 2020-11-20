package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.board.Coordinates;

public class MoveRequest
{

	private final Coordinates origin;

	private final Coordinates destination;

	public MoveRequest(Coordinates origin, Coordinates destination)
	{
		this.origin = origin;
		this.destination = destination;
	}

	public Coordinates getOrigin()
	{
		return origin;
	}

	public Coordinates getDestination()
	{
		return destination;
	}

	@Override
	public String toString()
	{
		return "MoveRequest{" +
				"origin=" + origin +
				", destination=" + destination +
				'}';
	}
}
