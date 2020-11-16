package fr.utt.lo02.projet.strategy;

import java.util.Map.Entry;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.board.RectangleBoard;
import fr.utt.lo02.projet.game.AbstractShapeUpGame;

/**
 * Represent the strategy for a real player.
 * It implements Player Strategy to follow the player's construction.
 * @author Baptiste, Jacques
 *
 */


public class RealPlayer implements PlayerStrategy
{

	@Override
	public Choice askChoice()
	{
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public Request askPlaceCard(Set<Card> playerHand, Map<Coordinates, Card> cards)
	{
		// TODO pick card and x,y
		return null;
	}

	@Override
	public Request askMoveCard(Map<Coordinates, Card> cards)
	{
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public void displayBoard()
	{
		// TODO Auto-generated method stub

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
			playerNumber++;
	}


}
