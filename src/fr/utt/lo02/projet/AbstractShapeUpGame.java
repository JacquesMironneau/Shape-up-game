package fr.utt.lo02.projet;

import java.util.List;
import java.util.Set;

public abstract class AbstractShapeUpGame
{

	/**
	 * Round index
	 */
	private int roundNumber;
	
	// TODO: think about Collection type
	private List<List<Integer>> scores;
	
	/**
	 * Deck of cards
	 */
	private Set<Card> deck;
	
	/**
	 * Victory card of each player
	 */
	private Set<Card> victoryCards;
	
	/**
	 * Cards of each player
	 */
	private List<Set<Card>> playerCards;
	
	private Set<PlayerStrategy> players;
	
	/**
	 * Initiate a round
	 */
	protected abstract void initRound();
	
	/**
	 * Play several round for a game and calculate gameScore
	 */
	public void playGame()
	{
		for (this.roundNumber = 0; this.roundNumber < 4; ++this.roundNumber)
		{
			playRound();
		}
		
		calculateGameScore();
	}
	/**
	 * Plays one of the game round 
	 */
	protected abstract void playRound();

	/**
	 * Turns loop for each player
	 */
	protected abstract void playTurn();
	
	/**
	 * Request to place a card from the player hand to a position on the board
	 * 
	 * @param aCard the existing card
	 * @param x coordinate on the abscissa
	 * @param y coordinates in ordinate
	 * @return if card has been placed or not
	 */
	public boolean placeCardRequest(Card aCard, int x, int y)
	{
		return false;
	}
	
	/**
	 * Request to move a existing card from the board to another position
	 * 
	 * @param aCard the existing card
	 * @param x coordinate on the abscissa
	 * @param y coordinates in ordinate
	 * @return if card has been moved or not
	 */
	public boolean moveCardRequest(Card aCard, int x, int y)
	{
		return false;
	}
	
	/**
	 * Add a card from the deck to a given player based on its index
	 * If the deck is empty, it doesn't add anything to the player.
	 * @param playerIndex index of the player in the player Collection.
	 */
	protected void drawCard(int playerIndex)
	{
		
	}
	
	/**
	 *  This method calculate the game score, so at the end of the game (after every round)
	 *  It adds score of each player, and display it. 
	 */
	protected abstract void calculateGameScore();

}
