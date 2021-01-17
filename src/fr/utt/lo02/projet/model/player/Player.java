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

	public Card getVictoryCard()
	{
		return victoryCard;
	}

	public List<Card> getPlayerHand()
	{
		return playerHand;
	}

	public String getName()
	{
		return name;
	}


	public List<Integer> getScoresRound()
	{
		return scoresRound;
	}


}
