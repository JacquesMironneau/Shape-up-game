package fr.utt.lo02.projet.controller;

import fr.utt.lo02.projet.model.board.BoardEmptyException;
import fr.utt.lo02.projet.model.board.Card;
import fr.utt.lo02.projet.model.board.Coordinates;
import fr.utt.lo02.projet.model.game.AbstractShapeUpGame;
import fr.utt.lo02.projet.model.game.GameState;
import fr.utt.lo02.projet.model.game.MoveRequestResult;
import fr.utt.lo02.projet.model.game.PlaceRequestResult;
import fr.utt.lo02.projet.model.player.MoveRequest;
import fr.utt.lo02.projet.model.player.PlaceRequest;
import fr.utt.lo02.projet.model.player.PlayerHandEmptyException;
import fr.utt.lo02.projet.model.player.RealPlayer;
import fr.utt.lo02.projet.view.GameView;
import fr.utt.lo02.projet.view.console.GameConsoleView;
import fr.utt.lo02.projet.view.hmi.SwingHmiView;

import java.util.Set;

/**
 * This class represent a game controller in the shape up game
 * It is intended to work with 2 views (more precisely a console and a hmi one)
 * and implements therefore a lock system which allow to play using both view in a concurrent way.
 *
 * The controller is here a state-machine, that changes the state of the model (here the AbstractShapeUpGame)
 * the views are observing (Observer pattern) the model and thus are notified when the model changes its states
 * they then call controller method and so on.
 */
public class ShapeUpGameController implements GameController
{

    /**
     * the model used in the current game
     */
    protected final AbstractShapeUpGame gameModel;

    /**
     * The views of the MVC pattern
     */
    protected Set<GameView> view;

    /**
     * The last action, here used to make some decision based on previous and current state
     */
    protected GameState lastAction;

    /**
     * A lock used in the play method, allowing only one view (in the 2) to use this method
     */
    protected boolean playLock;

    /**
     * A lock used in the endTurn method, allowing only one view (in the 2) to use this method
     */
    protected boolean endTurnLock;

    /**
     * A lock used in the endRound method, allowing only one view (in the 2) to use this method
     */
    protected boolean endRoundLock;

    /**
     * Instantiate the controller, setting up the lock and model
     *
     * @param gameModel the game to use
     * @param viewSet   the MVC views
     */
    public ShapeUpGameController(AbstractShapeUpGame gameModel, Set<GameView> viewSet)
    {

        this.view = viewSet;
        this.gameModel = gameModel;
        this.gameModel.initRound();
        lastAction = GameState.FIRST_TURN;
        // We here place playLock to true because the hmi view call this method after the call of the console
        // and we want the hmi to trigger if the game should be played (after a button click from the user).
        playLock = true;
        // The others lock are set to false: the first view to call their associated method, block the other call
        endTurnLock = false;
        endRoundLock = false;
    }

    @Override
    public synchronized void askChoice(int choiceNumber, int choice)
    {
        reset();

        if (choiceNumber == 1)
        {
            switch (choice)
            {
                case 1 -> gameModel.setState(GameState.MOVE);
                case 2 -> gameModel.setState(GameState.PLACE);
            }
        } else
        {
            if (choice == 1)
            {
                gameModel.setState(GameState.MOVE);
            } else
            {
                gameModel.setState(GameState.END_TURN);
            }
        }
    }

    @Override
    public synchronized void askMove(int x, int y, int x2, int y2)
    {
        reset();

        MoveRequestResult mrr = this.gameModel.moveCardRequest(new MoveRequest(new Coordinates(x, y), new Coordinates(x2, y2)));

        if (mrr == MoveRequestResult.MOVE_VALID)
        {
            if (lastAction == GameState.PLACE_DONE)
            {
                lastAction = GameState.END_TURN;
                gameModel.setState(GameState.MOVE_DONE);
                gameModel.setState(GameState.END_TURN);
            } else
            {
                lastAction = GameState.MOVE_DONE;
                gameModel.setState(GameState.MOVE_DONE);
                gameModel.setState(GameState.PLACE);

            }
        } else
        {
            for (GameView view : view)
            {
                view.displayPlaceFailed(mrr);

            }
            gameModel.setState(GameState.ACTION_FAILED);
            if (lastAction == GameState.PLACE_DONE)
            {
                gameModel.setState(GameState.SECOND_CHOICE);
            } else
            {
                gameModel.setState(GameState.FIRST_CHOICE);
            }
        }
    }

    @Override
    public synchronized void askPlace(int x, int y, int cardIndex)
    {
        reset();

        Card card = null;

        try
        {
            card = gameModel.getCurrentPlayer().getPlayerHand().get(cardIndex);
        } catch (IndexOutOfBoundsException e)
        {
            gameModel.setState(GameState.ACTION_FAILED);
            gameModel.setState(GameState.PLACE);
        }
        PlaceRequestResult prr = this.gameModel.placeCardRequest(new PlaceRequest(new Coordinates(x, y), card));

        if (prr == PlaceRequestResult.CORRECT_PLACEMENT)
        {
            if (gameModel.isRoundFinished())
            {
                gameModel.setState(GameState.PLACE_DONE);

                gameModel.setState(GameState.END_ROUND);
            } else if (lastAction == GameState.MOVE_DONE)
            {
                lastAction = GameState.END_TURN;
                gameModel.setState(GameState.PLACE_DONE);
                gameModel.setState(GameState.END_TURN);

            } else if (lastAction == GameState.FIRST_TURN)
            {
                gameModel.setState(GameState.PLACE_DONE);
                gameModel.setState(GameState.END_TURN);

            } else
            {
                lastAction = GameState.PLACE_DONE;
                gameModel.setState(GameState.PLACE_DONE);

                gameModel.setState(GameState.SECOND_CHOICE);
            }
        } else
        {
            for (GameView view : view)
            {
                view.displayMoveFailed(prr);

            }
            gameModel.setState(GameState.ACTION_FAILED);

            if (lastAction == GameState.FIRST_TURN)
            {
                gameModel.setState(GameState.PLACE);
            } else if (lastAction == GameState.MOVE_DONE)
            {
                gameModel.setState(GameState.PLACE);
            } else
            {
                gameModel.setState(GameState.FIRST_CHOICE);
            }
        }
    }

    @Override
    public synchronized void endTurn()
    {
        if (!endTurnLock)
        {

            endTurnLock = true;
            lastAction = null;
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
            endTurnLock = false;

        }

    }

    @Override
    public synchronized void play()
    {

        if (!playLock)
        {
            playLock = true;
            if (gameModel.getCurrentPlayer() instanceof RealPlayer)// If the player is real, give him the choice
            {
                this.gameModel.drawCard();
//                gameModel.setState(GameState.CARD_DRAW);

                if (lastAction == GameState.FIRST_TURN)
                {
                    gameModel.setState(GameState.PLACE);
                } else
                {
                    gameModel.setState(GameState.FIRST_CHOICE);
                }
                //TODO check why this line exists
                //gameModel.setState(GameState.CARD_DRAW);
            } else
            {
                this.gameModel.drawCard();
//                gameModel.setState(GameState.CARD_DRAW);

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
    public synchronized void endRound()
    {
        if (!endRoundLock)
        {
            endRoundLock = true;
            gameModel.endRound();
            if (gameModel.getRoundNumber() == AbstractShapeUpGame.MAX_ROUND_NUMBER)
            {
                endGame();
            } else
            {
                lastAction = GameState.FIRST_TURN;

                for (GameView view : view)
                {
                    view.displayScoresEndRound();

                }
                gameModel.initRound();
                // TODO add play call in gameconsoleview
                //play();
            }
        } else
        {
            endRoundLock = false;
        }

    }

    @Override
    public synchronized void endGame()
    {
        reset();

        for (GameView view : view)
        {
            view.displayBoard();
        }
        gameModel.setState(GameState.VICTORY_CARD);

        gameModel.setState(GameState.END_GAME);
    }

    /**
     * This method handle the console view interruption:
     * To put it shortly when the user can do an action (place,move) he has the choice to do it in console
     * or in hmi. If he does the choice to do it in hmi, the controller needs to stop the console from reading input
     * in the terminal. To do this, we interrupt the reading thread.
     *
     * @see fr.utt.lo02.projet.view.console.ConsoleInputReadTask for explanation on how the console handle
     * the interruption
     */
    protected void reset()
    {
        if (!Thread.currentThread().getName().equals(SwingHmiView.THREAD_FROM_GAME_VIEW_NAME))
        {
            return;
        }
        for (GameView view : view)
        {
            if (view instanceof GameConsoleView)
            {
                GameConsoleView gcv = (GameConsoleView) view;
                Thread thread = gcv.getThread();
                if (thread == null)
                {
                    return;
                }
                if (thread.isAlive() && !thread.isInterrupted())
                {
                    thread.interrupt();
                }
            }
        }
    }

}
