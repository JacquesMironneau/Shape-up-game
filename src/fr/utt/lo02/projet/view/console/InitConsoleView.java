package fr.utt.lo02.projet.view.console;

import fr.utt.lo02.projet.controller.InitController;
import fr.utt.lo02.projet.model.InitState;
import fr.utt.lo02.projet.view.InitView;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Console implementation of InitView
 * <p>
 * Player interactions are hence console displayed text and integer reading from the terminal
 */
public class InitConsoleView implements InitView
{

    /**
     * Choice for the easy level of difficulty for the virtual player
     */
    public static final String EASY = "easy";

    /**
     * Choice for the medium level of difficulty for the virtual player
     */
    public static final String MEDIUM = "medium";

    /**
     * Controller of the init view (MVC pattern)
     */
    private InitController controller;

    /**
     * Console processing thread
     * <p>
     * This is the thread that can be interrupted by the controller in order to cancel
     * input reading from the console
     */
    private Thread thread;

    /**
     * Scanner, used to read players data
     */
    private final Scanner scan;

    /**
     * Set up the scanner
     */
    public InitConsoleView()
    {
        scan = new Scanner(System.in);
    }

    /**
     * Observer pattern, called by a state change in the model
     *
     * @param evt the fired change
     * @see fr.utt.lo02.projet.model.InitModel
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {

        InitState is = (InitState) evt.getNewValue();
        thread = new Thread(() -> stateCheck(is));
        thread.start();

    }

    /**
     * Displays and asks user to set up a game according to the init state
     *
     * @param initState current init state of the model
     */
    private void stateCheck(InitState initState)
    {
        switch (initState)
        {

            case START_MENU -> {

                int choice;

                System.out.println(" ____    _                                _   _         ");
                System.out.println("/ ___|  | |__     __ _   _ __     ___    | | | |  _ __  ");
                System.out.println("\\___ \\  | '_ \\   / _` | | '_ \\   / _ \\   | | | | | '_ \\ ");
                System.out.println(" ___) | | | | | | (_| | | |_) | |  __/   | |_| | | |_) |");
                System.out.println("|____/  |_| |_|  \\__,_| | .__/   \\___|    \\___/  | .__/ ");
                System.out.println("                        |_|                      |_|    ");

                System.out.println("1. Play");
                System.out.println("2. Rules");
                System.out.println("3. Credits");
                System.out.println("4. Quit");

                choice = getNumber("Please enter your choice: ", 1, 4);

                if (choice != Integer.MIN_VALUE)
                {
                    this.controller.startMenu(choice);

                }

            }
            case RULES -> {
                int back;
                System.out.println("Rules are too complicated to be explained.\n");

                back = getNumber("Enter 0 to go back to the main menu.", 0, 0);
                if (back != Integer.MIN_VALUE)
                {
                    this.controller.startMenu(back);

                }

            }
            case QUIT -> {
                System.out.println("Thanks for playing !");
                System.out.println("See you soon !");

                this.controller.quit();
            }
            case CREDITS -> {
                int back;
                System.out.println("Code: Jacques Mironneau and Baptiste Guichard.");
                System.out.println("Graphics: Thomas Durand.");
                System.out.println("Music: Marceau Canu. \n");

                back = getNumber("Enter 0 to go back to the main menu.", 0, 0);

                if (back != Integer.MIN_VALUE)
                {
                    this.controller.startMenu(back);

                }
            }
            case GAME_MODE_CHOICE -> {
                int choice;

                System.out.println("Welcome in Shape Up ! Please set up your game: ");
                System.out.println("What game mode do you want to play ?");
                System.out.println("1. Normal");
                System.out.println("2. Advanced");
                System.out.println("3. NoAdjacency \n");
                System.out.println("Enter 0 to go back to the main menu. ");
                choice = getNumber("Please enter your game mode choice: ", 0, 3);

                if (choice != Integer.MIN_VALUE)
                {
                    this.controller.setGameMode(choice);

                }
            }
            case SCORE_CALCULATOR_CHOICE -> {
                int choice;

                System.out.println("What score calculator do you want to choose ? ");
                System.out.println("1. Normal calculator");
                System.out.println("2. Bonus calculator \n");
                System.out.println("Enter 0 to go back to the game mode choice. ");
                choice = getNumber("Please enter your score choice: ", 0, 2);

                if (choice != Integer.MIN_VALUE)
                {
                    this.controller.setScoreCalculator(choice);
                }
            }
            case SHAPE_BOARD_CHOICE -> {

                int choice;

                System.out.println("Which shape of board would you like to play with ?");
                System.out.println("1. Rectangle board");
                System.out.println("2. Triangle board ");
                System.out.println("3. Circle board \n");
                System.out.println("Enter 0 to go back to the score calculator choice. ");

                choice = getNumber("Please enter your board choice: ", 0, 3);


                if (choice != Integer.MIN_VALUE)
                {
                    this.controller.shapeBoard(choice);

                }
            }
            case PLAYER_CHOICE -> {
                int choice;
                System.out.println("How many players do you want for the game ?");
                System.out.println("2 Players");
                System.out.println("3 Players \n");
                System.out.println("Enter 0 to go back to the shape board choice. ");
                choice = getNumber("Please enter your choice: ", 0, 3);
                this.controller.setNbPlayers(choice);
                Map<Integer, String> realPlayers = new HashMap<>();
                Map<Integer, String> virtualPlayers = new HashMap<>();
                for (int i = 1; i <= choice; i++)
                {
                    System.out.println("Is Player " + i + " a real or a virtual player ? ");
                    System.out.println("1. Real");
                    System.out.println("2. Virtual");
                    int choiceRealOrVirtual = getNumber("Please enter the type of player: ", 1, 2);
                    if (choiceRealOrVirtual == 1)
                    {
                        System.out.println("What is your name ? ");
                        String name;
                        name = scan.next();
                        realPlayers.put(i, name);
                    } else if (choiceRealOrVirtual == 2)
                    {
                        String strategy = "";
                        System.out.println("Which difficulty do you want for player " + i + " ?");
                        System.out.println("1. Easy");
                        System.out.println("2. Medium");
                        int choiceEasyOrMedium = getNumber("Please enter the difficulty: ", 1, 2);
                        switch (choiceEasyOrMedium)
                        {
                            case 1 -> strategy = EASY;
                            case 2 -> strategy = MEDIUM;
                        }
                        virtualPlayers.put(i, strategy);
                    } else
                    {
                        System.exit(0);
                    }
                }
                this.controller.setPlayer(realPlayers, virtualPlayers);
            }
            case INIT_DONE -> this.controller.launch();
        }
    }

    /**
     * Sets a controller to the view
     *
     * @param controller the controller
     * @see fr.utt.lo02.projet.controller.InitController
     */
    public void setController(InitController controller)
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
     * Returns the thread of the reading task
     *
     * @return the thread of input task
     * @see ConsoleInputReadTask
     */
    public Thread getThread()
    {
        return thread;
    }
}
