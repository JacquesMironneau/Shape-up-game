package fr.utt.lo02.projet.strategy;

import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import fr.utt.lo02.projet.game.GameController;
import fr.utt.lo02.projet.game.GameState;

public class InitConsoleView implements InitView {

	public static final String EASY = "easy";
	public static final String MEDIUM = "medium";
	private InitController controller;
	
	private final Scanner scan;
	
	public InitConsoleView() {
		scan = new Scanner(System.in);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		InitState is = (InitState) evt.getNewValue();
		
		switch (is)
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
				
				this.controller.startMenu(choice);
				
			}
			case RULES -> {
				int back;
				System.out.println("Rules are too complicated to be explained.\n");
				
				back = getNumber("Enter 0 to go back to the main menu.", 0, 0);
				
				this.controller.startMenu(back);
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
				//System.out.println("Music: Marceau Canu. \n");
				
				back = getNumber("Enter 0 to go back to the main menu.", 0, 0);
				
				this.controller.startMenu(back);
			}
			case GAME_MODE_CHOICE -> {
				int choice;
				
				System.out.println("Welcome in Shape Up ! Please set up your game: ");
				System.out.println("What game mode do you want to play ?");
				System.out.println("1. Normal");
				System.out.println("2. Advanced");
				System.out.println("3. NoAdjacency \n");
				System.out.println("Enter 0 to go back to the main menu. ");
				choice = getNumber("Please enter your choice: ", 0, 3);
				
				this.controller.setGameMode(choice);
			}
			case SCORE_CALCULATOR_CHOICE -> {
				int choice;
				
				System.out.println("What score calculator do you want to choose ? ");
				System.out.println("1. Normal calculator");
				System.out.println("2. Bonus calculator \n");
				System.out.println("Enter 0 to go back to the game mode choice. ");
				choice = getNumber("Please enter your choice: ", 0, 2);
				
				this.controller.setScoreCalculator(choice);
			}
			case SHAPE_BOARD_CHOICE -> {
				
				int choice;
				
				System.out.println("Which shape of board would you like to play with ?");
				System.out.println("1. Rectangle board");
				System.out.println("2. Triangle board ");
				System.out.println("3. Circle board \n");
				System.out.println("Enter 0 to go back to the score calculator choice. ");
				choice = getNumber("Please enter your choice: ", 0, 3);
				
				this.controller.shapeBoard(choice);
			}
			case PLAYER_CHOICE -> {
				int choice;
				System.out.println("How many players do you want for the game ?");
				System.out.println("2 Players");
				System.out.println("3 Players \n");
				System.out.println("Enter 0 to go back to the shape board choice. ");
				choice = getNumber("Please enter your choice: ", 0, 3);
				Map<Integer, String> realPlayers = new HashMap<Integer, String>();
				Map<Integer, String> virtualPlayers = new HashMap<Integer, String>();
				int virtualNumber = 1;
				for (int i = 1; i <= choice; i++)
				{
					System.out.println("Is Player " + i + " a real or a virtual player ? ");
					System.out.println("1. Real");
					System.out.println("2. Virtual");
					int choiceRealOrVirtual = getNumber("Please enter your choice: ", 1, 2);
					switch (choiceRealOrVirtual)
					{
						case 1:
							System.out.println("What is your name ? ");
							String name = "User";
							name = scan.next();
							realPlayers.put(i, name);
							break;
						case 2:
							String strategy = "";
							System.out.println("Which difficulty do you want for player " + i + " ?");
							System.out.println("1. Easy");
							System.out.println("2. Medium");
							int choiceEasyOrMedium = getNumber("Please enter your choice: ", 1, 2);
							switch (choiceEasyOrMedium) 
							{
							case 1: 
								strategy= EASY;
								break;
							case 2:
								strategy= MEDIUM;
								break;
							}
							virtualPlayers.put(i, strategy);
							virtualNumber++;
							break;
						default:
							System.exit(0);
					}
				}
				this.controller.setPlayer(realPlayers, virtualPlayers);
			}
			case INIT_DONE -> {
				this.controller.launch();
			}
		}
	}
	
	public void setController(InitController controller)
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
				}
				else
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
	
}
