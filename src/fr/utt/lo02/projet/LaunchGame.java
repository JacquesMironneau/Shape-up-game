package fr.utt.lo02.projet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.CircleBoard;
import fr.utt.lo02.projet.board.RectangleBoard;
import fr.utt.lo02.projet.board.boardEmptyException;
import fr.utt.lo02.projet.board.visitor.ScoreCalculatorVisitor;
import fr.utt.lo02.projet.game.ShapeUpGame;
import fr.utt.lo02.projet.game.ShapeUpGameAdvanced;
import fr.utt.lo02.projet.strategy.PlayerHandEmptyException;
import fr.utt.lo02.projet.strategy.PlayerStrategy;
import fr.utt.lo02.projet.strategy.RealPlayer;
import fr.utt.lo02.projet.strategy.VirtualPlayer;

public class LaunchGame {

	public static void main(String[] args) throws PlayerHandEmptyException, boardEmptyException
	{
		try ( Scanner scanner = new Scanner( System.in ) ) {
			System.out.println("-------------ShapeUpGame-------------");
			System.out.println("Welcome !");
			boolean replay = true;
			while (replay) {
				System.out.println("Do you want to display the rules ?");
				System.out.println("1. Yes");
				System.out.println("2. No");
				int choiceRulesOrNot = scanner.nextInt();
				switch(choiceRulesOrNot) {
					case 1:
						System.out.println("RULES blablablablablabla");
					case 2:
					//default: System.err.println("Please enter a correct answer.");
				} 
				System.out.println("What game mode do you want to play ?");
				System.out.println("1. Normal mode");
				System.out.println("2. Advanced mode");
				int choiceMode = scanner.nextInt();
				switch (choiceMode) {
					case 1, 2:
					//default: System.err.println("Please enter a correct answer.");
				}
				System.out.println("What form of board do you want to play ?");
				System.out.println("1. Rectangle board");
				System.out.println("2. Circle board ");
				int choiceBoard = scanner.nextInt();
				AbstractBoard board = null;
				switch (choiceBoard) {
					case 1: board = new RectangleBoard();
					case 2: board = new CircleBoard();
					//default: System.err.println("Please enter a correct answer.");
				}
				System.out.println("How many players do you want for the game ?");
				System.out.println("1. 2 Players");
				System.out.println("2. 3 Players");
				int choiceNumberPlayers = scanner.nextInt();
				List<PlayerStrategy> players = new ArrayList<PlayerStrategy>();
				for (int i=1; i<=choiceNumberPlayers+1; i++) {
					System.out.println("Is Player " + i + " a real or a virtual player ? ");
					System.out.println("1. Real");
					System.out.println("2. Virtual");
					int choiceRealOrVirtual = scanner.nextInt();
					switch (choiceRealOrVirtual) {
						case 1:
							System.out.println("What is your name ? ");
							String name = scanner.next();
							players.add(new RealPlayer(name, board));
						case 2:
							players.add(new VirtualPlayer("User" + i, board));
						//default:	System.err.println("Please enter a correct answer.");
					}
				}
				switch (choiceMode) {
					case 1: new ShapeUpGameAdvanced(new ScoreCalculatorVisitor(), players, board).playGame();
					case 2: new ShapeUpGame(new ScoreCalculatorVisitor(), players, board).playGame();
				}
				System.out.println("Do you want to replay ?");
				System.out.println("1. Yes");
				System.out.println("2. No");
				int choiceReplay = scanner.nextInt();
				switch (choiceReplay) {
					case 1: replay=true;
					case 2: 
						System.out.println("Thanks for playing !");
						System.out.println("See you soon !");
						System.out.println("Credits: Jacques Mironneau, Baptiste Guichard");
						replay=false;
					//default: System.err.println("Please enter a correct answer.");
				}
			}			
		}
	}
}
