package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.game.AbstractShapeUpGame;

/**
 * Represent the strategy for a virtual player.
 * It implements Player Strategy to follow the player's construction.
 * @author Baptiste, Jacques
 *
 */

public class VirtualPlayer implements PlayerStrategy
{


	private AbstractShapeUpGame game;


	@Override
	public Choice askChoice()
	{
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public void askPlaceCard()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void askMoveCard()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void displayBoard()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void displayScores()
	{
		// TODO Auto-generated method stub

	}

	public void setGame(AbstractShapeUpGame game)
	{
		this.game = game;
	}


}
