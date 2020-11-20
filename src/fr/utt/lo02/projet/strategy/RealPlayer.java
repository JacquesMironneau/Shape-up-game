package fr.utt.lo02.projet.strategy;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;

/**
 * Represent the strategy for a real player.
 * It implements Player Strategy to follow the player's construction.
 * @author Baptiste, Jacques
 *
 */


public class RealPlayer implements PlayerStrategy
{

	private List<Card> playerHand;
	private List<Integer> scoresRound;
	private Card victoryCard;
	private AbstractBoard board;
	
	public RealPlayer(AbstractBoard b) {
		this.board = b;
		this.scoresRound = new ArrayList<>();
		this.playerHand = new ArrayList<>();
	}
	
	@Override
	public Choice askChoice()
	{
		try ( Scanner scanner = new Scanner( System.in ) ) {
			System.out.println( "Please choose one action : " );
			System.out.println( "1. Move a Card" );
			System.out.println( "2. Place a Card" );
			System.out.println( "3. End the turn" );
            int choice = scanner.nextInt();
            if (choice == 1) {
    			return Choice.MOVE_A_CARD;
    		} else if (choice == 2) {
    			return Choice.PLACE_A_CARD;
    		} else {
    			return Choice.END_THE_TURN;
    		}
		}
	}

	@Override
	public PlaceRequest askPlaceCard()
	{
		int choiceCard;
		try ( Scanner scanner = new Scanner( System.in ) ) {
			if (playerHand.size()==1) {
				choiceCard=1;
			} else {
				System.out.println( "Please choose one card from your Hand : " );
				for (int i=0; i<playerHand.size(); i++) {
					System.out.println((i+1)+ ". " + playerHand.get(i));
				}
				choiceCard = scanner.nextInt();
			}
		}
		Card card = playerHand.get(choiceCard);
		
		int scanX, scanY;
		try ( Scanner scanner = new Scanner( System.in ) ) {
			System.out.println( "You have to enter coordinates for where you want to place this card. " );
			System.out.println( "Please enter X pos : " );
			scanX = scanner.nextInt();
			System.out.println( "Please enter Y pos : " );
			scanY = scanner.nextInt();
		}
		Coordinates scanCoord = new Coordinates(scanX, scanY);
		PlaceRequest request = new PlaceRequest(scanCoord, card);
		return request;
	}

	@Override
	public MoveRequest askMoveCard()
	{
		List <Coordinates> coordsMap = new ArrayList<Coordinates>();
		int i=0;
		for (Map.Entry<Coordinates, Card> entry : board.getPlacedCards().entrySet()) {
			coordsMap.add(i, entry.getKey());
			i++;
		}
		int scanX1, scanY1;
		Coordinates scanCoord1;
		do {
			try ( Scanner scanner = new Scanner( System.in ) ) {
				System.out.println( "You have to enter coordinates of the card you want to move. " );
				System.out.println( "Please enter X pos : " );
				scanX1 = scanner.nextInt();
				System.out.println( "Please enter Y pos : " );
				scanY1 = scanner.nextInt();
			}
		scanCoord1 = new Coordinates(scanX1, scanY1);
		} while (board.getPlacedCards().get(scanCoord1)==null);
		
		int scanX2, scanY2;
		try ( Scanner scanner = new Scanner( System.in ) ) {
			System.out.println( "You have to enter coordinates for where you want to move this card. " );
			System.out.println( "Please enter X pos : " );
			scanX2 = scanner.nextInt();
			System.out.println( "Please enter Y pos : " );
			scanY2 = scanner.nextInt();
		}
		Coordinates scanCoord2 = new Coordinates(scanX2, scanY2);

		MoveRequest request = new MoveRequest(scanCoord1, scanCoord2);
		return request;
	}

	@Override
	public void displayRoundScore(int roundNumber)
	{
		int score = scoresRound.get(roundNumber-1);
		System.out.println("Score : "+ score);

	}
	
	@Override
	public void displayFinalScoreForThisRound (int roundNumber, int playerNumber) {
		int score = scoresRound.get(roundNumber-1);
		System.out.println("Player " + playerNumber + " : Final Score for this round -> " + score);
	}

	@Override
	public void displayFinalScore(int playerNumber) {
			int roundNumber=1;
			int finalScore=0;
			for (int scores: scoresRound) {
				System.out.println("Player " + playerNumber + " : Score for Round " + roundNumber + " -> " + scores);
				roundNumber++;
				finalScore += scores;
			}
			System.out.println("Player " + playerNumber + " : FINAL SCORE = " + finalScore);
	}

	@Override
	public void setVictoryCard(Card victoryCard)
	{
		this.victoryCard = victoryCard;
	}

	@Override
	public void drawCard(Card card)
	{
		this.playerHand.add(card);
	}

	@Override
	public void addRoundScore(int scoreOfCurrentRound)
	{
		this.scoresRound.add(scoreOfCurrentRound);
	}

	@Override
	public Card getVictoryCard()
	{
		return victoryCard;
	}

	public List<Card> getPlayerHand()
	{
		return playerHand;
	}
}
