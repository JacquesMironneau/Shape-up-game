package fr.utt.lo02.projet.model.player;

import fr.utt.lo02.projet.model.board.AbstractBoard;
import fr.utt.lo02.projet.model.board.BoardEmptyException;
import fr.utt.lo02.projet.model.board.Card;
import fr.utt.lo02.projet.model.game.ChoiceOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player with all his characteristics and his actions.
 * It is abstract because we have 2 types of player, a real player and a virtual player.
 *
 * @author Baptiste, Jacques
 */
public abstract class Player
{
	/**
	 * Player's name.
	 */
	private final String name;
	
	/**
	 * Player's hand.
	 */
	protected List<Card> playerHand;

	/**
	 * Player's scores, one at each round of the game.
	 */
	private final List<Integer> scoresRound;
	
	/**
	 * Player's victory card.
	 */
	private Card victoryCard;
	
	/**
	 * The board of the game.
	 */
	protected AbstractBoard board;

	/**
	 * Player's constructor. Sets up parameters and create 2 lists: 
	 * one for player rounds' scores and one for player's hand.
	 * @param name the player's name.
	 * @param b the game's board.
	 */
	public Player(String name, AbstractBoard b)
	{
		this.name = name;
		this.board = b;
		this.scoresRound = new ArrayList<>();
		this.playerHand = new ArrayList<>();
	}
	
	/**
	 * Ask the player if he wants to place or move a card, or end his turn.
	 * @param choiceNumber player's choice.
	 */
	public abstract Choice askChoice(ChoiceOrder choiceNumber);

	/**
	 * Ask a player to place a card
	 * So pick one card and select where he wants to put it.
	 */
	public  abstract PlaceRequest askPlaceCard() throws PlayerHandEmptyException;

	/**
	 * Ask a player to move a card.
	 * So pick one card and select where he wants to move it.
	 */
	public abstract MoveRequest askMoveCard() throws BoardEmptyException;

	/**
	 * Display player's round score
	 */
	public void displayRoundScore()
	{
		int score = scoresRound.get(scoresRound.size()-1);
		System.out.println(name + " score : "+ score);
	}

	/**
	 * Display all player's round scores and player's final score
	 */
	public void displayFinalScore() {
		int roundNumber=1;
		int finalScore=0;
		for (int scores: scoresRound) {
			System.out.println(name + " : Score for Round " + roundNumber + " -> " + scores);
			roundNumber++;
			finalScore += scores;
		}
		System.out.println(name + " : FINAL SCORE = " + finalScore);
	}

	/**
	 * Sets the victory Card to the player.
	 * @param victoryCard the player's victory card.
	 */
	public void setVictoryCard(Card victoryCard)
	{
		this.victoryCard = victoryCard;
	}

	/**
	 * Makes the player draw a card
	 * @param card the card he draws
	 */
	public void drawCard(Card card)
	{
		this.playerHand.add(card);
	}

	/**
	 * Used to get the card which player just draw.
	 * @return the card he draw.
	 */
	public Card getDrawCard()
	{
		if (playerHand.isEmpty())
		{
			return null;
		}
		return this.playerHand.get(getPlayerHand().size()-1);
	}

	/**
	 * Add the current round's score to the list scoresRound.
	 * @param scoreOfCurrentRound
	 */
	public void addRoundScore(int scoreOfCurrentRound)
	{
		this.scoresRound.add(scoreOfCurrentRound);
	}

	/**
	 * Used to get player's victory card.
	 * @return player's victory card.
	 */
	public Card getVictoryCard()
	{
		return victoryCard;
	}

	/**
	 * Used to get player's hand.
	 * @return player's hand.
	 */
	public List<Card> getPlayerHand()
	{
		return playerHand;
	}

	/**
	 * Used to get player's name.
	 * @return player's name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Used to get player rounds' scores.
	 * @return A list of the player rounds' scores.
	 */
	public List<Integer> getScoresRound()
	{
		return scoresRound;
	}


}
