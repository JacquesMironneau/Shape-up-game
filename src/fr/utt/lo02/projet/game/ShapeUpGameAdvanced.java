package fr.utt.lo02.projet.game;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.visitor.IBoardVisitor;
import fr.utt.lo02.projet.strategy.PlayerStrategy;

import java.util.List;

public class ShapeUpGameAdvanced extends AbstractShapeUpGame
{

	public ShapeUpGameAdvanced(IBoardVisitor visitor, List<PlayerStrategy> players, AbstractBoard board)
	{
		super(visitor, players, board);
	}

	@Override
	public void initRound()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void playRound()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void playTurn(PlayerStrategy player)
	{

	}


	@Override
	protected void calculateGameScore()
	{
		// TODO Auto-generated method stub

	}

}
