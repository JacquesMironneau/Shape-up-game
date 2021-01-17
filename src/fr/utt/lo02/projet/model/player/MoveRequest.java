package fr.utt.lo02.projet.model.player;

import fr.utt.lo02.projet.model.board.Coordinates;

/**
 * Represents a move request from a player.
 * @author Baptiste, Jacques
 *
 */
public class MoveRequest
{

	/**
	 * Origin coordinates of the card to move.
	 */
	private final Coordinates origin;

	/**
	 * Destination coordinates of the card to move.
	 */
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

	/**
	 * Print in the console the move request.
	 */
	@Override
	public String toString()
	{
		return "MoveRequest{" +
				"origin=" + origin +
				", destination=" + destination +
				'}';
	}
}
