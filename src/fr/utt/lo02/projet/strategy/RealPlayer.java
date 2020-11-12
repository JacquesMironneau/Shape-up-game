package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.game.AbstractShapeUpGame;

/**
 * Represent the strategy for a real player.
 * It implements Player Strategy to follow the player's construction.
 * @author Baptiste, Jacques
 *
 */


public class RealPlayer implements PlayerStrategy
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
		// TODO pick card and x,y
		this.game.moveCardRequest(this,null, 1,2);
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
