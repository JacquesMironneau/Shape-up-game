package fr.utt.lo02.projet.model.player;

import fr.utt.lo02.projet.model.board.AbstractBoard;
import fr.utt.lo02.projet.model.game.ChoiceOrder;

/**
 * Represents a real player. It doesn't use a predefined strategy because a real player has his own strategy.
 * It extends Player to follow the player's construction.
 *
 * @author Baptiste, Jacques
 */

public class RealPlayer extends Player
{

	/**
	 * Player's constructor. Sets up parameters.
	 * @param name the player's name.
	 * @param b the game's board.
	 */
	public RealPlayer(String name, AbstractBoard b)
	{
		super(name, b);
	}

	/**
	 * Ask the player if he wants to place or move a card, or end his turn.
	 * @param choiceNumber player's choice.
	 */
	@Override
	public Choice askChoice(ChoiceOrder choiceNumber)
	{
		return Choice.END_THE_TURN;
	}

	/**
	 * Ask a player to place a card
	 * So pick one card and select where he wants to put it.
	 */
	@Override
	public PlaceRequest askPlaceCard()
	{
	return null;
	}

	/**
	 * Ask a player to move a card.
	 * So pick one card and select where he wants to move it.
	 */
	@Override
	public MoveRequest askMoveCard()
	{
		return null;
	}
}
