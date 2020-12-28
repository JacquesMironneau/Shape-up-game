package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.BoardEmptyException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent the player strategy in which we can define
 * It is an interface because we have 2 player strategies, a real player and a virtual player.
 *
 * @author Baptiste, Jacques
 */

public abstract class Player
{
	private final String name;
	protected List<Card> playerHand;


	private final List<Integer> scoresRound;
	private Card victoryCard;
	protected AbstractBoard board;

	public Player(String name, AbstractBoard b)
	{
		this.name = name;
		this.board = b;
		this.scoresRound = new ArrayList<>();
		this.playerHand = new ArrayList<>();
	}
	/**
	 * Ask the player if he wants to place or move a card
	 * @param choiceNumber
	 */
	public abstract Choice askChoice(ChoiceOrder choiceNumber);

	/**
	 * Ask a player to place a card
	 * So pick one card and select where he wants to put it
	 */
	public  abstract PlaceRequest askPlaceCard() throws PlayerHandEmptyException;

	/**
	 * Ask a player to move a card
	 * So pick one card and select where he wants to move it
	 */
	public abstract MoveRequest askMoveCard() throws BoardEmptyException;

	/**
	 * Display the scores to player
	 */
	public void displayRoundScore()
	{
		int score = scoresRound.get(scoresRound.size()-1);
		System.out.println(name + " score : "+ score);
	}

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

	public void drawCard(Card card)
	{
		this.playerHand.add(card);
	}

	public Card getDrawCard()
	{
		return this.playerHand.get(getPlayerHand().size()-1);
	}

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
