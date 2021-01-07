package fr.utt.lo02.projet.game;

import fr.utt.lo02.projet.GameView;
import fr.utt.lo02.projet.board.BoardEmptyException;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.strategy.*;

import java.util.Set;

public class ShapeUpGameController implements GameController
{

    protected final AbstractShapeUpGame gameModel;
    protected Set<GameView> view;
    protected GameState lastAction;

    protected boolean lock;
    protected boolean endTurnLock;
    protected boolean endRoundLock;

    public ShapeUpGameController(AbstractShapeUpGame gameModel, Set<GameView> viewSet)
    {

        this.view = viewSet;
        this.gameModel = gameModel;
        this.gameModel.initRound();
        lastAction = GameState.FIRST_TURN;
        lock = false;
        endTurnLock = false;
        endRoundLock = false;
    }

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

    // Called by event in case HMI
    public synchronized void askMove(int x, int y, int x2, int y2)
    {
        reset();

        MoveRequestResult mrr = this.gameModel.moveCardRequest(new MoveRequest(new Coordinates(x, y), new Coordinates(x2, y2)));

        if (mrr == MoveRequestResult.MOVE_VALID)
        {
            // Change game state step 2
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

    public synchronized void askPlace(int x, int y, int cardIndex)
    {
        reset();

        Card card = null;

        try
        {
            card = gameModel.currentPlayer.getPlayerHand().get(cardIndex);
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
                boolean oldLock = lock;
                lock = false;
                play();
                lock = oldLock;
            }

        } else
        {
            endTurnLock = false;

        }

    }


    public synchronized void play()
    {

        if (!lock)
        {
            lock = true;
            if (gameModel.currentPlayer instanceof RealPlayer)// If the player is real, give him the choice
            {
                this.gameModel.drawCard();
                gameModel.setState(GameState.CARD_DRAW);

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
                gameModel.setState(GameState.CARD_DRAW);

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
            lock = false;
        }

    }

    public synchronized void endRound()
    {
        if (!endRoundLock)
        {
            endRoundLock = true;
            gameModel.endRound();
            if (gameModel.roundNumber == AbstractShapeUpGame.MAX_ROUND_NUMBER)
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

    public synchronized void endGame()
    {
        reset();

        for (GameView view : view)
        {
            view.displayBoard();
//			view.displayScoresEndRound();
        }
        gameModel.setState(GameState.VICTORY_CARD);

        gameModel.setState(GameState.END_GAME);
    }

    protected void reset()
    {
//        System.out.println(Thread.currentThread().getName());
        if (!Thread.currentThread().getName().equals(RectangleBoardFrameTest.THREAD_FROM_GAME_VIEW_NAME))
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
