package fr.utt.lo02.projet.game;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.board.visitor.IBoardVisitor;
import fr.utt.lo02.projet.strategy.PlayerStrategy;

import java.util.*;

public abstract class AbstractShapeUpGame
{

	public static final int MAX_ROUND_NUMBER = 4;

	/**
	 * Round index
	 */
	protected int roundNumber;

	/**
	 * is the current turn the first one ever played (by one of the player)
	 */
	protected boolean isFirstTurn;

	// TODO: think about Collection type
	/**
	 * Scores of players in different rounds
	 */
	protected List<List<Integer>> scores;

	/**
	 * Deck of cards
	 */
	protected Queue<Card> deck;

	/**
	 * Victory card of each player
	 */
	protected Set<Card> victoryCards;

	/**
	 * Cards of each player
	 */
	protected List<Set<Card>> playerCards;

	/**
	 * Players of the game
	 */
	protected List<PlayerStrategy> players;

	/**
	 * Game board of the game
	 */
	protected AbstractBoard board;

	/**
	 * Visitor used for score calculation at the end of each round
	 */
	private final IBoardVisitor visitor;


	public AbstractShapeUpGame(IBoardVisitor visitor, List<PlayerStrategy> players, AbstractBoard board)
	{
		this.visitor = visitor;
		this.players = players;
		this.board = board;


		this.roundNumber = 0;
		this.scores = new ArrayList<>(4);

		for (PlayerStrategy player : this.players)
		{
			player.setGame(this);
		}
	}

	/**
	 * Play several round for a game and calculate gameScore
	 */
	public void playGame()
	{

		for (this.roundNumber = 0; this.roundNumber < MAX_ROUND_NUMBER; ++this.roundNumber)
		{
			initRound();
			playRound();
		}
		calculateGameScore();
	}

	/**
	 * Initiate a round
	 */
	protected abstract void initRound();


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
	 * @param request
	 * @return
	 */
	public boolean placeCardRequest(Map.Entry<Coordinates, Card> request, PlayerStrategy player)
	{
		Card aCard = request.getValue();
		Coordinates coord = request.getKey();

		boolean cardInTheLayout = board.isCardInTheLayout(coord);
		boolean cardAdjacentToAnExistingCard = true;

		if (!isFirstTurn)
		{
			cardAdjacentToAnExistingCard = board.isCardAdjacent(coord);
		}
		if (cardAdjacentToAnExistingCard && cardInTheLayout)
		{
			board.addCard(aCard, coord);
			this.playerCards.get(players.indexOf(player)).remove(aCard);
			return true;
		}

		return false;
	}

	/**
	 * * Request to move a existing card from the board to another position
	 *
	 * @param entry player request
	 * @return if the card has been moved or not
	 */
	public boolean moveCardRequest(Map.Entry<Coordinates, Card> entry)
	{
		Card aCard = entry.getValue();
		Coordinates coordinates = entry.getKey();

		boolean cardAdjacentToAnExistingCard = board.isCardAdjacent(coordinates);
		boolean cardInTheLayout = board.isCardInTheLayout(coordinates);

		if (cardAdjacentToAnExistingCard && cardInTheLayout)
		{
			board.addCard(aCard, coordinates);
			return true;
		}
		return false;
	}

	/**
	 * Add a card from the deck to a given player based on its index
	 * If the deck is empty, it doesn't add anything to the player.
	 *
	 * @param playerIndex index of the player in the player Collection.
	 */
	protected void drawCard(int playerIndex)
	{
		this.playerCards.get(playerIndex).add(this.deck.poll());
	}

	/**
	 * This method calculate the game score, so at the end of the game (after every round)
	 * It adds score of each player, and display it.
	 */
	protected void calculateGameScore()
	{
		ArrayList<Integer> gameScore = new ArrayList<>(this.players.size());

		for (int i = 0; i < this.players.size(); i++)
		{
			int playerScore = 0;
			for (int j = 0; j < MAX_ROUND_NUMBER; j++)
			{
				playerScore += this.scores.get(i).get(j);
			}
			gameScore.add(playerScore);
		}
		//TODO: display

	}

	protected void calculateRoundScore()
	{
		ArrayList<Integer> roundScore = new ArrayList<>();
		for (Card card : victoryCards)
		{
			roundScore.add(this.board.accept(visitor, card));
		}
		this.scores.add(roundNumber, roundScore);
	}

	protected void initDeck()
	{
		for (Card.Color color : Arrays.asList(Card.Color.BLUE, Card.Color.GREEN, Card.Color.RED))
		{
			this.deck.add(new Card(color, Card.Shape.CIRCLE, Card.Filling.HOLLOW));
		}

		for (Card.Color color : Arrays.asList(Card.Color.BLUE, Card.Color.GREEN, Card.Color.RED))
		{
			this.deck.add(new Card(color, Card.Shape.SQUARE, Card.Filling.HOLLOW));
		}

		for (Card.Color color : Arrays.asList(Card.Color.BLUE, Card.Color.GREEN, Card.Color.RED))
		{
			this.deck.add(new Card(color, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));
		}

		for (Card.Color color : Arrays.asList(Card.Color.BLUE, Card.Color.GREEN, Card.Color.RED))
		{
			this.deck.add(new Card(color, Card.Shape.CIRCLE, Card.Filling.FILLED));
		}

		for (Card.Color color : Arrays.asList(Card.Color.BLUE, Card.Color.GREEN, Card.Color.RED))
		{
			this.deck.add(new Card(color, Card.Shape.SQUARE, Card.Filling.FILLED));
		}

		for (Card.Color color : Arrays.asList(Card.Color.BLUE, Card.Color.GREEN, Card.Color.RED))
		{
			this.deck.add(new Card(color, Card.Shape.TRIANGLE, Card.Filling.FILLED));
		}
	}

}
