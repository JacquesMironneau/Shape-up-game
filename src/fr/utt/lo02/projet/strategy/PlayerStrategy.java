package fr.utt.lo02.projet.strategy;

public interface PlayerStrategy
{
	/**
	 * Ask the player if he wants to place or move a card
	 */
	public void askChoice();
	
	/**
	 * Ask a player to place a card
	 * So pick one card and select where he wants to put it
	 */
	public void askPlaceCard();
	
	/**
	 * Ask a player to move a card
	 * So pick one card and select where he wants to move it
	 */
	public void askMoveCard();
	
	/**
	 * Display the current state of the board to the player
	 */
	public void displayBoard();
	
	/**
	 * Display the scores to player
	 */
	public void displayScores();

}
