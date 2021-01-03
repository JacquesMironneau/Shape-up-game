package fr.utt.lo02.projet.game;

import fr.utt.lo02.projet.GameView;
import fr.utt.lo02.projet.board.BoardEmptyException;
import fr.utt.lo02.projet.strategy.GameConsoleView;
import fr.utt.lo02.projet.strategy.PlayerHandEmptyException;
import fr.utt.lo02.projet.strategy.RealPlayer;

import java.util.Set;

public class AdvancedShapeUpGameController extends ShapeUpGameController
{
	public AdvancedShapeUpGameController(AbstractShapeUpGame gameModel, Set<GameView> view)
	{
		super(gameModel, view);
	}

	@Override
	public void play()
	{

		if (gameModel.currentPlayer instanceof RealPlayer)// If the player is real, give him the choice
		{
			if (lastAction == GameState.FIRST_TURN)
			{
				gameModel.setState(GameState.PLACE);
			} else
			{
				gameModel.setState(GameState.FIRST_CHOICE);
			}

		} else
		{
			try
			{
				gameModel.playTurn();
			} catch (PlayerHandEmptyException | BoardEmptyException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void endTurn()
	{
		lastAction = null;
		if (gameModel.getCurrentPlayer() instanceof RealPlayer)
		{
			gameModel.drawCard();
			gameModel.setState(GameState.CARD_DRAW);
		}

		gameModel.nextPlayer();

		if (gameModel.isRoundFinished())
		{
			gameModel.setState(GameState.END_ROUND);
		}
		else
		{
			play();
		}
	}

}
