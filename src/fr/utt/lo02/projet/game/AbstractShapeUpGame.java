package fr.utt.lo02.projet.game;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.boardEmptyException;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.board.visitor.IBoardVisitor;
import fr.utt.lo02.projet.strategy.MoveRequest;
import fr.utt.lo02.projet.strategy.PlaceRequest;
import fr.utt.lo02.projet.strategy.PlayerHandEmptyException;
import fr.utt.lo02.projet.strategy.PlayerStrategy;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;

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


	/**
	 * Deck of cards
	 */
	protected Queue<Card> deck;

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
	}

	/**
	 * Play several round for a game and calculate gameScore
	 */
	public void playGame() throws PlayerHandEmptyException, boardEmptyException
	{

		for (this.roundNumber = 0; this.roundNumber < MAX_ROUND_NUMBER; ++this.roundNumber)
		{
			initRound();
			playRound();
			calculateRoundScore();

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
	protected abstract void playRound() throws PlayerHandEmptyException, boardEmptyException;

	/**
	 * Turns loop for one player
	 */
	protected abstract void playTurn(PlayerStrategy player) throws PlayerHandEmptyException, boardEmptyException;

	/**
	 * Request to place a card from the player hand to a position on the board
	 *
	 * @param placeRequest Which card and where the player want to put it
	 * @param player       the player that is making the request
	 * @return if the request is matching the game rules
	 */
	public boolean placeCardRequest(PlaceRequest placeRequest, PlayerStrategy player) throws boardEmptyException
	{
		Card aCard = placeRequest.getCard();
		Coordinates coord = placeRequest.getCoordinates();

		if (!player.getPlayerHand().contains(aCard)) return false;

		boolean cardInTheLayout = board.isCardInTheLayout(coord);
		boolean cardAdjacentToAnExistingCard = true;

		if (!isFirstTurn)
		{
			cardAdjacentToAnExistingCard = board.isCardAdjacent(coord);
		}
		if (cardAdjacentToAnExistingCard && cardInTheLayout)
		{
			board.addCard(coord, aCard);
			player.getPlayerHand().remove(aCard);
			System.out.println("[LOG] "+ aCard + " has been placed at " + coord);

			return true;
		}
		return false;
	}

	/**
	 * * Request to move a existing card from the board to another position
	 *
	 * @param moveRequest player request
	 * @return if the card has been moved or not
	 */
	public boolean moveCardRequest(MoveRequest moveRequest) throws boardEmptyException
	{
		Coordinates origin = moveRequest.getOrigin();
		Coordinates destination = moveRequest.getDestination();

		if (origin.equals(destination)) return false;



		Card card = this.board.getPlacedCards().get(origin);

		if (card == null) return false;

		board.removeCard(origin, card);

		if (board.getPlacedCards().isEmpty())
		{
			board.addCard(destination, card);
			return true;
		}
		boolean cardAdjacentToAnExistingCard = board.isCardAdjacent(destination);
		boolean cardInTheLayout = board.isCardInTheLayout(destination);

		if (cardAdjacentToAnExistingCard && cardInTheLayout)
		{
			//board.removeCard(origin, card);
			board.addCard(destination, card);
			System.out.println("[LOG] " + card + " has been moved from" + origin + "to "+ destination);
			return true;
		}
		board.addCard(origin,card);
		System.out.println("euuuuuuh ?");
		return false;
	}

	/**
	 * Add a card from the deck to a given player based on its index
	 * If the deck is empty, it doesn't add anything to the player.
	 *
	 * @param player The player which will be given a card.
	 */
	public void drawCard(PlayerStrategy player)
	{
		player.drawCard(this.deck.poll());
	}

	/**
	 * This method calculate the game score, so at the end of the game (after every round)
	 * It adds score of each player, and display it.
	 */
	protected void calculateGameScore()
	{
		for (PlayerStrategy player: players)
		{
			player.displayFinalScore();
		}
	}

	protected void calculateRoundScore()
	{
		for (PlayerStrategy p : players)
		{
			p.addRoundScore(this.board.accept(visitor, p.getVictoryCard()));
			p.displayRoundScore();
		}

	}

	protected void initDeck()
	{
		if (!deck.isEmpty()) return;

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

	protected boolean isRoundFinished()
	{
		for (PlayerStrategy player : players)
		{
			if (!player.getPlayerHand().isEmpty()) return false;
		}
		return deck.isEmpty();
	}
}
