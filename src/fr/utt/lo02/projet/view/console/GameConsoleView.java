package fr.utt.lo02.projet.view.console;

import fr.utt.lo02.projet.controller.GameController;
import fr.utt.lo02.projet.model.board.AbstractBoard;
import fr.utt.lo02.projet.model.board.Card;
import fr.utt.lo02.projet.model.game.AbstractShapeUpGame;
import fr.utt.lo02.projet.model.game.GameState;
import fr.utt.lo02.projet.model.game.MoveRequestResult;
import fr.utt.lo02.projet.model.game.PlaceRequestResult;
import fr.utt.lo02.projet.model.player.Player;
import fr.utt.lo02.projet.view.GameView;

import java.beans.PropertyChangeEvent;
import java.io.IOException;

/**
 * Console implementation of GameView
 * <p>
 * Player interactions are hence console displayed text and integer reading from the terminal
 *
 * @see GameView
 */
public class GameConsoleView implements GameView
{
    /**
     * Controller of the view (MVC pattern)
     */
    private GameController controller;

    /**
     * Game model (MVC pattern)
     */
    private final AbstractShapeUpGame model;

    /**
     * Board model of the game (MVC pattern)
     */
    private final AbstractBoard boardModel;

    /**
     * Console processing thread
     * <p>
     * This is the thread that can be interrupted by the controller in order to cancel
     * input reading from the console
     */
    private Thread thread;

    /**
     * Creates a GameConsoleView by setting the models
     *
     * @param model      game model
     * @param boardModel board game model
     */
    public GameConsoleView(AbstractShapeUpGame model, AbstractBoard boardModel)
    {
        this.model = model;
        this.boardModel = boardModel;
    }

    /**
     * Observer pattern, called by a state change in the model
     *
     * @param evt the fired change
     * @see AbstractShapeUpGame
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {

        GameState gameState = (GameState) evt.getNewValue();
        thread = new Thread(() -> stateProcess(gameState));
        thread.start();
    }

    /**
     * Displays and ask user to play according to the gameState
     *
     * @param gameState current game state of the model
     */
    private void stateProcess(GameState gameState)
    {
        switch (gameState)
        {

            case MOVE -> {
                displayVictoryCard();
                displayBoard();
                int x, y, x2, y2;
                System.out.println("You have to enter origin coordinates and destination coordinates.");

                x = getNumber("Please enter X origin : ");
                if (x == Integer.MIN_VALUE)
                {
                    return;
                }
                y = getNumber("Please enter Y origin : ");
                if (y == Integer.MIN_VALUE)
                {
                    return;
                }
                x2 = getNumber("Please enter X dest : ");
                if (x2 == Integer.MIN_VALUE)
                {
                    return;
                }
                y2 = getNumber("Please enter Y dest: ");
                if (y2 == Integer.MIN_VALUE)
                {
                    return;
                }

                this.controller.askMove(x, y, x2, y2);

            }
            case PLACE -> {
                displayVictoryCard();
                displayBoard();
                displayHand();
                int x, y, cardIndex;
                cardIndex = 0;
                System.out.println("You have to enter coordinates for where you want to place the card you draw. ");

                x = getNumber("Please enter X pos : ");
                if (x == Integer.MIN_VALUE)
                {
                    return;
                }
                y = getNumber("Please enter Y pos : ");
                if (y == Integer.MIN_VALUE)
                {
                    return;
                }
                if (this.model.getCurrentPlayer().getPlayerHand().size() > 1)
                {
                    cardIndex = getNumber("Please enter your card index: ");
                    if (cardIndex == Integer.MIN_VALUE)
                    {
                        return;
                    }
                    cardIndex--;

                }

                this.controller.askPlace(x, y, cardIndex);
            }
            case PLACE_DONE, MOVE_DONE -> displayBoard();
            case ACTION_FAILED -> System.err.println("Action failed, please retry...");
            case FIRST_TURN -> this.controller.play();
            case VICTORY_CARD -> {
                System.out.println("Victory card are");
                displayAllVictoryCard();

            }
            case FIRST_CHOICE -> {
                System.out.println("Turn:\t" + model.getCurrentPlayer().getName());
                displayVictoryCard();

                displayBoard();
                displayHand();
                int choice = getNumber("Please choose one action : \n 1. Move a card \n 2. Place a Card", 1, 2);

                if (choice != Integer.MIN_VALUE)
                {
                    this.controller.askChoice(1, choice);
                }

            }
            case SECOND_CHOICE -> {
                int choice = getNumber("Do you want to move a card ?\n 1. Yes (move a card)\n 2. No (end the turn)", 1, 2);
                displayBoard();
                displayHand();

                if (choice != Integer.MIN_VALUE)
                {
                    this.controller.askChoice(2, choice);
                }
            }
            case END_TURN -> this.controller.endTurn();
            case CARD_DRAW -> {
                Card c = model.getCurrentPlayer().getDrawCard();
                System.out.println(model.getCurrentPlayer().getName() + " you have draw");
                // TODO: 1/7/21 sometimes the card is placed before the display is set
                Card.printSingleCard(c);

            }
            case END_ROUND -> {
                System.out.println("The round is finished");
                this.controller.endRound();


                // Prevent the game to relaunch
                if (model.getRoundNumber() < 4)
                {
                    this.controller.play();
                }


            }
            case END_GAME -> {
                System.out.println("The game is finished");
                for (Player p : model.getPlayers())
                {
                    p.displayFinalScore();
                }
            }
        }
    }


    /**
     * Returns the console input reading thread
     *
     * @return the console thread
     */
    public Thread getThread()
    {
        return thread;
    }

    /**
     * Displays every victory cards of every players
     */
    private void displayAllVictoryCard()
    {
        if (model.getCurrentPlayer().getVictoryCard() == null)
        {
            System.out.println("No victory card for the moment");
        } else
        {
            model.getPlayers().forEach(player ->
            {
                System.out.println(player.getName());
                Card.printSingleCard(player.getVictoryCard());
            });

        }
    }

    /**
     * Displays the hand (one or several cards) of the current player
     */
    public void displayHand()
    {
        System.out.println(model.getCurrentPlayer().getPlayerHand().size() + " card(s) in hand\t");

        model.getCurrentPlayer().getPlayerHand().forEach(Card::printSingleCard);
    }

    /**
     * Displays the scores for a player
     */
    @Override
    public void displayScoresEndRound()
    {
        for (Player p : model.getPlayers())
        {
            p.displayRoundScore();
        }
        try
        {
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Displays the board in ascii art
     */
    @Override
    public void displayBoard()
    {
        boardModel.display();
    }

    /**
     * Displays the current victory card
     */
    public void displayVictoryCard()
    {
        System.out.println("Victory card\t");
        if (model.getCurrentPlayer().getVictoryCard() == null)
        {
            System.out.println("No victory card for the moment");
        } else
        {
            Card.printSingleCard(model.getCurrentPlayer().getVictoryCard());

        }
    }

    /**
     * Displays a text based reason of the previous failed move
     *
     * @param moveRequestResult reason of the failed move
     */
    @Override
    public void displayMoveFailed(MoveRequestResult moveRequestResult)
    {
        switch (moveRequestResult)
        {
            case NO_CARD_IN_THE_ORIGIN_COORDINATE -> System.err.println("You can't move an nonexistent card");
            case CARD_NOT_ADJACENT -> System.err.println("This card will not be adjacent to any card");
            case CARD_NOT_IN_THE_LAYOUT -> System.err.println("The card will be too far from the layout");
            case ORIGIN_AND_DESTINATION_ARE_EQUAL -> System.err.println("Moving the card to its exact location has never been a good idea");
        }
    }

    /**
     * Displays a text based reason of the previous failed place
     *
     * @param placeRequestResult reason of the failed place
     */
    @Override
    public void displayPlaceFailed(PlaceRequestResult placeRequestResult)
    {

        switch (placeRequestResult)
        {
            case PLAYER_DOESNT_OWN_CARD -> System.err.println("You don't own this card");
            case CARD_NOT_ADJACENT -> System.err.println("This location is not adjacent to an already existing card");
            case CARD_NOT_IN_THE_LAYOUT -> System.err.println("The card is too far from the layout");
        }
    }

    /**
     * Sets a controller to the view
     *
     * @param controller the controller
     * @see fr.utt.lo02.projet.controller.GameController
     */
    @Override
    public void setController(GameController controller)
    {
        this.controller = controller;
    }

    /**
     * Returns the user choice as an integer
     * <p>
     * The value is Integer.MIN_VALUE if the read task is interrupted
     *
     * @param msg the message to prompt
     * @param min the minimum value for the integer choice
     * @param max the maximum value for the integer choice
     * @return a number between min and max
     * @see ConsoleInputReadTask
     */
    private int getNumber(String msg, int min, int max)
    {

        ConsoleInputReadTask readTask = new ConsoleInputReadTask();
        Integer input = null;
        try
        {
            input = readTask.getInt(msg, min, max);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        if (input == null)
        {
            input = Integer.MIN_VALUE;
        }
        return input;
    }

    /**
     * Returns the user choice as an integer
     * <p>
     * The value is Integer.MIN_VALUE if the read task is interrupted
     *
     * @param msg the message to prompt
     * @return a number corresponding to the choice
     * @see ConsoleInputReadTask
     */
    private int getNumber(String msg)
    {

        ConsoleInputReadTask readTask = new ConsoleInputReadTask();
        Integer input = null;
        try
        {
            input = readTask.getInt(msg);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        if (input == null)
        {
            input = Integer.MIN_VALUE;
        }
        return input;
    }
}
