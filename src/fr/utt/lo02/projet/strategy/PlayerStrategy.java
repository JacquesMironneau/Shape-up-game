package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.board.boardEmptyException;
import fr.utt.lo02.projet.board.Card;

import java.util.List;

/**
 * Represent the player strategy in which we can define
 * It is an interface because we have 2 player strategies, a real player and a virtual player.
 *
 * @author Baptiste, Jacques
 */

public interface PlayerStrategy
{
	/**
	 * Ask the player if he wants to place or move a card
	 * @param choiceNumber
	 */
	Choice askChoice(ChoiceOrder choiceNumber);

	/**
	 * Ask a player to place a card
	 * So pick one card and select where he wants to put it
	 */
	PlaceRequest askPlaceCard() throws PlayerHandEmptyException;

	/**
	 * Ask a player to move a card
	 * So pick one card and select where he wants to move it
	 */
	MoveRequest askMoveCard() throws boardEmptyException;

	/**
	 * Display the scores to player
	 */
	void displayRoundScore();

	void displayFinalScore();

	void setVictoryCard(Card victoryCard);

	void drawCard(Card card);

	void addRoundScore(int scoreOfCurrentRound);

	Card getVictoryCard();

	List<Card> getPlayerHand();

	public String getName();
}
