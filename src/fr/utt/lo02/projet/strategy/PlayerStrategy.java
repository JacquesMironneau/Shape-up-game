package fr.utt.lo02.projet.strategy;
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
	PlaceRequest askPlaceCard();

	/**
	 * Ask a player to move a card
	 * So pick one card and select where he wants to move it
	 */
	MoveRequest askMoveCard();
	
	/**
	 * Display the scores to player
	 */
	void displayRoundScore(int roundNumber);
	
	void displayFinalScoreForThisRound (int roundNumber, int playerNumber);

	void displayFinalScore(int playerNumber);
}
