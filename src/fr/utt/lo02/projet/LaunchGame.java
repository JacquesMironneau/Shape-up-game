package fr.utt.lo02.projet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.CircleBoard;
import fr.utt.lo02.projet.board.RectangleBoard;
import fr.utt.lo02.projet.board.TriangleBoard;
import fr.utt.lo02.projet.board.boardEmptyException;
import fr.utt.lo02.projet.board.visitor.ScoreCalculatorVisitor;
import fr.utt.lo02.projet.game.ShapeUpGame;
import fr.utt.lo02.projet.game.ShapeUpGameAdvanced;
import fr.utt.lo02.projet.game.ShapeUpGameWithoutAdajencyRule;
import fr.utt.lo02.projet.strategy.PlayerHandEmptyException;
import fr.utt.lo02.projet.strategy.PlayerStrategy;
import fr.utt.lo02.projet.strategy.RealPlayer;
import fr.utt.lo02.projet.strategy.VirtualPlayer;

public class LaunchGame {

	 private static Scanner scanner = new Scanner( System.in );
	 
	public static void main(String[] args) throws PlayerHandEmptyException, boardEmptyException
	{
			boolean quit = false;
			while (quit==false) {
				System.out.println("-------------SHAPE UP-------------");
				System.out.println("1. Play");
				System.out.println("2. Rules");
				System.out.println("3. Quit");
				int menuChoice = readInt(3);
				switch (menuChoice) {
					case 1:
						System.out.println("Welcome in Shape Up ! Before to play, you have to set up your game: ");
						System.out.println("What game mode do you want to play ?");
						System.out.println("1. Normal");
						System.out.println("2. Advanced");
						System.out.println("3. NoAdajency");
						int choiceMode = readInt(3);
						switch (choiceMode) {
							case 1, 2, 3: break;
							default: 
								System.exit(0);
						}
						System.out.println("What form of board do you want to play ?");
						System.out.println("1. Rectangle board");
						System.out.println("2. Circle board ");
						System.out.println("3. Triangle board ");
						int choiceBoard = readInt(3);
						AbstractBoard board = null;
						switch (choiceBoard) {
							case 1:
								board = new RectangleBoard();
								break;
							case 2:
								board = new CircleBoard();
								break;
							case 3:
								board = new TriangleBoard();
								break;
							default:
								System.exit(0);
						}
						System.out.println("How many players do you want for the game ?");
						System.out.println("1. 2 Players");
						System.out.println("2. 3 Players");
						int choiceNumberPlayers = readInt(2);
						List<PlayerStrategy> players = new ArrayList<PlayerStrategy>();
						int virtualNumber=1;
						for (int i=1; i<=choiceNumberPlayers+1; i++) {
							System.out.println("Is Player " + i + " a real or a virtual player ? ");
							System.out.println("1. Real");
							System.out.println("2. Virtual");
							int choiceRealOrVirtual = readInt(2);
							switch (choiceRealOrVirtual) {
								case 1:
									System.out.println("What is your name ? ");
									String name = "User";
									name = scanner.next();
									players.add(new RealPlayer(name, board));
									break;
								case 2:
									players.add(new VirtualPlayer("User" + virtualNumber, board));
									virtualNumber++;
									break;
								default:	
									System.exit(0);
							}
						}
						switch (choiceMode) {
							case 1: 
								new ShapeUpGame(new ScoreCalculatorVisitor(), players, board).playGame();
								break;
							case 2: 
								new ShapeUpGameAdvanced(new ScoreCalculatorVisitor(), players, board).playGame();
								break;
							case 3: 
								new ShapeUpGameWithoutAdajencyRule(new ScoreCalculatorVisitor(), players, board).playGame();
								break;
						}
						break;
					case 2: 
						System.out.println("RULES blablablablablabla");
						break;
					case 3:
						System.out.println("Thanks for playing !");
						System.out.println("See you soon !");
						System.out.println("Credits: Jacques Mironneau, Baptiste Guichard");
						quit=true;
						break;
					default:
						System.exit(0);
				}
			}			
	}
	public static int readInt(int possibilities) {
			int choice=0;
			switch (possibilities) {
			case 2:
				do {
					choice = scanner.nextInt();
					if (choice!=1 && choice!=2) {
						System.err.println("Please enter a correct answer.");
					}
				} while (choice!=1 && choice!=2);
				break;
			case 3:
				do {
					choice = scanner.nextInt();
					if (choice!=1 && choice!=2 && choice!=3) {
						System.err.println("Please enter a correct answer.");
					}
				} while (choice!=1 && choice!=2 && choice!=3);
				break;
			default: break;
			}
			
			return choice;
	}
}
