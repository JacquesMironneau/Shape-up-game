package fr.utt.lo02.projet.strategy;

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
	void askPlaceCard();

	/**
	 * Ask a player to move a card
	 * So pick one card and select where he wants to move it
	 */
	void askMoveCard();
	
	/**
	 * Display the current state of the board to the player
	 */
	void displayBoard();
	
	/**
	 * Display the scores to player
	 */
	void displayScores();

	/**
	 *  Set a game for a player, allowing him to request movement or display elements.
	 * @param game the given game
	 */
	void setGame(AbstractShapeUpGame game);


}
