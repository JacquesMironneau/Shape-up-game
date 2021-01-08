package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.GameView;
import fr.utt.lo02.projet.board.*;
import fr.utt.lo02.projet.board.visitor.ScoreCalculatorVisitor;
import fr.utt.lo02.projet.game.*;

import java.beans.PropertyChangeEvent;
import java.util.*;

public class GameConsoleView implements GameView
{
    private GameController controller;

    private final AbstractShapeUpGame model;

    private final AbstractBoard boardModel;

    private final Scanner scan;


    public GameConsoleView(AbstractShapeUpGame model, AbstractBoard boardModel)
    {
        this.model = model;
        this.boardModel = boardModel;
        scan = new Scanner(System.in);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        System.out.println("notified" + evt.getNewValue() + " " + evt.getPropertyName());

        GameState gs = (GameState) evt.getNewValue();
        System.out.println(gs);

        switch (gs)
        {

            case MOVE -> {
                displayVictoryCard();
                displayBoard();
                int x, y, x2, y2;
                System.out.println("You have to enter origin coordinates and destination coordinates.");

                x = getNumber("Please enter X origin : ");
                y = getNumber("Please enter Y origin : ");
                x2 = getNumber("Please enter X dest : ");
                y2 = getNumber("Please enter Y dest: ");

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
                y = getNumber("Please enter Y pos : ");

                if (this.model.getCurrentPlayer().getPlayerHand().size() > 1)
                {
                    cardIndex = getNumber("Please enter your card index: ");
                    cardIndex--;
                }
                this.controller.askPlace(x, y, cardIndex);
            }
            case PLACE_DONE, MOVE_DONE -> {
                displayBoard();
            }
            case ACTION_FAILED -> {
                System.err.println("Action failed, please retry...");
            }
            case FIRST_TURN -> {
                this.controller.play();
            }
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
                this.controller.askChoice(1, choice);
            }
            case SECOND_CHOICE -> {
                int choice = getNumber("Do you want to move a card ?\n 1. Yes (move a card)\n 2. No (end the turn)", 1, 2);
                displayBoard();
                displayHand();
                this.controller.askChoice(2, choice);
            }
            case END_TURN -> {
                System.out.println("end turn");
                this.controller.endTurn();
            }
            case CARD_DRAW -> {
                System.out.println(model.getCurrentPlayer().getName() + " you have draw");
//				Card.printSingleCard(model.getCurrentPlayer().getDrawCard());
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
                System.exit(0);
            }
        }


    }


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

    // TODO: move the display in the view
    public void displayHand()
    {
        System.out.println(model.getCurrentPlayer().getPlayerHand().size() + " card(s) in hand\t");

        model.getCurrentPlayer().getPlayerHand().forEach(Card::printSingleCard);
    }

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
//        if (model.getRoundNumber() != 3)
//        {
//            this.controller.play();

//        }
    }

    // TODO: move the display in the View
    public void displayBoard()
    {
        boardModel.display();
    }

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

    public void displayMoveFailed(PlaceRequestResult prr)
    {
        switch (prr)
        {
            case PLAYER_DOESNT_OWN_CARD -> System.err.println("You don't own this card");
            case CARD_NOT_ADJACENT -> System.err.println("This location is not adjacent to an already existing card");
            case CARD_NOT_IN_THE_LAYOUT -> System.err.println("The card is too far from the layout");
        }
    }

    public void displayPlaceFailed(MoveRequestResult mrr)
    {
        switch (mrr)
        {
            case NO_CARD_IN_THE_ORIGIN_COORDINATE -> System.err.println("You can't move an nonexistent card");
            case CARD_NOT_ADJACENT -> System.err.println("This card will not be adjacent to any card");
            case CARD_NOT_IN_THE_LAYOUT -> System.err.println("The card will be too far from the layout");
            case ORIGIN_AND_DESTINATION_ARE_EQUAL -> System.err.println("Moving the card to its exact location has never been a good idea");
        }
    }

    public void setController(GameController controller)
    {
        this.controller = controller;
    }


    private int getNumber(String msg, int min, int max)
    {
        int choice = -1;
        boolean isChoice = true;
        while (isChoice)
        {
            System.out.println(msg);

            try
            {
                choice = scan.nextInt();
                if (choice >= min && choice <= max)
                {
                    isChoice = false;
                } else
                {
                    System.err.println("Please enter a number between " + min + " and " + max);

                }
            } catch (Exception exception)
            {
                System.err.println("Please enter a correct number...");
            }
            scan.nextLine();
        }

        return choice;

    }

    private int getNumber(String msg)
    {
        int choice = -1;
        boolean isChoice = true;
        while (isChoice)
        {
            System.out.println(msg);

            try
            {
                choice = scan.nextInt();
                isChoice = false;
            } catch (Exception exception)
            {
                System.err.println("Please enter a correct number...");
            }
            scan.nextLine();
        }

        return choice;

    }

    public static void main(String[] args) throws PlayerHandEmptyException, BoardEmptyException
    {
        List<Player> ps = new ArrayList<>();
        AbstractBoard rb = new RectangleBoard();
//		ps.add(new RealPlayer("Jacques", rb));
//		ps.add(new RealPlayer("Théo", rb));
//		ps.add(new RealPlayer("Th1", rb));
//		ps.add(new RealPlayer("Aaa", rb));
        ScoreCalculatorVisitor visitor = new ScoreCalculatorVisitor();

        ps.add(new VirtualPlayer("ord1", rb, new RandomStrategy()));
 //       ps.add(new VirtualPlayer("ord3", rb, new RandomStrategy()));


//		ps.add(new RealPlayer("Baptiste", rb));

        ps.add(new VirtualPlayer("ord2", rb, new DifficultStrategy(visitor)));
//		ps.add(new VirtualPlayer("ord3", rb, new RandomStrategy()));
        AbstractShapeUpGame model = new ShapeUpGame(visitor, ps, rb);
        GameView view = new GameConsoleView(model, rb);


        Set<GameView> gvs = new HashSet<>();
        gvs.add(view);
        GameController sugc = new ShapeUpGameController(model, gvs);
        view.setController(sugc);
        model.addPropertyChangeListener(view);

        model.setState(GameState.FIRST_TURN);

    }


}
