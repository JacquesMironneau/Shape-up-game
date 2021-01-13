package fr.utt.lo02.projet.model.player;

import fr.utt.lo02.projet.model.board.AbstractBoard;
import fr.utt.lo02.projet.model.game.ChoiceOrder;

/**
 * Represent the strategy for a real player.
 * It implements Player Strategy to follow the player's construction.
 *
 * @author Baptiste, Jacques
 */

public class RealPlayer extends Player
{


	public RealPlayer(String name, AbstractBoard b)
	{
		super(name, b);
	}

	@Override
	public Choice askChoice(ChoiceOrder choiceNumber)
	{
		return Choice.END_THE_TURN;
	}

	@Override
	public PlaceRequest askPlaceCard()
	{
	return null;
	}

	@Override
	public MoveRequest askMoveCard()
	{
		return null;
	}
}
