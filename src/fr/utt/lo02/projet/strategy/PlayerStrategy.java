package fr.utt.lo02.projet.strategy;

import java.util.Map.Entry;
import java.util.List;
import java.util.Set;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.board.RectangleBoard;
import fr.utt.lo02.projet.game.AbstractShapeUpGame;

/**
 * Represent the player strategy in which we can define
 * It is an interface because we have 2 player strategies, a real player and a virtual player.
 * @author Baptiste, Jacques
 *
 */

public interface PlayerStrategy
{
	/**
	 * Ask the player if he wants to place or move a card
	 */
	Choice askChoice();

	/**
	 * Ask a player to place a card
	 * So pick one card and select where he wants to put it
	 */
	Request askPlaceCard(Set<Card> playerHand, AbstractBoard board);

	/**
	 * Ask a player to move a card
	 * So pick one card and select where he wants to move it
	 */
	Request askMoveCard(AbstractBoard board);
	
	/**
	 * Display the current state of the board to the player
	 */
	void displayBoard();
	
	/**
	 * Display the scores to player
	 */
	void displayRoundScore(int score);
	
	void displayFinalScoreForThisRound (int score, int playerNumber);

	void displayFinalScore(List<Integer> scoresRound, int playerNumber);
}
