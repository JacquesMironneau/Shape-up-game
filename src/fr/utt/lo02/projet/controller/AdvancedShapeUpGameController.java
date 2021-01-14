package fr.utt.lo02.projet.controller;

import fr.utt.lo02.projet.model.board.BoardEmptyException;
import fr.utt.lo02.projet.model.game.AbstractShapeUpGame;
import fr.utt.lo02.projet.model.game.GameState;
import fr.utt.lo02.projet.model.player.PlayerHandEmptyException;
import fr.utt.lo02.projet.model.player.RealPlayer;
import fr.utt.lo02.projet.view.GameView;

import java.util.Set;

/**
 * Controller for the game used in the advanced mode
 * The main changes are here when the card are draw since in the rules
 * the card are drawn at the end of a turn and not at the beginning.
 */
public class AdvancedShapeUpGameController extends ShapeUpGameController
{
    public AdvancedShapeUpGameController(AbstractShapeUpGame gameModel, Set<GameView> view)
    {
        super(gameModel, view);
    }

    @Override
    public void play()
    {

        if (!playLock)
        {
            playLock = true;
            if (gameModel.getCurrentPlayer() instanceof RealPlayer)// If the player is real, give him the choice
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
        } else
        {
            playLock = false;
        }
    }

    @Override
    public void endTurn()
    {
        if (!endRoundLock)
        {
            endRoundLock = true;
            lastAction = null;
            if (gameModel.getCurrentPlayer() instanceof RealPlayer)
            {
                gameModel.drawCard();
            }

            gameModel.nextPlayer();

            if (gameModel.isRoundFinished())
            {
                gameModel.setState(GameState.END_ROUND);
            } else
            {
                boolean oldLock = playLock;
                playLock = false;
                play();
                playLock = oldLock;
            }
        } else
        {
            endRoundLock = false;
        }
    }

}
