package fr.utt.lo02.projet.strategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.math.*;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.board.RectangleBoard;
import fr.utt.lo02.projet.game.AbstractShapeUpGame;

/**
 * Represent the strategy for a virtual player.
 * It implements Player Strategy to follow the player's construction.
 * @author Baptiste, Jacques
 *
 */

public class VirtualPlayer implements PlayerStrategy
{


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
	public Request askPlaceCard(Set<Card> playerHand, Map<Coordinates, Card> cards)
	{
		Card card = null;
		do {
			int randomCard = 1 + (int)(Math.random() * ((3 - 1) + 1));
			for (int i=0; i<randomCard; i++) {
				Iterator<Card> it = playerHand.iterator();
				while (it.hasNext()) {
					card = it.next();
				}			
			}
		} while (card != null);
		
		Coordinates randomCoord;
		List <Coordinates> coordsMap = new ArrayList<Coordinates>();
		int i=0;
		for (Map.Entry<Coordinates, Card> entry : cards.entrySet()) {
			coordsMap.set(i, entry.getKey());;
			i++;
		}
		int randomX = (Coordinates.smallestAbscissa(coordsMap)-1) + (int)(Math.random() * (((Coordinates.biggestAbscissa(coordsMap)+1) - (Coordinates.smallestAbscissa(coordsMap)-1)) + 1));
		int randomY = (Coordinates.smallestOrdinate(coordsMap)-1) + (int)(Math.random() * (((Coordinates.biggestOrdinate(coordsMap)+1) - (Coordinates.smallestOrdinate(coordsMap)-1)) + 1));
		randomCoord = new Coordinates(randomX, randomY);
		
			
		Request request = new Request(randomCoord, card);
		return request;

	}

	@Override
	public Request askMoveCard(Map<Coordinates, Card> cards)
	{
		Coordinates randomCoord;
		List <Coordinates> coordsMap = new ArrayList<Coordinates>();
		int i=0;
		for (Map.Entry<Coordinates, Card> entry : cards.entrySet()) {
			coordsMap.set(i, entry.getKey());;
			i++;
		}
		do {
		int randomX = (Coordinates.smallestAbscissa(coordsMap)) + (int)(Math.random() * (((Coordinates.biggestAbscissa(coordsMap)) - (Coordinates.smallestAbscissa(coordsMap))) + 1));
		int randomY = (Coordinates.smallestOrdinate(coordsMap)) + (int)(Math.random() * (((Coordinates.biggestOrdinate(coordsMap)) - (Coordinates.smallestOrdinate(coordsMap))) + 1));
		randomCoord = new Coordinates(randomX, randomY);
		} while (cards.get(randomCoord)==null);
		Card cardToMove = cards.get(randomCoord);
		
		int randomX2 = (Coordinates.smallestAbscissa(coordsMap)-1) + (int)(Math.random() * (((Coordinates.biggestAbscissa(coordsMap)+1) - (Coordinates.smallestAbscissa(coordsMap)-1)) + 1));
		int randomY2 = (Coordinates.smallestOrdinate(coordsMap)-1) + (int)(Math.random() * (((Coordinates.biggestOrdinate(coordsMap)+1) - (Coordinates.smallestOrdinate(coordsMap)-1)) + 1));
		Coordinates randomCoord2 = new Coordinates(randomX2, randomY2);

		Request request = new Request(randomCoord2, cardToMove);
		return request;
	}

	@Override
	public void displayBoard()
	{
		

	}

	@Override
	public void displayRoundScore(int score)
	{
		System.out.println("Score : "+ score);
	}
	
	@Override
	public void displayFinalScoreForThisRound (int score, int playerNumber) {
		System.out.println("Player " + playerNumber + " : Final Score for this round -> " + score);
	}

	@Override
	public void displayFinalScore(List<Integer> scoresRound, int playerNumber) {
			int roundNumber=1;
			int finalScore=0;
			for (int scores: scoresRound) {
				System.out.println("Player " + playerNumber + " : Score for Round " + roundNumber + " -> " + scores);
				roundNumber++;
				finalScore += scores;
			}
			System.out.println("Player " + playerNumber + " : FINAL SCORE = " + finalScore);
	}

	public void main(String[] args) {
	}

}
