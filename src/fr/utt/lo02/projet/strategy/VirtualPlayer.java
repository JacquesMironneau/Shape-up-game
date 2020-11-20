package fr.utt.lo02.projet.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;

/**
 * Represent the strategy for a virtual player.
 * It implements Player Strategy to follow the player's construction.
 * @author Baptiste, Jacques
 *
 */

public class VirtualPlayer implements PlayerStrategy
{
	
	public List<Card> playerHand;
	public List<Integer> scoresRound;
	public Card victoryCard;
	protected AbstractBoard board;
	
	public VirtualPlayer(AbstractBoard b, Card vC, List<Integer> sR, List<Card> pH) {
		this.board = b;
		this.victoryCard = vC;
		this.scoresRound = sR;
		this.playerHand = pH;
	}

	@Override
	public Choice askChoice()
	{
		int randomNumber = 1 + (int)(Math.random() * ((3 - 1) + 1));
		if (randomNumber == 1) {
			return Choice.MOVE_A_CARD;
		} else if (randomNumber == 2) {
			return Choice.PLACE_A_CARD;
		} else {
			return Choice.END_THE_TURN;
		}
	}

	@Override
	public PlaceRequest askPlaceCard()
	{
		Card card = null;
		if (playerHand.size()==1) {
			card = playerHand.get(0);	
		} else if (playerHand.size()==2){
			int randomCard = 0 + (int)(Math.random() * ((1 - 0) + 1));
			card = playerHand.get(randomCard);	
		} else {
			int randomCard = 0 + (int)(Math.random() * ((2 - 0) + 1));
			card = playerHand.get(randomCard);	
		}	
		
		Coordinates randomCoord;
		List <Coordinates> coordsMap = new ArrayList<Coordinates>();
		int i=0;
		for (Map.Entry<Coordinates, Card> entry : board.getPlacedCards().entrySet()) {
			coordsMap.add(i, entry.getKey());;
			i++;
		}
		int randomX = (Coordinates.smallestAbscissa(coordsMap)-1) + (int)(Math.random() * (((Coordinates.biggestAbscissa(coordsMap)+1) - (Coordinates.smallestAbscissa(coordsMap)-1)) + 1));
		int randomY = (Coordinates.smallestOrdinate(coordsMap)-1) + (int)(Math.random() * (((Coordinates.biggestOrdinate(coordsMap)+1) - (Coordinates.smallestOrdinate(coordsMap)-1)) + 1));
		randomCoord = new Coordinates(randomX, randomY);
		
			
		PlaceRequest request = new PlaceRequest(randomCoord, card);
		return request;

	}

	@Override
	public MoveRequest askMoveCard()
	{
		List <Coordinates> coordsMap = new ArrayList<Coordinates>();
		int i=0;
		for (Map.Entry<Coordinates, Card> entry : board.getPlacedCards().entrySet()) {
			coordsMap.add(i, entry.getKey());;
			i++;
		}
		Coordinates randomCoord1;
		do {
		int randomX = (Coordinates.smallestAbscissa(coordsMap)) + (int)(Math.random() * (((Coordinates.biggestAbscissa(coordsMap)) - (Coordinates.smallestAbscissa(coordsMap))) + 1));
		int randomY = (Coordinates.smallestOrdinate(coordsMap)) + (int)(Math.random() * (((Coordinates.biggestOrdinate(coordsMap)) - (Coordinates.smallestOrdinate(coordsMap))) + 1));
		randomCoord1 = new Coordinates(randomX, randomY);
		} while (board.getPlacedCards().get(randomCoord1)==null);
		
		int randomX2 = (Coordinates.smallestAbscissa(coordsMap)-1) + (int)(Math.random() * (((Coordinates.biggestAbscissa(coordsMap)+1) - (Coordinates.smallestAbscissa(coordsMap)-1)) + 1));
		int randomY2 = (Coordinates.smallestOrdinate(coordsMap)-1) + (int)(Math.random() * (((Coordinates.biggestOrdinate(coordsMap)+1) - (Coordinates.smallestOrdinate(coordsMap)-1)) + 1));
		Coordinates randomCoord2 = new Coordinates(randomX2, randomY2);

		MoveRequest request = new MoveRequest(randomCoord1, randomCoord2);
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
	
}
